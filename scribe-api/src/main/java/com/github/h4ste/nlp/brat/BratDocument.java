package com.github.h4ste.nlp.brat;

import com.github.h4ste.scribe.util.Describable;
import com.github.h4ste.util.Fatal;
import com.github.h4ste.util.PathUtils;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BratDocument implements Describable {

  private static final Logger log = LogManager.getLogger(BratDocument.class);

  private final Table<String, String, BratTextAnnotation> textAnnots = TreeBasedTable.create();
  private final Table<String, String, Set<BratAttributeAnnotation>> attributes = TreeBasedTable
      .create();
  private final Table<String, String, Set<BratNormalizationAnnotation>> normalizations =
      TreeBasedTable.create();

  private final AtomicInteger textNid = new AtomicInteger(0);
  private final AtomicInteger attrNid = new AtomicInteger(0);
  private final AtomicInteger normNid = new AtomicInteger(0);

  private final String text;
  private final Path path;

  public BratDocument(String text, Path path) {
    this.text = text;
    this.path = path;
  }

  public static BratDocument fromPath(Path path) {
    final String text;
    try {
      text = new String(Files.readAllBytes(path), Charset.defaultCharset());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return new BratDocument(text, path);
  }

  private static <T> Set<T> asSet(Collection<T> values) {
    if (values instanceof Set) {
      return (Set<T>) values;
    } else {
      return new LinkedHashSet<>(values);
    }
  }

  public Path getPath() {
    return path;
  }

  private BufferedReader getAnnotationReader(Path path) throws IOException {
    final String fileName = path.getFileName().toString();
    if (fileName.endsWith(".gz")) {
      // Despite the name, the ann.gz files are not all currently gzipped,
      // so we need to fall back to regular file reading
      try {
        final InputStream is = Files.newInputStream(path);
        // Store the first two bytes in a buffer so we can "unread" them if gzip decompression fails
        final PushbackInputStream pbis = new PushbackInputStream(is, 2);

        // Read first byte of the file
        final int header = pbis.read();

        final InputStream fis;
        if (header == -1) {
          // The file is empty
          fis = pbis;
        } else {
          // The file is not empty, so read the second byte of the header
          final int b = pbis.read();
          if (b == -1) {
            // There is no second byte, so unread the first byte and return underlying stream
            pbis.unread(header);
            fis = pbis;
          } else {
            // There is a second byte to the header, so unread both header bytes
            pbis.unread(new byte[]{(byte) header, (byte) b});

            // Form full two-byte header
            final int gzip_header = (b << 8) | header;
            if (gzip_header == GZIPInputStream.GZIP_MAGIC) {
              // This file has a GZIP header, so open it as a gzip stream
              fis = new GZIPInputStream(pbis);
            } else {
              // This file doesn't have a gzip header, so read it as-is
              fis = pbis;
            }
          }
        }

        final InputStreamReader isr = new InputStreamReader(fis);
        return new BufferedReader(isr);
      } catch (NoSuchFileException e) {
        return getAnnotationReader(PathUtils.removeExtension(path));
      }

    } else if (fileName.endsWith(".ann")) {
      return Files.newBufferedReader(path);
    } else {
      throw new UnsupportedOperationException("Unable to read annotation file " + path);
    }
  }

  /**
   * Adds all annotations from the given brat annotation file to this document. This method assumes
   * that the annotation IDs in path are unique and consistent within that file If this {@code
   * BratDocument} already has annotations, the IDs of annotations in the given file will be shifted
   * to as to not collide with current annotations.
   *
   * @param path Path to brat-style stand-off annotations
   * @throws IOException if unable to read annotation file
   */
  public void addAnnotations(Path path) throws IOException {
    /* Prevent annotations in path from conflicting by shifting all new annotations based on the
       last assigned index of annotations in this document.
     */
    final int textNidShift = textNid.get();
    final int attrNidShift = attrNid.get();
    final int normNidShift = normNid.get();

    try (final BufferedReader reader = getAnnotationReader(path)) {
      String line;
      for (int lineNo = 1; (line = reader.readLine()) != null; lineNo++) {
        // Skip empty or blank lines
        if (line.isEmpty()) {
          continue;
        }
        switch (line.charAt(0)) {
          case 'T':
            final BratTextAnnotation textAnnot = BratTextAnnotation.fromString(line);
            addTextAnnotation(textAnnot.shiftIndex(textNidShift));
            textNid.incrementAndGet();
            break;
          case 'A':
            final BratAttributeAnnotation brat = BratAttributeAnnotation.fromString(line);
            int shift;
            switch (brat.getParentId().charAt(0)) {
              case 'N':
                shift = normNidShift;
                break;
              case 'T':
                shift = textNidShift;
                break;
              default:
                throw new UnsupportedOperationException("Encountered attribute for unsupported annotation type: " + brat.getParentId() + "(excepted N## or T##)");
            }
            addAttribute(brat.shiftIndex(attrNidShift).shiftParent(shift));
            attrNid.incrementAndGet();
            break;
          case 'N':
            addNormalization(BratNormalizationAnnotation.fromString(line).shiftIndex(normNidShift)
                .shiftParent(textNidShift));
            normNid.incrementAndGet();
            break;
          case '#':
            break;
          case '*':
          case 'R':
            log.warn("Brat relations are not supported in file {}, ignoring line {}: \"{}\"", path,
                lineNo, line);
            break;
          default:
            log.warn("Malformed file {} line {} was malformed: \"{}\"", path, lineNo, line);
            break;
        }
      }
    }
  }

  public BratDocument addTextAnnotation(BratTextAnnotation annot) {
    if (annot.getText() != null) {
      String textSpan = this.text.substring(annot.getStart(), annot.getEnd());
      if (!textSpan.equals(annot.getText())) {
        int newStart = this.text.indexOf(annot.getText(), annot.getStart());
        if (newStart < 0) {
          log.fatal("Failed to find brat text annotation {} after position {} in {}",
              annot.getText(), annot.getStart(), getPath());
          Fatal.ioError();
        }
        int newEnd = newStart + annot.getText().length();
//        log.trace("Adjusting Brat text annotation from \"{}\"@[{},{}) to \"{}\"@[{},{})",
//            textSpan, annot.getStart(), annot.getEnd(),
//            text.substring(newStart, newEnd), newStart, newEnd
//        );
        annot.setPosition(newStart, newEnd);
      }
    }
    this.textAnnots.put(annot.getType(), annot.getId(), annot);
    return this;
  }

  public BratTextAnnotation newTextAnnotation(String name, int start, int end) {
    return new BratTextAnnotation(textNid.incrementAndGet(), name, start, end,
        text.substring(start, end));
  }

  public BratTextAnnotation newTextAnnotation(String name, int start, int end, String text) {
    return new BratTextAnnotation(textNid.incrementAndGet(), name, start, end, text);
  }

  public BratDocument addNewBinaryAttribute(String parentId, String name) {
    return addAttribute(new BratAttributeAnnotation(attrNid.incrementAndGet(), name, parentId));
  }

  public BratDocument addNewAttribute(String parentId, String name, String value) {
    return addAttribute(
        new BratAttributeAnnotation(attrNid.incrementAndGet(), name, parentId, value));
  }

  public BratDocument addAttribute(BratAttributeAnnotation annot) {
    assert textAnnots.containsColumn(annot.getParentId())  ||
           normalizations.containsColumn(annot.getParentId())
           : "Attribute refers to non-existent parent";
    Set<BratAttributeAnnotation> attrs = attributes.get(annot.getParentId(), annot.getType());
    if (attrs == null) {
      attrs = new HashSet<>();
    }
    attrs.add(annot);
    attributes.put(annot.getParentId(), annot.getType(), attrs);
    return this;
  }

  public BratNormalizationAnnotation newNormalization(String parentId, String source, String sourceId,
      String sourceText) {
    return new BratNormalizationAnnotation(
        normNid.incrementAndGet(),
        parentId,
        source, sourceId, sourceText);
  }

  public BratDocument addNormalization(BratNormalizationAnnotation annot) {
    assert textAnnots
        .containsColumn(annot.getParentId()) : "Normalization refers to non-existent parent";
    Set<BratNormalizationAnnotation> norms = normalizations
        .get(annot.getParentId(), annot.getSource());
    if (norms == null) {
      norms = new HashSet<>();
    }
    norms.add(annot);
    normalizations.put(annot.getParentId(), annot.getSource(), norms);
    return this;
  }

  public Set<BratTextAnnotation> getTextAnnots() {
    return asSet(textAnnots.values());
  }

  private <T> Set<T> getOrEmpty(Table<String, String, ? extends Set<T>> table, String key,
      String value) {
    final Set<T> set = table.get(key, value);
    if (set == null) {
      return Collections.emptySet();
    } else {
      return set;
    }
  }

  public Set<BratAttributeAnnotation> getAttributes(String parentId, String type) {
    return getOrEmpty(attributes, parentId, type);
  }

  public Set<BratNormalizationAnnotation> getNormalization(String parentId, String source) {
    return getOrEmpty(normalizations, parentId, source);
  }

  public Set<BratAttributeAnnotation> getAttributes() {
    return attributes.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
  }

  public Set<BratNormalizationAnnotation> getNormalizations() {
    return normalizations.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
  }

  public String getText() {
    return text;
  }

  public String annotationsToString() {
    return Stream.of(
        // Stream of all text annotations
        textAnnots.values().stream(),
        // Flatten Stream<Collection<A>> to Stream<At>
        attributes.values().stream().flatMap(Collection::stream),
        // Flatten Stream<Collection<Norm>> to Stream<N>
        normalizations.values().stream().flatMap(Collection::stream))

        // We already have streams so we just need to flatten,
        // but Java doesn't give  us a flatten() method,
        // so we have to flatMap without actually mapping
        // (i.e., using the identity function)
        .flatMap(Function.identity())

        // The toString() method on BratAnnotation prints it in Brat format
        .map(Object::toString)

        // Add new lines
        .collect(Collectors.joining("\n"));
  }

  @Override
  public String describe() {
    return "Text:\n\"" + this.text
        + "\"\nText Annotations:\n"
        + getTextAnnots().stream().map(Object::toString).collect(Collectors.joining("\n"))
        + "\nAttributes:\n"
        + getAttributes().stream().map(Object::toString).collect(Collectors.joining("\n"))
        + "\nNormalizations:\n"
        + getNormalizations().stream().map(Object::toString).collect(Collectors.joining("\n"));
  }

  @Override
  public String toString() {
    return "Brat[" + path + ']';
  }
}

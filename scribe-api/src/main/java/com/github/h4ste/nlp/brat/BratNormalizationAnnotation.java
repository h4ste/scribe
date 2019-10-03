package com.github.h4ste.nlp.brat;

import static com.github.h4ste.collect.IteratorUtils.nextOrIOException;
import static com.github.h4ste.util.ParseUtils.parsePositiveInt;

import com.google.common.base.Splitter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Objects;

public class BratNormalizationAnnotation extends BratAnnotation implements BratModifierAnnotation {

  private static final Splitter ATTR_SPLITTER = Splitter.on(' ').limit(3);
  private static final String ATTR_TYPE = "Reference";
  private static final char ANNOT_TYPE = 'N';

  private String parentId;

  private final String source;
  private final String sourceId;
  private final String sourceText;

  public BratNormalizationAnnotation(int index, String parentId, String source, String sourceId,
      String sourceText) {
    super(ANNOT_TYPE, index, "Reference");
    this.parentId = parentId;
    this.source = source;
    this.sourceId = sourceId;
    this.sourceText = sourceText;
  }

  public static BratNormalizationAnnotation fromString(String line) {
    final Iterator<String> fields = FIELD_SPLITTER.limit(3).split(line).iterator();

    try {

      final String key = nextOrIOException(fields, "missing annotation id");
      final char bratType = key.charAt(0);
      assert bratType == ANNOT_TYPE : "invalid normalization annotation id \"" + key
          + "\" (should maybeStart with '" + bratType + "' not '" + bratType + "'";
      final int index = parsePositiveInt(key.substring(1), "invalid annotation index");

      final String attr = nextOrIOException(fields, "missing annotation fields");
      final Iterator<String> attrFields = ATTR_SPLITTER.split(attr).iterator();

      final String attrType = nextOrIOException(attrFields, "missing annotation type/name");
      assert attrType.equals(ATTR_TYPE) : "invalid attribute type for normalization \"" + key
          + "\" (should be \"" + bratType + "\" not \"" + bratType + "\"";

      final String parentId = nextOrIOException(attrFields, "missing parent ID");
      final String sourceMapping = nextOrIOException(attrFields, "missing source mapping");

      final int delim = sourceMapping.indexOf(':');
      if (delim < 0) {
        throw new IOException("invalid source mapping");
      }
      final String source = sourceMapping.substring(0, delim);
      final String sourceId = sourceMapping.substring(delim + 1);

      final String sourceText = nextOrIOException(fields, "missing annotation text");

      return new BratNormalizationAnnotation(index, parentId, source, sourceId, sourceText);

    } catch (IOException ioe) {
      throw new UncheckedIOException("Failed to parse line \"" + line + '"', ioe);
    }
  }

  public String getSourceId() {
    return sourceId;
  }

  public String getSourceText() {
    return sourceText;
  }

  public String getSource() {
    return source;
  }

  @Override
  public String getParentId() {
    return parentId;
  }

  @Override
  public String toString() {
    return BratAnnotation.FIELD_JOINER.join(
        getId(),
        getType() + ' ' + parentId + ' ' + source + ':' + sourceId,
        sourceText);
  }

  @Override
  BratNormalizationAnnotation shiftIndex(int shift) {
    return (BratNormalizationAnnotation) super.shiftIndex(shift);
  }

  BratNormalizationAnnotation shiftParent(int shift) {
    final char annotType = this.parentId.charAt(0);

    // Get current/original index
    int index = Integer.parseInt(this.parentId.substring(1));

    // Update id by shifting index;
    this.parentId = annotType + Integer.toString(index + shift);

    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    BratNormalizationAnnotation that = (BratNormalizationAnnotation) o;
    return Objects.equals(parentId, that.parentId) &&
        Objects.equals(source, that.source) &&
        Objects.equals(sourceId, that.sourceId) &&
        Objects.equals(sourceText, that.sourceText);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), parentId, source, sourceId, sourceText);
  }
}

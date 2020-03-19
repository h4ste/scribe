package com.github.h4ste.nlp.brat;

import com.github.h4ste.scribe.Annotation;
import com.github.h4ste.scribe.Text;
import com.github.h4ste.scribe.TextAnnotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Brat2Scribe<D extends Text> {

  private static final Logger log = LogManager.getLogger(Brat2Scribe.class);
  private final Function<BratDocument, ? extends D> documentConverter;
  private final Map<String, TextMapping<? extends TextAnnotation>> textAnnotationMapping =
      new ConcurrentHashMap<>();
  private final Map<String, AttributeMapping<? extends Annotation>> attrAnnotationMapping =
      new ConcurrentHashMap<>();

  public Brat2Scribe(
      Function<BratDocument, ? extends D> documentConverter) {
    this.documentConverter = documentConverter;
  }

  public <T extends TextAnnotation> Brat2Scribe<D> addTextMapping(
      String bratTextAnnotationName,
      BiFunction<BratTextAnnotation, BratDocument, ? extends T> converter,
      String... tags) {

    textAnnotationMapping.put(bratTextAnnotationName,
        new TextMapping<>(converter, new HashSet<>(Arrays.asList(tags))));

    return this;
  }

  public <A extends Annotation> Brat2Scribe<D> addAttributeMapping(
      String bratAttributeAnnotationName,
      BiFunction<BratAttributeAnnotation, BratDocument, ? extends A> converter,
      String... tags) {

    attrAnnotationMapping.put(bratAttributeAnnotationName,
        new AttributeMapping<>(converter, new HashSet<>(Arrays.asList(tags))));

    return this;
  }

  public D convert(BratDocument bratDoc) {
    log.trace("Converting {}", bratDoc);
    final D scribeDoc = documentConverter.apply(bratDoc);
    if (bratDoc.getTextAnnots().isEmpty()) {
      log.warn("No annotations on {}", bratDoc.getPath());
      return scribeDoc;
    }

    final Map<String, TextAnnotation> textAnnotations = new HashMap<>();

    for (final BratTextAnnotation bratAnnot : bratDoc.getTextAnnots()) {
      final TextMapping<? extends TextAnnotation> textMapping =
          textAnnotationMapping.computeIfAbsent(bratAnnot.getType(),
              unused -> {
                log.warn("No mapping found for text annotation {}", bratAnnot.getType());
                return  TextMapping.NONE;
              }
            );

      if (textMapping == TextMapping.NONE) {
        continue;
      }

      final TextAnnotation annot = textMapping.converter.apply(bratAnnot, bratDoc);
      final String textSpan = annot.toString();
      assert !bratAnnot.hasText() || textSpan.equals(bratAnnot.getText())
          : "Text span @[" + annot.getStart() + ", "           + annot.getEnd() + "]=\"" + textSpan
          + "\" != brat text \"" + bratAnnot.getText() + "\"!";

      scribeDoc.addAnnotation(annot, textMapping.tags);
      textAnnotations.put(bratAnnot.getId(), annot);
//      log.trace("Added annotation {}", annot.describe());
    }

    for (BratAttributeAnnotation bratAnnot : bratDoc.getAttributes()) {
      AttributeMapping<? extends Annotation> attrMapping =
          attrAnnotationMapping.computeIfAbsent(bratAnnot.getType(),
              unused -> {
                log.warn("No mapping found for attribute annotation {}", bratAnnot.getType());
                return  AttributeMapping.NONE;
              }
          );

      if (attrMapping == AttributeMapping.NONE) {
        continue;
      }

      final Annotation annot = attrMapping.converter.apply(bratAnnot, bratDoc);

      final TextAnnotation parent = textAnnotations.get(bratAnnot.getParentId());
      parent.addAnnotation(annot, attrMapping.tags);
//      log.debug("Added annotation {} to {}", annot.describe(), parent.describe());
    }

    return scribeDoc;
  }

  private static class TextMapping<T extends TextAnnotation> {
    static TextMapping<TextAnnotation> NONE = new TextMapping<>(null, null);

    private BiFunction<BratTextAnnotation, BratDocument, ? extends T> converter;
    private Set<String> tags;

    public TextMapping(
        BiFunction<BratTextAnnotation, BratDocument, ? extends T> converter,
        Set<String> tags) {
      this.tags = tags;
      this.converter = converter;
    }
  }

  private static class AttributeMapping<A extends Annotation> {
    static AttributeMapping<Annotation> NONE = new AttributeMapping<>(null, null);

    private BiFunction<BratAttributeAnnotation, BratDocument, ? extends A> converter;
    private Set<String> tags;

    public AttributeMapping(
        BiFunction<BratAttributeAnnotation, BratDocument, ? extends A> converter,
        Set<String> tags) {
      this.converter = converter;
      this.tags = tags;
    }
  }
}

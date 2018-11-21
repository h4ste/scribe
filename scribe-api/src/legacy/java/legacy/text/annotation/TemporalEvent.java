package com.github.h4ste.scribe.legacy.text.annotation;

import com.github.h4ste.scribe.legacy.text.Attribute;
import com.github.h4ste.scribe.legacy.text.Document;

/**
 * Created by rmm120030 on 1/11/16.
 */
public class TemporalEvent extends AbstractAnnotation<TemporalEvent> implements ThymeAnnotation {
  private final static long serialVersionUID = 1l;
  public static final Attribute<TemporalEvent, String> type = Attribute.typed("type", String.class);
  public static final Attribute<TemporalEvent, String> polarity = Attribute.typed("polarity", String.class);
  public static final Attribute<TemporalEvent, String> modality = Attribute.typed("modality", String.class);
  public static final Attribute<TemporalEvent, String> docTimeRel = Attribute.typed("docTimeRel", String.class);
  public static final Attribute<TemporalEvent, String> degree = Attribute.typed("degree", String.class);
  public static final Attribute<TemporalEvent, String> aspect = Attribute.typed("aspect", String.class);
  public static final Attribute<TemporalEvent, String> permanence = Attribute.typed("permanence", String.class);
  public static final Attribute<TemporalEvent, String> id = Attribute.typed("id", String.class);

  protected TemporalEvent(Document<?> parent, gate.Annotation ann) {
    super(parent, ann);
  }

  public static final AnnotationType<TemporalEvent> TYPE = new AbstractAnnotationType<TemporalEvent>("TemporalEvent") {
    @Override public TemporalEvent wrap(Document<?> parent, gate.Annotation ann) {
      return new TemporalEvent(parent, ann);
    }
  };

  public String getNullable(Attribute<TemporalEvent, String> attr) {
    final String s = get(attr);
    return (s == null) ? "null" : s;
  }
}
package com.github.h4ste.scribe.legacy.text.annotation;

import com.github.h4ste.scribe.legacy.text.Attribute;
import com.github.h4ste.scribe.legacy.text.Document;

/**
 * Created by rmm120030 on 9/4/15.
 */
public class Section extends AbstractAnnotation<Section> {
  private static final long serialVersionUID = 1L;

  public static final Attribute<Section, String> title = Attribute.typed("title", String.class);

  protected Section(final Document<?> parent, final gate.Annotation ann) {
    super(parent, ann);
  }

  public static final AnnotationType<Section> TYPE = new AbstractAnnotationType<Section>("SectionTitle") {
    @Override public Section wrap(Document<?> parent, gate.Annotation ann) {
      return new Section(parent, ann);
    }
  };
}
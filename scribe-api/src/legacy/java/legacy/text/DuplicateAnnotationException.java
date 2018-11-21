package com.github.h4ste.scribe.legacy.text;

import com.github.h4ste.scribe.legacy.text.annotation.Annotation;

/**
 * Created by travis on 8/13/14.
 */
public class DuplicateAnnotationException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final Annotation previousAnnotation;

  public DuplicateAnnotationException(Annotation previousAnnotation) {
    super();
    this.previousAnnotation = previousAnnotation;
  }
}

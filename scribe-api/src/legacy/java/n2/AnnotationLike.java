package com.github.h4ste.scribe.n2;

import com.github.h4ste.scribe.legacy.text.Identifiable;

/**
 * Annotation.
 *
 * Created by travis on 7/15/14.
 */
@SuppressWarnings("unused")
public interface AnnotationLike extends
    Describable,
    Identifiable,
    NumericIdentifiable,
    Taggable<String>,
    IntInterval {

  @Override default String describe() {
    return getId() + ':' + Long.toString(getNid()) + '@' + IntInterval.super.describe();
  }

  AnnotationManager getManager();



}

package com.github.h4ste.scribe.n2;

import com.github.h4ste.scribe.legacy.text.Identifiable;

import java.util.Comparator;

public interface Relation<S extends Annotation, T extends Annotation>
    extends Describable, Identifiable, NumericIdentifiable, Typeable, Taggable<String> {
  S getSource();
  T getTarget();

  static <R extends Relation<? extends Annotation, ? extends Annotation>> Comparator<R> documentOrder() {
    return Comparator.comparing(R::getSource, Annotation.documentOrder())
        .thenComparing(R::getTarget, Annotation.documentOrder());
  }
}

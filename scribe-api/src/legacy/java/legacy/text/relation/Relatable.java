package com.github.h4ste.scribe.legacy.text.relation;

import com.github.h4ste.scribe.legacy.text.annotation.Annotation;
import com.github.h4ste.scribe.legacy.text.annotation.AnnotationType;

/**
 * Created by rmm120030 on 5/18/16.
 */
public interface Relatable<A extends Annotation<A>> {
  AnnotationType<A> type();
}

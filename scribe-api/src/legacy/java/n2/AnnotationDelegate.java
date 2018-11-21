package com.github.h4ste.scribe.n2;

abstract class AnnotationDelegate implements AnnotationLike {
  abstract void setDelegator(Annotation delegator);

  abstract Annotation getDelegator();
}

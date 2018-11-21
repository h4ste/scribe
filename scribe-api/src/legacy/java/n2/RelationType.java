package com.github.h4ste.scribe.n2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RelationType {
  String name();
  Class<? extends Annotation> sourceType();
  Class<? extends Annotation> targetType();
}

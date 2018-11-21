package com.github.h4ste.scribe.n;

import com.github.h4ste.scribe.n2.Attribute;

public class Entity<T extends CharSequence & Annotatable<T>> extends TextAnnotation<T> {

  @Attribute("type")
  String type;

  public Entity(int start, int end, String type) {
    super(start, end);
  }

  public String getType() {
    return type;
  }
}

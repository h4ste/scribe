package com.github.h4ste.scribe.n;

import com.github.h4ste.scribe.n2.Attribute;

public class Section<T extends CharSequence & Annotatable<T>> extends TextAnnotation<T> {
  @Attribute("name")
  private final String name;

  public Section(int start, int end, String name) {
    super(start, end);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}

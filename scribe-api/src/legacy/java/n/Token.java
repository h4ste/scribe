package com.github.h4ste.scribe.n;

public class Token<T extends CharSequence & Annotatable<T>> extends TextAnnotation<T> {
  public Token(int start, int end) {
    super(start, end);
  }
}

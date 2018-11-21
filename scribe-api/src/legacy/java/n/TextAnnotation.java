//package com.github.h4ste.scribe.n;
//
//public abstract class TextAnnotation<T extends CharSequence & Annotatable<T>>  extends AbstractSpanAnnotation<T> implements CharSequence {
//  public TextAnnotation(int start, int end) {
//    super(start, end);
//  }
//
//  @Override
//  public final int length() {
//    return end - start;
//  }
//
//  @Override
//  public final char charAt(int index) {
//    return getSource().charAt(start + index);
//  }
//
//  @Override
//  public final CharSequence subSequence(int start, int end) {
//    return getSource().subSequence(this.start + start, this.start + end);
//  }
//
//  @Override
//  public final String toString() {
//    return getSource().subSequence(start, end).toString();
//  }
//}

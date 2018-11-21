//package com.github.h4ste.scribe.n2;
//
//public class Text<T extends CharSequence> extends Record<T> implements CharSequence {
//
//  Text(AnnotationManager annotations, RelationManager<Annotation> relations, T source, long nid, String id, String type) {
//    super(annotations, relations, source, nid, id, type);
//  }
//
//  @Override
//  public int length() {
//    return source.length();
//  }
//
//  @Override
//  public char charAt(int i) {
//    return source.charAt(i);
//  }
//
//  @Override
//  public CharSequence subSequence(int from, int to) {
//    return source.subSequence(from, to);
//  }
//}

//package com.github.h4ste.scribe.n;
//
//import com.github.h4ste.scribe.n2.Attribute;
//
//public abstract class AbstractSpanAnnotation<T extends CharSequence & Annotatable<T>> extends ManagedSpanAnnotation<T> {
//
//  @Attribute("start")
//  protected final int start;
//
//  @Attribute("end")
//  protected final int end;
//
//  public AbstractSpanAnnotation(T source, int start, int end) {
//    super(source.getAnnotationManager().getAnnotationImplementor());
//    this.start = start;
//    this.end = end;
//  }
//
//  @Override
//  public int getStart() {
//    return start;
//  }
//
//  @Override
//  public int getEnd() {
//    return end;
//  }
//}

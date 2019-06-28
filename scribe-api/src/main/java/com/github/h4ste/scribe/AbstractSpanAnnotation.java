package com.github.h4ste.scribe;

public abstract class AbstractSpanAnnotation extends SpanAnnotation {
  protected final int start;
  protected final int end;

  public AbstractSpanAnnotation( int start, int end) {
    this.start = start;
    this.end = end;
  }

  @Override
  public int getStart() {
    return start;
  }

  @Override
  public int getEnd() {
    return end;
  }

  @Override
  protected SpanAnnotationHandle implementor() {
    return (SpanAnnotationHandle) implementor;
  }

  @Override
  void setImplementor(SpanAnnotationHandle handle) {
    this.implementor = handle;
  }
}

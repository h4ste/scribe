package com.github.h4ste.scribe;

/* package-private */ class SpanImpl implements Span {
  final int start;
  final int end;

  public SpanImpl(int start, int end) {
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
}

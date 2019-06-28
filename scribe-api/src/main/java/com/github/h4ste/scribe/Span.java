package com.github.h4ste.scribe;

import java.util.Comparator;

public interface Span {
  int getStart();
  int getEnd();

  default int getLength() {
    return getEnd() - getStart();
  }

  static <I extends Span> Comparator<I> topologicalOrder() {
    return Comparator.<I>comparingInt(Span::getStart).<I>thenComparingInt(Span::getEnd);
  }

  default boolean contains(Span other) {
    return other.getStart() >= this.getStart() && other.getEnd() <= this.getEnd();
  }

  default boolean covers(Span other) {
    return other.getStart() <= this.getStart() && other.getEnd() >= this.getEnd();
  }

  default boolean topologicallyEquals(Span other) {
    return other.getStart() == this.getStart() && other.getEnd() == this.getEnd();
  }

  default boolean disjoins(Span other) {
    return other.getStart() >= this.getEnd() || other.getEnd() <= this.getStart();
  }

  default boolean touches(Span other) {
    return other.getStart() == this.getEnd() || other.getEnd() == this.getStart();
  }

  default boolean intersects(Span other) {
    return other.getStart() < this.getEnd() || other.getEnd() > this.getStart();
  }

  public static Span of(int start, int end){
    return new SpanImpl(start, end);
  }
}

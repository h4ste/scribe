package com.github.h4ste.scribe.n2;

import java.util.Comparator;

public interface IntInterval extends Describable {
  int getStart();

  int getEnd();


  default int getLength() {
    return getEnd() - getStart();
  }

  static <I extends IntInterval> Comparator<I> topologicalOrder() {
    return Comparator.<I>comparingInt(IntInterval::getStart).thenComparingInt(IntInterval::getEnd);
  }

  default boolean contains(IntInterval other) {
    return other.getStart() >= this.getStart() && other.getEnd() <= this.getEnd();
  }

  default boolean covers(IntInterval other) {
    return other.getStart() <= this.getStart() && other.getEnd() >= this.getEnd();
  }

  default boolean topologicallyEquals(IntInterval other) {
    return other.getStart() == this.getStart() && other.getEnd() == this.getEnd();
  }

  default boolean disjoins(IntInterval other) {
    return other.getStart() >= this.getEnd() || other.getEnd() <= this.getStart();
  }

  default boolean touches(IntInterval other) {
    return other.getStart() == this.getEnd() || other.getEnd() == this.getStart();
  }

  default boolean intersects(IntInterval other) {
    return other.getStart() < this.getEnd() || other.getEnd() > this.getStart();
  }

  default String describe() {
    return '[' + this.getStart() + ", " + this.getEnd() + ')';
  }
}

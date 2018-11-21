package com.github.h4ste.scribe.n;

public interface Span {
  int getStart();
  int getEnd();

  default int length() {
    return getEnd() - getStart();
  }
}

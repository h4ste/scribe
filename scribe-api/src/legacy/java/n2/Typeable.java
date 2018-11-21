package com.github.h4ste.scribe.n2;

public interface Typeable<T> extends Typed<T> {
  void setType(T type);
}

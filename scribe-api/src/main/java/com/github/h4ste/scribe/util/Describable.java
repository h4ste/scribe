package com.github.h4ste.scribe.util;

public interface Describable {
  default String describe() {
    return this.toString();
  }
}

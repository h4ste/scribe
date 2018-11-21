package com.github.h4ste.scribe.legacy.util;

import java.util.Set;

public interface IntIntervalMap<T> {
  Set<T> find(int start, int end);
  Set<T> findIntersecting(int start, int end);
  Set<T> findContained(int start, int end);
  Set<T> findStrictlyContained(int start, int end);
  Set<T> findContaining(int start, int end);
  Set<T> findStrictlyContaining(int start, int end);
}

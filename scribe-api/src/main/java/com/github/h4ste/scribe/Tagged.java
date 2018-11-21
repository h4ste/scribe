package com.github.h4ste.scribe;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

public interface Tagged<T> {
  Set<T> getTags();

  default Stream<T> tags() {
    return getTags().stream();
  }

  default boolean hasTag(T tag) {
    return getTags().contains(tag);
  }

  @SuppressWarnings("unchecked")
  default boolean hasTags(T... tags) {
    return hasTags(Arrays.asList(tags));
  }

  default boolean hasTags(Collection<T> tags) {
    return getTags().containsAll(tags);
  }
}

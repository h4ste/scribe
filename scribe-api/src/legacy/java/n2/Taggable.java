package com.github.h4ste.scribe.n2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public interface Taggable<T> {
  void addTag(T tag);

  default void addTags(Collection<? extends T> tags) {
    tags.forEach(this::addTag);
  }

  @SuppressWarnings("unchecked")
  default void addTags(T... tags) {
    addTags(Arrays.asList(tags));
  }

  boolean removeTag(T tag);
  default boolean removeTags(Collection<? extends T> tags) {
    boolean changed = false;
    for (T tag : tags) {
      changed |= removeTag(tag);
    }
    return changed;
  }

  @SuppressWarnings("unchecked")
  default boolean removeTags(T... tags) {
    boolean changed = false;
    for (T tag : tags) {
      changed |= removeTag(tag);
    }
    return changed;
  }

  Set<T> setTags(Collection<T> tags);

  @SuppressWarnings("unchecked")
  default Set<T> setTags(T... tags) {
    return setTags(Arrays.asList(tags));
  }

  default void clearTags() {
    setTags(Collections.emptyList());
  }

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

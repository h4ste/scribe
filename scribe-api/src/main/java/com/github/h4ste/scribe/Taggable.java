package com.github.h4ste.scribe;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public interface Taggable<T> extends Tagged<T> {
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








}
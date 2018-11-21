package com.github.h4ste.scribe.n2;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Created by travis on 8/13/14.
 */
public interface Attributeable {

  /**
   * Returns the number of keys in the map.
   */
  default int numAttributes() {
    return Attributes.getAttributeMap(this.getClass()).size();
  }

  default <T> Stream<T> mapAttributes(BiFunction<? super String, Object, ? extends T> mapper) {
    return Attributes.mapAttributes(this, mapper);
  }

  default void forEachAttribute(BiConsumer<? super String, Object> consumer) {
    Attributes.forEachAttribute(this, consumer);
  }
}

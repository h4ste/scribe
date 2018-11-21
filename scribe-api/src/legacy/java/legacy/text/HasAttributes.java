package com.github.h4ste.scribe.legacy.text;

import edu.utdallas.hltri.util.Unsafe;
import gate.FeatureMap;

import java.util.Optional;

/**
 * Created by rmm120030 on 8/10/15.
 */
public interface HasAttributes {
  FeatureMap getAttributes();

  default <T> Optional<T> get(String key) {
    return Optional.ofNullable(Unsafe.<T>cast(getAttributes().get(key)));
  }

  default <T> void set(String key, T value) {
    getAttributes().put(key, value);
  }
}

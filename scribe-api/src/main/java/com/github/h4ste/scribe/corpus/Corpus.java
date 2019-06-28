package com.github.h4ste.scribe.corpus;

import com.github.h4ste.scribe.Annotatable;
import java.io.Closeable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Corpus<K, D extends Annotatable> extends AutoCloseable {

  D load(final K key);

  default Stream<D> stream(final Collection<K> keys) {
    return keys.stream().map(this::load);
  }

  long size();

  boolean contains(final K key);

  D add(K key, D document);

  D remove(K key);

  Map<K, D> toMap();
}

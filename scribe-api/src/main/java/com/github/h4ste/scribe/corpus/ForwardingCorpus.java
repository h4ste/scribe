package com.github.h4ste.scribe.corpus;

import com.github.h4ste.scribe.Annotatable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public abstract class ForwardingCorpus<K, D extends Annotatable> extends ForwardingAutoCloseable implements Corpus<K, D> {

  /** Constructor for use by subclasses. */
  protected ForwardingCorpus() {}

  @Override
  protected abstract Corpus<K, D> delegate();

  @Override
  public D load(K key) {
    return delegate().load(key);
  }

  @Override
  public Stream<D> stream(Collection<K> keys) {
    return delegate().stream(keys);
  }

  @Override
  public long size() {
    return delegate().size();
  }

  @Override
  public boolean contains(K key) {
    return delegate().contains(key);
  }

  @Override
  public D add(K key, D document) {
    return delegate().add(key, document);
  }

  @Override
  public D remove(K key) {
    return delegate().remove(key);
  }

  @Override
  public Map<K, D> toMap() {
    return delegate().toMap();
  }
}

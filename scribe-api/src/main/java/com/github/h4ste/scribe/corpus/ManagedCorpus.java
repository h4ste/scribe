package com.github.h4ste.scribe.corpus;

import com.github.h4ste.scribe.Annotatable;
import com.google.common.collect.ForwardingMap;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class ManagedCorpus<K, D extends Annotatable> implements Corpus<K, D> {
  private CorpusHandle<K, D> handle;

  /* package-protected */ final boolean setHandle(CorpusHandle<K, D> handle) {
    final boolean changed = !Objects.equals(this.handle, handle);
    this.handle = handle;
    return changed;
  }

  @Override
  public final D load(K key) {
    return handle.load(key);
  }

  @Override
  public final void close() throws Exception {
    handle.close();
  }

  @Override
  public final Stream<D> stream(Collection<K> keys) {
    return handle.stream(keys);
  }

  @Override
  public final long size() {
    return handle.size();
  }

  @Override
  public final boolean contains(K key) {
    return handle.contains(key);
  }

  @Override
  public final D add(K key, D document) {
    return handle.add(key, document);
  }

  @Override
  public final D remove(K key) {
    return handle.remove(key);
  }

  @Override
  public final Map<K, D> toMap() {
    return handle.toMap();
  }
}

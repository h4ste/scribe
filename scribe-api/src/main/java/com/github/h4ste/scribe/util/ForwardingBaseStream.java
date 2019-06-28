package com.github.h4ste.scribe.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

public abstract class ForwardingBaseStream<T, S extends BaseStream<T, S>> implements BaseStream<T, S> {
  protected abstract S delegate();

  @Override
  public Iterator<T> iterator() {
    return delegate().iterator();
  }

  @Override
  public Spliterator<T> spliterator() {
    return delegate().spliterator();
  }

  @Override
  public boolean isParallel() {
    return delegate().isParallel();
  }

  @Override
  public S sequential() {
    return delegate().sequential();
  }

  @Override
  public S parallel() {
    return delegate().parallel();
  }

  @Override
  public S unordered() {
    return delegate().unordered();
  }

  @Override
  public S onClose(Runnable runnable) {
    return delegate().onClose(runnable);
  }

  @Override
  public void close() {
    delegate().close();
  }
}

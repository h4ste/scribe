package com.github.h4ste.scribe.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class WrappingForwardingStream<K, S extends WrappingForwardingStream<K, S>> extends ForwardingStream<K> {

  protected abstract S supply(Stream<K> stream);

  @Override
  public S filter(Predicate<? super K> predicate) {
    return supply(delegate().filter(predicate));
  }

  @Override
  public S distinct() {
    return supply(delegate().distinct());
  }

  @Override
  public S sorted() {
    return supply(delegate().sorted());
  }

  @Override
  public S sorted(Comparator<? super K> comparator) {
    return supply(delegate().sorted(comparator));
  }

  @Override
  public S peek(Consumer<? super K> consumer) {
    return supply(delegate().peek(consumer));
  }

  @Override
  public S limit(long l) {
    return supply(delegate().limit(l));
  }

  @Override
  public S skip(long l) {
    return supply(delegate().skip(l));
  }
}

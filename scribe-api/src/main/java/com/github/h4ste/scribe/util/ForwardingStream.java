package com.github.h4ste.scribe.util;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class ForwardingStream<K> extends ForwardingBaseStream<K, Stream<K>> implements Stream<K> {

  @Override
  protected abstract Stream<K> delegate();

  @Override
  public Stream<K> filter(Predicate<? super K> predicate) {
    return delegate().filter(predicate);
  }

  @Override
  public <R> Stream<R> map(Function<? super K, ? extends R> function) {
    return delegate().map(function);
  }

  @Override
  public IntStream mapToInt(ToIntFunction<? super K> toIntFunction) {
    return delegate().mapToInt(toIntFunction);
  }

  @Override
  public LongStream mapToLong(ToLongFunction<? super K> toLongFunction) {
    return delegate().mapToLong(toLongFunction);
  }

  @Override
  public DoubleStream mapToDouble(ToDoubleFunction<? super K> toDoubleFunction) {
    return delegate().mapToDouble(toDoubleFunction);
  }

  @Override
  public <R> Stream<R> flatMap(Function<? super K, ? extends Stream<? extends R>> function) {
    return delegate().flatMap(function);
  }

  @Override
  public IntStream flatMapToInt(Function<? super K, ? extends IntStream> function) {
    return delegate().flatMapToInt(function);
  }

  @Override
  public LongStream flatMapToLong(Function<? super K, ? extends LongStream> function) {
    return delegate().flatMapToLong(function);
  }

  @Override
  public DoubleStream flatMapToDouble(Function<? super K, ? extends DoubleStream> function) {
    return delegate().flatMapToDouble(function);
  }

  @Override
  public Stream<K> distinct() {
    return delegate().distinct();
  }

  @Override
  public Stream<K> sorted() {
    return delegate().sorted();
  }

  @Override
  public Stream<K> sorted(Comparator<? super K> comparator) {
    return delegate().sorted(comparator);
  }

  @Override
  public Stream<K> peek(Consumer<? super K> consumer) {
    return delegate().peek(consumer);
  }

  @Override
  public Stream<K> limit(long l) {
    return delegate().limit(l);
  }

  @Override
  public Stream<K> skip(long l) {
    return delegate().skip(l);
  }

  @Override
  public Stream<K> takeWhile(Predicate<? super K> predicate) {
    return delegate().takeWhile(predicate);
  }

  @Override
  public Stream<K> dropWhile(Predicate<? super K> predicate) {
    return delegate().dropWhile(predicate);
  }

  @Override
  public void forEach(Consumer<? super K> consumer) {
    delegate().forEach(consumer);
  }

  @Override
  public void forEachOrdered(Consumer<? super K> consumer) {
    delegate().forEachOrdered(consumer);
  }

  @Override
  public Object[] toArray() {
    return delegate().toArray();
  }

  @Override
  public <A> A[] toArray(IntFunction<A[]> intFunction) {
    return delegate().toArray(intFunction);
  }

  @Override
  public K reduce(K k, BinaryOperator<K> binaryOperator) {
    return delegate().reduce(k, binaryOperator);
  }

  @Override
  public Optional<K> reduce(BinaryOperator<K> binaryOperator) {
    return delegate().reduce(binaryOperator);
  }

  @Override
  public <U> U reduce(U u, BiFunction<U, ? super K, U> biFunction, BinaryOperator<U> binaryOperator) {
    return delegate().reduce(u, biFunction, binaryOperator);
  }

  @Override
  public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super K> biConsumer, BiConsumer<R, R> biConsumer1) {
    return delegate().collect(supplier, biConsumer, biConsumer1);
  }

  @Override
  public <R, A> R collect(Collector<? super K, A, R> collector) {
    return delegate().collect(collector);
  }

  @Override
  public Optional<K> min(Comparator<? super K> comparator) {
    return delegate().min(comparator);
  }

  @Override
  public Optional<K> max(Comparator<? super K> comparator) {
    return delegate().max(comparator);
  }

  @Override
  public long count() {
    return delegate().count();
  }

  @Override
  public boolean anyMatch(Predicate<? super K> predicate) {
    return delegate().anyMatch(predicate);
  }

  @Override
  public boolean allMatch(Predicate<? super K> predicate) {
    return delegate().allMatch(predicate);
  }

  @Override
  public boolean noneMatch(Predicate<? super K> predicate) {
    return delegate().noneMatch(predicate);
  }

  @Override
  public Optional<K> findFirst() {
    return delegate().findFirst();
  }

  @Override
  public Optional<K> findAny() {
    return delegate().findAny();
  }
}

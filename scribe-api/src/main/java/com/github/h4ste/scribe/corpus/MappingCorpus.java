//package com.github.h4ste.scribe.corpus;
//
//import com.github.h4ste.scribe.Annotatable;
//import java.util.Collection;
//import java.util.function.Function;
//import java.util.stream.Stream;
//
//public class MappingCorpus<T, K, D extends Annotatable, S extends Corpus<K, D>> implements S {
//  final Corpus<K, D> delegate;
//  final Function<T, K> mappingFunction;
//
//  public MappingCorpus(Corpus<K, D> delegate, Function<T, K> mappingFunction) {
//    this.delegate = delegate;
//  }
//
//  @Override
//  public D load(T key) {
//    return delegate.load(mappingFunction.apply(key));
//  }
//
//  @Override
//  public Stream<D> stream(Collection<T> keys) {
//    return keys.stream().map(mappingFunction).map(delegate::load);
//  }
//
//  @Override
//  public boolean contains(T key) {
//    return delegate.contains(mappingFunction.apply(key));
//  }
//
//  @Override
//  public D add(T key, D document) {
//    return super.key(key, document);
//  }
//
//  @Override
//  public D remove(T key) {
//    return super.remove(key);
//  }
//}

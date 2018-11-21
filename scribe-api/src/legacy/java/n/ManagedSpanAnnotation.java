//package com.github.h4ste.scribe.n;
//
//import java.util.Collection;
//import java.util.List;
//
//public abstract class ManagedSpanAnnotation<T extends CharSequence & Annotatable<T>> extends ManagedAnnotation<T, SpanAnnotation<T>> implements SpanAnnotation<T> {
//
//  @Override
//  public final AnnotationStream<SpanAnnotation<T>> contained() {
//    return delegate().contained();
//  }
//
//  @Override
//  public final List<SpanAnnotation<T>> getContained() {
//    return delegate().getContained();
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getContained(Class<A> type) {
//    return delegate().getContained(type);
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getContained(Class<A> type, String... tags) {
//    return delegate().getContained(type, tags);
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getContained(Class<A> type,
//      Collection<String> tags) {
//    return delegate().getContained(type, tags);
//  }
//
//  @Override
//  public final AnnotationStream<SpanAnnotation<T>> containing() {
//    return delegate().containing();
//  }
//
//  @Override
//  public final List<SpanAnnotation<T>> getContaining() {
//    return delegate().getContaining();
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getContaining(Class<A> type) {
//    return delegate().getContaining(type);
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getContaining(Class<A> type,
//      String... tags) {
//    return delegate().getContaining(type, tags);
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getContaining(Class<A> type,
//      Collection<String> tags) {
//    return delegate().getContaining(type, tags);
//  }
//
//  @Override
//  public final AnnotationStream<SpanAnnotation<T>> intersecting() {
//    return delegate().intersecting();
//  }
//
//  @Override
//  public final List<SpanAnnotation<T>> getIntersecting() {
//    return delegate().getIntersecting();
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getIntersecting(Class<A> type) {
//    return delegate().getIntersecting(type);
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getIntersecting(Class<A> type,
//      String... tags) {
//    return delegate().getIntersecting(type, tags);
//  }
//
//  @Override
//  public final <A extends SpanAnnotation<T>> List<A> getIntersecting(Class<A> type,
//      Collection<String> tags) {
//    return delegate().getIntersecting(type, tags);
//  }
//
//}

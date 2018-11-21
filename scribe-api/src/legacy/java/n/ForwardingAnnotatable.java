package com.github.h4ste.scribe.n;

import java.util.Collection;
import java.util.List;

public abstract class ForwardingAnnotatable<S extends Annotatable> implements Annotatable<S> {

  protected abstract Annotatable<S> delegate();


  @Override
  public void addAnnotation(Annotation<? super S> annotation) {
    delegate().addAnnotation(annotation);
  }

  @Override
  public boolean removeAnnotation(Annotation<? super S> annotation) {
    return delegate().removeAnnotation(annotation);
  }

  @Override
  public AnnotationStream<Annotation<? super S>> annotations(Collection<String> tags) {
    return delegate().annotations(tags);
  }

  @Override
  public AnnotationStream<Annotation<? super S>> annotations(String... tags) {
    return delegate().annotations(tags);
  }

  @Override
  public <A extends Annotation<? super S>> List<A> getAnnotations(Class<A> type, String... tags) {
    return delegate().getAnnotations(type, tags);
  }

  @Override
  public <A extends Annotation<? super S>> List<A> getAnnotations(Class<A> type,
      Collection<String> tags) {
    return delegate().getAnnotations(type, tags);
  }
}

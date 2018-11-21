package com.github.h4ste.scribe.n;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public abstract class ManagedAnnotation<T extends Annotatable<T>, S extends Annotation<T>> implements Annotation<T> {

  private transient S delegate;

  protected ManagedAnnotation(S delegate) {
    this.delegate = delegate;
  }

  S delegate() {
    return delegate;
  }

  void setDelegate(S delegate) {
    this.delegate = delegate;
  }

  @Override
  public final T getSource() {
    return delegate().getSource();
  }

  @Override
  public AnnotationManager<T> getAnnotationManager() {
    return delegate.getAnnotationManager();
  }

  @Override
  public void addAnnotation(Annotation<T> annotation) {
    delegate.addAnnotation(annotation);
  }

  @Override
  public boolean removeAnnotation(Annotation<T> annotation) {
    return delegate.removeAnnotation(annotation);
  }

  @Override
  public AnnotationStream<Annotation<T>> annotations(Collection<String> tags) {
    return delegate.annotations(tags);
  }

  @Override
  public AnnotationStream<Annotation<T>> annotations(String... tags) {
    return delegate.annotations(tags);
  }

  @Override
  public <A extends Annotation<T>> List<A> getAnnotations(Class<A> type, String... tags) {
    return delegate.getAnnotations(type, tags);
  }

  @Override
  public <A extends Annotation<T>> List<A> getAnnotations(Class<A> type,
      Collection<String> tags) {
    return delegate.getAnnotations(type, tags);
  }

  @Override
  public void addTag(String tag) {
    delegate.addTag(tag);
  }

  @Override
  public void addTags(Collection<? extends String> tags) {
    delegate.addTags(tags);
  }

  @Override
  public void addTags(String... tags) {
    delegate.addTags(tags);
  }

  @Override
  public boolean removeTag(String tag) {
    return delegate.removeTag(tag);
  }

  @Override
  public boolean removeTags(Collection<? extends String> tags) {
    return delegate.removeTags(tags);
  }

  @Override
  public boolean removeTags(String... tags) {
    return delegate.removeTags(tags);
  }

  @Override
  public Set<String> setTags(Collection<String> tags) {
    return delegate.setTags(tags);
  }

  @Override
  public Set<String> setTags(String... tags) {
    return delegate.setTags(tags);
  }

  @Override
  public void clearTags() {
    delegate.clearTags();
  }

  @Override
  public Set<String> getTags() {
    return delegate.getTags();
  }

  @Override
  public Stream<String> tags() {
    return delegate.tags();
  }

  @Override
  public boolean hasTag(String tag) {
    return delegate.hasTag(tag);
  }

  @Override
  public boolean hasTags(String... tags) {
    return delegate.hasTags(tags);
  }

  @Override
  public boolean hasTags(Collection<String> tags) {
    return delegate.hasTags(tags);
  }
}

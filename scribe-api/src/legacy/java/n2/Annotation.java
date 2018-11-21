package com.github.h4ste.scribe.n2;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class Annotation implements AnnotationLike, Attributeable {

  private AnnotationDelegate delegate;

  /* package protected */ void setDelegate(AnnotationDelegate delegate) {
    this.delegate = Objects.requireNonNull(delegate);
  }

  AnnotationDelegate getDelegate() {
    return this.delegate;
  }

  public static <A extends AnnotationLike> Comparator<A> documentOrder() {
    return IntInterval.<A>topologicalOrder().thenComparingLong(NumericIdentifiable::getNid);
  }

  @Override
  public final AnnotationManager getManager() {
    return delegate.getManager();
  }

  @Override
  public final int getStart() {
    return delegate.getStart();
  }

  @Override
  public final int getEnd() {
    return delegate.getEnd();
  }

  @Override
  public final long getNid() {
    return delegate.getNid();
  }

  @Override
  public final void addTag(String tag) {
    delegate.addTag(tag);
  }

  @Override
  public final void addTags(Collection<? extends String> tags) {
    delegate.addTags(tags);
  }

  @Override
  public final void addTags(String... tags) {
    delegate.addTags(tags);
  }

  @Override
  public final boolean removeTag(String tag) {
    return delegate.removeTag(tag);
  }

  @Override
  public final boolean removeTags(Collection<? extends String> tags) {
    return delegate.removeTags(tags);
  }

  @Override
  public final boolean removeTags(String... tags) {
    return delegate.removeTags(tags);
  }

  @Override
  public final Set<String> setTags(Collection<String> tags) {
    return delegate.setTags(tags);
  }

  @Override
  public final Set<String> getTags() {
    return delegate.getTags();
  }

  @Override
  public final Set<String> setTags(String... tags) {
    return delegate.setTags(tags);
  }

  @Override
  public final void clearTags() {
    delegate.clearTags();
  }

  @Override
  public final Stream<String> tags() {
    return delegate.tags();
  }

  @Override
  public final boolean hasTag(String tag) {
    return delegate.hasTag(tag);
  }

  @Override
  public final boolean hasTags(String... tags) {
    return delegate.hasTags(tags);
  }

  @Override
  public final boolean hasTags(Collection<String> tags) {
    return delegate.hasTags(tags);
  }

  @Override
  public final int getLength() {
    return delegate.getLength();
  }

  /**
   * Checks if the passed interval is contained within this one.
   *
   * @param other the other interval
   * @return true if the passed interval is contained within this one, false otherwise.
   */
  @Override
  public final boolean contains(IntInterval other) {
    return delegate.contains(other);
  }

  @Override
  public final boolean covers(IntInterval other) {
    return delegate.covers(other);
  }

  @Override
  public final boolean topologicallyEquals(IntInterval other) {
    return delegate.topologicallyEquals(other);
  }

  @Override
  public final boolean disjoins(IntInterval other) {
    return delegate.disjoins(other);
  }

  @Override
  public final boolean touches(IntInterval other) {
    return delegate.touches(other);
  }

  @Override
  public final boolean intersects(IntInterval other) {
    return delegate.intersects(other);
  }

  @Override
  public final AnnotationStream<Annotation> contained() { return delegate.contained(); }

  @Override
  public final List<Annotation> getContained() {
    return delegate.getContained();
  }

  @Override
  public final <A extends Annotation> List<A> getContained(Class<A> type) {
    return delegate.getContained(type);
  }

  @Override
  public final <A extends Annotation> List<A> getContained(Class<A> type, String... tags) {
    return delegate.getContained(type, tags);
  }

  @Override
  public final <A extends Annotation> List<A> getContained(Class<A> type, Collection<String> tags) {
    return delegate.getContained(type, tags);
  }

  @Override
  public final AnnotationStream<Annotation> containing() {
    return delegate.containing();
  }

  @Override
  public final List<Annotation> getContaining() {
    return delegate.getContaining();
  }

  @Override
  public final <A extends Annotation> List<A> getContaining(Class<A> type) {
    return delegate.getContaining(type);
  }

  @Override
  public final <A extends Annotation> List<A> getContaining(Class<A> type, String... tags) {
    return delegate.getContaining(type, tags);
  }

  @Override
  public <A extends Annotation> List<A> getContaining(Class<A> type, Collection<String> tags) {
    return delegate.getContaining(type, tags);
  }

  @Override
  public final AnnotationStream<Annotation> intersecting() {
    return delegate.intersecting();
  }

  @Override
  public final List<Annotation> getIntersecting() {
    return delegate.getIntersecting();
  }

  @Override
  public final <A extends Annotation> List<A> getIntersecting(Class<A> type) {
    return delegate.getIntersecting(type);
  }

  @Override
  public final <A extends Annotation> List<A> getIntersecting(Class<A> type, String... tags) {
    return delegate.getIntersecting(type, tags);
  }

  @Override
  public final <A extends Annotation> List<A> getIntersecting(Class<A> type, Collection<String> tags) {
    return delegate.getIntersecting(type, tags);
  }

  public String getAnnotationType() {
    return AnnotationTypes.getAnnotationType(this.getClass());
  }

  @Override
  public String getId() {
    return getAnnotationType() + '#' + getNid();
  }

  @Override
  public String describe() {
    return AnnotationLike.super.describe()
        + mapAttributes((attr, value) -> attr + ": " + value)
        .collect(Collectors.joining("; ", "[", "]"));
  }
}

package com.github.h4ste.scribe;

import de.uka.ilkd.pp.DataLayouter;
import de.uka.ilkd.pp.NoExceptions;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class SpanAnnotation extends AbstractAnnotation implements
    BaseSpanAnnotation {

  @Override
  protected abstract SpanAnnotationHandle implementor();

  abstract void setImplementor(SpanAnnotationHandle handle);

  @Override
  public final AnnotationStream<SpanAnnotation> contained() {
    return implementor().contained();
  }

  @Override
  public final List<SpanAnnotation> getContained() {
    return implementor().getContained();
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getContained(Class<A> type) {
    return implementor().getContained(type);
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getContained(Class<A> type,
      String... tags) {
    return implementor().getContained(type, tags);
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getContained(Class<A> type,
      Collection<String> tags) {
    return implementor().getContained(type, tags);
  }

  @Override
  public final AnnotationStream<SpanAnnotation> containing() {
    return implementor().containing();
  }

  @Override
  public final List<SpanAnnotation> getContaining() {
    return implementor().getContaining();
  }

  @Override
  public final <A extends SpanAnnotation> Optional<A> getParent(Class<A> type,
      String... tags) {
    return implementor().getParent(type, tags);
  }

  @Override
  public final <A extends SpanAnnotation> Optional<A> getParent(Class<A> type,
      Collection<String> tags) {
    return implementor().getParent(type, tags);
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getContaining(Class<A> type) {
    return implementor().getContaining(type);
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getContaining(Class<A> type,
      String... tags) {
    return implementor().getContaining(type, tags);
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getContaining(Class<A> type,
      Collection<String> tags) {
    return implementor().getContaining(type, tags);
  }

  @Override
  public final AnnotationStream<SpanAnnotation> intersecting() {
    return implementor().intersecting();
  }

  @Override
  public final List<SpanAnnotation> getIntersecting() {
    return implementor().getIntersecting();
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getIntersecting(Class<A> type) {
    return implementor().getIntersecting(type);
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getIntersecting(Class<A> type,
      String... tags) {
    return implementor().getIntersecting(type, tags);
  }

  @Override
  public final <A extends SpanAnnotation> List<A> getIntersecting(Class<A> type,
      Collection<String> tags) {
    return implementor().getIntersecting(type, tags);
  }

  @Override
  protected void printAnnotationTo(DataLayouter<NoExceptions> layout) {
    super.printAnnotationTo(layout);
    layout.print("@[").print(getStart()).print(",").print(getEnd()).print("]");
  }
}

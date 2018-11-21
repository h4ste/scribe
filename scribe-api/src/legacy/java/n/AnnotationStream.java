package com.github.h4ste.scribe.n;

import com.github.h4ste.scribe.util.WrappingForwardingStream;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class AnnotationStream<A extends Annotation<?>> extends
    WrappingForwardingStream<A, AnnotationStream<A>> {

  private final Stream<A> stream;

  private AnnotationStream(Stream<A> stream) {
    this.stream = stream;
  }

  @Override
  protected AnnotationStream<A> supply(Stream<A> stream) {
    return new AnnotationStream<>(stream);
  }

  @Override
  protected Stream<A> delegate() {
    return stream;
  }

  public static <T, R extends Annotation<T>> AnnotationStream<R> of(Stream<R> stream) {
    if (stream instanceof AnnotationStream) {
      return (AnnotationStream<R>) stream;
    }
    return new AnnotationStream<>(stream);
  }

  public <T, R extends Annotation<T>> AnnotationStream<R> mapAnnotation(Function<? super A, ? extends R> function) {
    return new AnnotationStream<>(super.map(function));
  }

  public <T, R extends Annotation<T>> AnnotationStream<R> flatMapAnnotation(Function<? super A, ? extends Stream<? extends R>> function) {
    return new AnnotationStream<>(super.flatMap(function));
  }

  public <T, R extends Annotation<T>> AnnotationStream<R> withType(Class<R> type) {
    //noinspection unchecked
    return filter(type::isInstance).mapAnnotation(type::cast);
  }

  public AnnotationStream<A> withTag(String tag) {
    return filter(k -> k.hasTag(tag));
  }

  public AnnotationStream<A> withTags(String... tags) {
    return filter(k -> k.hasTags(tags));
  }

  public AnnotationStream<A> withTags(Collection<String> tags) {
    return filter(k -> k.hasTags(tags));
  }
}

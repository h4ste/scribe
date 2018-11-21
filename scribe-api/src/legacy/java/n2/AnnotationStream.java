package com.github.h4ste.scribe.n2;

import com.github.h4ste.scribe.util.WrappingForwardingStream;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class AnnotationStream<A extends Annotation> extends
    WrappingForwardingStream<A, AnnotationStream<A>> {

  private final Stream<A> stream;

  private AnnotationStream(Stream<A> stream) {
    this.stream = stream;
  }

  @Override
  AnnotationStream<A> supply(Stream<A> stream) {
    return new AnnotationStream<>(stream);
  }

  @Override
  protected Stream<A> delegate() {
    return stream;
  }

  public static <T extends Annotation> AnnotationStream<T> of(Stream<T> stream) {
    if (stream instanceof AnnotationStream) {
      return (AnnotationStream<T>) stream;
    }
    return new AnnotationStream<>(stream);
  }

  public <R extends Annotation> AnnotationStream<R> mapAnnotation(Function<? super A, ? extends R> function) {
    return new AnnotationStream<>(super.map(function));
  }

  public <R extends Annotation> AnnotationStream<R> flatMapAnnotation(Function<? super A, ? extends Stream<? extends R>> function) {
    return new AnnotationStream<>(super.flatMap(function));
  }

  public <T extends A> AnnotationStream<T> withType(Class<T> type) {
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

package com.github.h4ste.scribe.n2;

import com.github.h4ste.scribe.util.WrappingForwardingStream;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class RelationStream<R extends Relation<? extends Annotation, ? extends Annotation>> extends
    WrappingForwardingStream<R, RelationStream<R>> {

  private final Stream<R> stream;

  private RelationStream(Stream<R> stream) {
    this.stream = stream;
  }

  @Override
  protected RelationStream<R> supply(Stream<R> stream) {
    return new RelationStream<>(stream);
  }

  @Override
  protected Stream<R> delegate() {
    return stream;
  }

  public static <R extends Relation<S, T>, S extends Annotation, T extends Annotation> RelationStream<R> of(Stream<R> stream) {
    return new RelationStream<>(stream);
  }

  public <R2 extends R> RelationStream<R2> withType(Class<R2> type) {
    return new RelationStream<>(filter(type::isInstance).map(type::cast));
  }

  public <R2 extends Relation<S, T>, S extends Annotation, T extends Annotation> RelationStream<R2> withSourceType(Class<R2> relationType, Class<S> sourceType) {
    return new RelationStream<>(
        filter(r -> relationType.isInstance(r) && sourceType.isInstance(r.getSource()))
        .map(r -> (R2) r)
    );
  }

  public <R2 extends Relation<S, T>, S extends Annotation, T extends Annotation> RelationStream<R2> withTargetType(Class<R2> relationType, Class<T> targetType) {
    return new RelationStream<>(
        filter(r -> relationType.isInstance(r) && targetType.isInstance(r.getTarget()))
        .map(r -> (R2) r)
    );
  }

  public <R2 extends Relation<S, T>, S extends Annotation, T extends Annotation> RelationStream<R2> withTypes(Class<R2> relationType, Class<S> sourceType, Class<T> targetType) {
    return new RelationStream<>(
        filter(r -> relationType.isInstance(r)
            && sourceType.isInstance(r.getSource())
            && targetType.isInstance(r.getTarget()))
        .map(r -> (R2) r)
    );
  }

  public RelationStream<R> withTag(String tag) {
    return filter(k -> k.hasTag(tag));
  }

  public RelationStream<R> withTags(String... tags) {
    return filter(k -> k.hasTags(tags));
  }

  public RelationStream<R> withTags(Collection<String> tags) {
    return filter(k -> k.hasTags(tags));
  }

  public <A extends Annotation> AnnotationStream<A> mapToAnnotation(Function<? super R, ? extends A> mapper) {
    return AnnotationStream.of(this.map(mapper));
  }

  public <A extends Annotation> AnnotationStream<A> flatMapToAnnotation(Function<? super R, ? extends Stream<? extends A>> mapper) {
    return AnnotationStream.of(this.flatMap(mapper));
  }

}

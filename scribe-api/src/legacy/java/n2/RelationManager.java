package com.github.h4ste.scribe.n2;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface RelationManager<A extends Annotation> extends Describable {

  RelationStream<Relation<A, A>> relations();

  default <R extends Relation<A, A>>
  List<R> getRelations(Class<R> type, String tag) {
    return relations().withType(type).withTag(tag).collect(Collectors.toUnmodifiableList());
  }

  default <R extends Relation<A, A>>
  List<R> getRelations(Class<R> type, Collection<String> tags) {
    return relations().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  default <R extends Relation<A, A>>
  List<R> getRelations(Class<R> type, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return relations().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  <S extends A> RelationStream<Relation<S, A>> targetRelations(final S source);

  default <S extends A, R extends Relation<S, A>> List<R> getTargetRelations(final S source, Class<R> type, String tag) {
    return targetRelations(source).withTag(tag).withType(type).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, A>> List<R> getTargetRelations(final S source, Class<R> type, Collection<String> tags) {
    return targetRelations(source).withTags(tags).withType(type).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, A>> List<R> getTargetRelations(final S source, Class<R> type, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return targetRelations(source).withTags(tags).withType(type).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<R> getTargetRelations(final S source, Class<R> type, Class<T> targetType, String tag) {
    return targetRelations(source).withTag(tag).withTargetType(type, targetType).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<R> getTargetRelations(final S source, Class<R> type, Class<T> targetType, Collection<String> tags) {
    return targetRelations(source).withTags(tags).withTargetType(type, targetType).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<R> getTargetRelations(final S source, Class<R> type, Class<T> targetType, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return targetRelations(source).withTags(tags).withTargetType(type, targetType).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, A>> List<A> getTargets(final S source, Class<R> type, String tag) {
    return targetRelations(source).withTag(tag).withType(type).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, A>> List<A> getTargets(final S source, Class<R> type, Collection<String> tags) {
    return targetRelations(source).withTags(tags).withType(type).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, A>> List<A> getTargets(final S source, Class<R> type, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return targetRelations(source).withTags(tags).withType(type).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<T> getTargets(final S source, Class<R> type, Class<T> targetType, String tag) {
    return targetRelations(source).withTag(tag).withTargetType(type, targetType).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<T> getTargets(final S source, Class<R> type, Class<T> targetType, Collection<String> tags) {
    return targetRelations(source).withTags(tags).withTargetType(type, targetType).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<T> getTargets(final S source, Class<R> type, Class<T> targetType, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return targetRelations(source).withTags(tags).withTargetType(type, targetType).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }


  <T extends A> RelationStream<Relation<A, T>> sourceRelations(final T dependant);

  default <T extends A, R extends Relation<A, T>> List<R> getSourceRelations(final T target, Class<R> type, String tag) {
    return sourceRelations(target).withTag(tag).withType(type).collect(Collectors.toUnmodifiableList());
  }

  default <T extends A, R extends Relation<A, T>> List<R> getSourceRelations(final T target, Class<R> type, Collection<String> tags) {
    return sourceRelations(target).withTags(tags).withType(type).collect(Collectors.toUnmodifiableList());
  }

  default <T extends A, R extends Relation<A, T>> List<R> getSourceRelations(final T target, Class<R> type, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return sourceRelations(target).withTags(tags).withType(type).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<R> getSourceRelations(final T target, Class<R> type, Class<S> sourceType, String tag) {
    return sourceRelations(target).withTag(tag).withSourceType(type, sourceType).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<R> getSourceRelations(final T target, Class<R> type, Class<S> sourceType, Collection<String> tags) {
    return sourceRelations(target).withTags(tags).withSourceType(type, sourceType).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<R> getSourceRelations(final T target, Class<R> type, Class<S> sourceType, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return sourceRelations(target).withTags(tags).withSourceType(type, sourceType).collect(Collectors.toUnmodifiableList());
  }

  default <T extends A, R extends Relation<A, T>> List<A> getSources(final T target, Class<R> type, String tag) {
    return sourceRelations(target).withTag(tag).withType(type).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <T extends A, R extends Relation<A, T>> List<A> getSources(final T target, Class<R> type, Collection<String> tags) {
    return sourceRelations(target).withTags(tags).withType(type).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <T extends A, R extends Relation<A, T>> List<A> getSources(final T target, Class<R> type, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return sourceRelations(target).withTags(tags).withType(type).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<T> getSources(final T target, Class<R> type, Class<S> sourceType, String tag) {
    return sourceRelations(target).withTag(tag).withSourceType(type, sourceType).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<T> getSources(final T target, Class<R> type, Class<S> sourceType, Collection<String> tags) {
    return sourceRelations(target).withTags(tags).withSourceType(type, sourceType).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  default <S extends A, R extends Relation<S, T>, T extends A> List<T> getSources(final T target, Class<R> type, Class<S> sourceType, String tag1, String tag2, String... tagv) {
    String[] tags = new String[ tagv.length + 2 ];
    tags[0] = tag1;
    tags[1] = tag2;
    if (tagv.length > 0) {
      System.arraycopy(tagv, 0, tags, 2, tagv.length);
    }
    return sourceRelations(target).withTags(tags).withSourceType(type, sourceType).mapToAnnotation(R::getTarget).collect(Collectors.toUnmodifiableList());
  }

  void addRelation(Relation<? extends A, ? extends A> relation);
  boolean removeRelation(Relation<? extends A, ? extends A> relation);

  @Override
  default String describe() {
    return relations()
        .map(Relation::describe)
        .collect(Collectors.joining("\n • ", "Relations:\n • ", "\n"));
  }
}

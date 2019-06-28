package com.github.h4ste.scribe;

import com.github.h4ste.scribe.util.Describable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface SpanAnnotationHandle extends AnnotationHandle, SpanAnnotationLike {

  @Override
  SpanAnnotation getHandler();

  @Override
  default List<SpanAnnotation> getContained() {
    return contained().collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getContained(
      Class<A> type) {
    return contained().withType(type).collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getContained(
      Class<A> type, String... tags) {
    return contained().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getContained(
      Class<A> type, Collection<String> tags) {
    return contained().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> Optional<A> getParent(Class<A> type, String... tags) {
    final List<A> containing = getContaining(type, tags);
    if (containing.isEmpty()) {
      return Optional.empty();
    } else if (containing.size() == 1) {
      return Optional.of(containing.get(0));
    } else {
      throw new UnsupportedOperationException("Annotation " + getHandler().describe()
          + " had multiple parents of type " + type.toString()
          + " with tags " + Arrays.toString(tags));
    }
  }

  @Override
  default <A extends SpanAnnotation> Optional<A> getParent(Class<A> type, Collection<String> tags) {
    final List<A> containing = getContaining(type, tags);
    if (containing.isEmpty()) {
      return Optional.empty();
    } else if (containing.size() == 1) {
      return Optional.of(containing.get(0));
    } else {
      throw new UnsupportedOperationException("Annotation " + getHandler().describe()
          + " had multiple parents of type " + type.toString()
          + " with tags " + tags.toString());
    }
  }

  @Override
  default List<SpanAnnotation> getContaining() {
    return containing().collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getContaining(
      Class<A> type) {
    return containing().withType(type).collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getContaining(
      Class<A> type, String... tags) {
    return containing().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getContaining(
      Class<A> type, Collection<String> tags) {
    return containing().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  @Override
  default List<SpanAnnotation> getIntersecting() {
    return intersecting().collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getIntersecting(
      Class<A> type) {
    return intersecting().withType(type).collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getIntersecting(
      Class<A> type, String... tags) {
    return intersecting().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  @Override
  default <A extends SpanAnnotation> List<A> getIntersecting(
      Class<A> type, Collection<String> tags) {
    return intersecting().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }
}

package com.github.h4ste.scribe.n;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface SpanAnnotation<T extends Annotatable<T>> extends Annotation<T>, Span {

  /**
   * Returns a stream of annotations that are fully contained by this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       returned
   *           *---------------------*    not returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return Stream of annotations which are contained by this annotation
   */
  AnnotationStream<SpanAnnotation<T>> contained();

  /**
   * Returns a list of annotations that are fully contained by this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       returned
   *           *---------------------*    not returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return List of annotations which are contained by this annotation
   */
  default List<SpanAnnotation<T>> getContained() {
    return contained().collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations castable to the given type that are fully contained by this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       returned
   *           *---------------------*    not returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return List of annotations of the given type (or a subtype) which are contained by this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getContained(Class<A> type) {
    return contained().withType(type).collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations castable to the given type with the given tags that are fully contained by this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       returned
   *           *---------------------*    not returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return List of annotations of the given type (or a subtype) and with the given tags which are contained by this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getContained(Class<A> type, String... tags) {
    return contained().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations castable to the given type with the given tags that are fully contained by this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       returned
   *           *---------------------*    not returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return List of annotations of the given type (or a subtype) and with the given tags which are contained by this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getContained(Class<A> type, Collection<String> tags) {
    return contained().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a stream of annotations that fully contain this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       not returned
   *           *---------------------*    returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return Stream of annotations which fully contain this annotation
   */
  AnnotationStream<SpanAnnotation<T>> containing();

  /**
   * Returns a list of annotations that fully contain this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       not returned
   *           *---------------------*    returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return List of annotations which fully contain this annotation
   */
  default List<SpanAnnotation<T>> getContaining() {
    return containing().collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations that extend the given type and fully contain this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       not returned
   *           *---------------------*    returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return List of annotations that extend the given type and fully contain this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getContaining(Class<A> type) {
    return containing().withType(type).collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations that extend the given type, have the given tags, and fully contain this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       not returned
   *           *---------------------*    returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return List of annotations that extend the given type, have the given tags, and fully contain this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getContaining(Class<A> type, String... tags) {
    return containing().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations that extend the given type, have the given tags, and fully contain this annotation
   *
   * this:       *-----------------*
   *          *------------*              not returned
   *                *-------------*       not returned
   *           *---------------------*    returned
   *        *----*                        not returned
   *                               *-*    not returned
   * @return List of annotations that extend the given type, have the given tags, and fully contain this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getContaining(Class<A> type, Collection<String> tags) {
    return containing().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a stream of annotations that intersect (overlap) this annotation
   *
   * this:       *-----------------*
   *          *------------*              returned
   *                *-------------*       not returned
   *           *---------------------*    not returned
   *        *----*                        returned
   *                               *-*    returned
   * @return Stream of annotations that intersect with this annotation
   */
  AnnotationStream<SpanAnnotation<T>> intersecting();

  /**
   * Returns a list of annotations that intersect (overlap) this annotation
   *
   * this:       *-----------------*
   *          *------------*              returned
   *                *-------------*       not returned
   *           *---------------------*    not returned
   *        *----*                        returned
   *                               *-*    returned
   * @return List of annotations that intersect with this annotation
   */
  default List<SpanAnnotation<T>> getIntersecting() {
    return intersecting().collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations that extend the given type and intersect (overlap) this annotation
   *
   * this:       *-----------------*
   *          *------------*              returned
   *                *-------------*       not returned
   *           *---------------------*    not returned
   *        *----*                        returned
   *                               *-*    returned
   * @return List of annotations that extend the given type and intersect with this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getIntersecting(Class<A> type) {
    return intersecting().withType(type).collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations that extend the given type, have the given tags, and intersect (overlap) this annotation
   *
   * this:       *-----------------*
   *          *------------*              returned
   *                *-------------*       not returned
   *           *---------------------*    not returned
   *        *----*                        returned
   *                               *-*    returned
   * @return List of annotations that extend the given type, have the given tags, and intersect with this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getIntersecting(Class<A> type, String... tags) {
    return intersecting().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  /**
   * Returns a list of annotations that extend the given type, have the given tags, and intersect (overlap) this annotation
   *
   * this:       *-----------------*
   *          *------------*              returned
   *                *-------------*       not returned
   *           *---------------------*    not returned
   *        *----*                        returned
   *                               *-*    returned
   * @return List of annotations that extend the given type, have the given tags, and intersect with this annotation
   */
  default <A extends SpanAnnotation<T>> List<A> getIntersecting(Class<A> type, Collection<String> tags) {
    return intersecting().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }
}

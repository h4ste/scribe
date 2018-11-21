package com.github.h4ste.scribe;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SpanAnnotationLike {

  /**
   * Returns a stream of annotations that are fully contained by this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * returned *---------------------*    not returned *----*                        not returned *-*
   * not returned
   *
   * @return Stream of annotations which are contained by this annotation
   */
  AnnotationStream<SpanAnnotation> contained();

  /**
   * Returns a list of annotations that are fully contained by this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * returned *---------------------*    not returned *----*                        not returned *-*
   * not returned
   *
   * @return List of annotations which are contained by this annotation
   */
  List<SpanAnnotation> getContained();

  /**
   * Returns a list of annotations castable to the given type that are fully contained by this
   * annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * returned *---------------------*    not returned *----*                        not returned *-*
   * not returned
   *
   * @return List of annotations of the given type (or a subtype) which are contained by this
   * annotation
   */
  <A extends SpanAnnotation> List<A> getContained(Class<A> type);

  /**
   * Returns a list of annotations castable to the given type with the given tags that are fully
   * contained by this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * returned *---------------------*    not returned *----*                        not returned *-*
   * not returned
   *
   * @return List of annotations of the given type (or a subtype) and with the given tags which are
   * contained by this annotation
   */
  <A extends SpanAnnotation> List<A> getContained(Class<A> type, String... tags);


  /**
   * Returns a list of annotations castable to the given type with the given tags that are fully
   * contained by this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * returned *---------------------*    not returned *----*                        not returned *-*
   * not returned
   *
   * @return List of annotations of the given type (or a subtype) and with the given tags which are
   * contained by this annotation
   */
  <A extends SpanAnnotation> List<A> getContained(Class<A> type, Collection<String> tags);


  <A extends SpanAnnotation> Optional<A> getParent(Class<A> type, String... tags);
  <A extends SpanAnnotation> Optional<A> getParent(Class<A> type, Collection<String> tags);


  /**
   * Returns a stream of annotations that fully contain this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * not returned *---------------------*    returned *----*                        not returned *-*
   * not returned
   *
   * @return Stream of annotations which fully contain this annotation
   */
  AnnotationStream<SpanAnnotation> containing();

  /**
   * Returns a list of annotations that fully contain this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * not returned *---------------------*    returned *----*                        not returned *-*
   * not returned
   *
   * @return List of annotations which fully contain this annotation
   */
  List<SpanAnnotation> getContaining();

  /**
   * Returns a list of annotations that extend the given type and fully contain this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * not returned *---------------------*    returned *----*                        not returned *-*
   * not returned
   *
   * @return List of annotations that extend the given type and fully contain this annotation
   */
  <A extends SpanAnnotation> List<A> getContaining(Class<A> type);

  /**
   * Returns a list of annotations that extend the given type, have the given tags, and fully
   * contain this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * not returned *---------------------*    returned *----*                        not returned *-*
   * not returned
   *
   * @return List of annotations that extend the given type, have the given tags, and fully contain
   * this annotation
   */
  <A extends SpanAnnotation> List<A> getContaining(Class<A> type, String... tags);

  /**
   * Returns a list of annotations that extend the given type, have the given tags, and fully
   * contain this annotation
   *
   * this:       *-----------------* *------------*              not returned *-------------*
   * not returned *---------------------*    returned *----*                        not returned *-*
   * not returned
   *
   * @return List of annotations that extend the given type, have the given tags, and fully contain
   * this annotation
   */
  <A extends SpanAnnotation> List<A> getContaining(Class<A> type, Collection<String> tags);

  /**
   * Returns a stream of annotations that intersect (overlap) this annotation
   *
   * this:       *-----------------* *------------*              returned *-------------*       not
   * returned *---------------------*    not returned *----*                        returned *-*
   * returned
   *
   * @return Stream of annotations that intersect with this annotation
   */
  AnnotationStream<SpanAnnotation> intersecting();

  /**
   * Returns a list of annotations that intersect (overlap) this annotation
   *
   * this:       *-----------------* *------------*              returned *-------------*       not
   * returned *---------------------*    not returned *----*                        returned *-*
   * returned
   *
   * @return List of annotations that intersect with this annotation
   */
  List<SpanAnnotation> getIntersecting();

  /**
   * Returns a list of annotations that extend the given type and intersect (overlap) this
   * annotation
   *
   * this:       *-----------------* *------------*              returned *-------------*       not
   * returned *---------------------*    not returned *----*                        returned *-*
   * returned
   *
   * @return List of annotations that extend the given type and intersect with this annotation
   */
  <A extends SpanAnnotation> List<A> getIntersecting(Class<A> type);

  /**
   * Returns a list of annotations that extend the given type, have the given tags, and intersect
   * (overlap) this annotation
   *
   * this:       *-----------------* *------------*              returned *-------------*       not
   * returned *---------------------*    not returned *----*                        returned *-*
   * returned
   *
   * @return List of annotations that extend the given type, have the given tags, and intersect with
   * this annotation
   */
  <A extends SpanAnnotation> List<A> getIntersecting(Class<A> type, String... tags);

  /**
   * Returns a list of annotations that extend the given type, have the given tags, and intersect
   * (overlap) this annotation
   *
   * this:       *-----------------* *------------*              returned *-------------*       not
   * returned *---------------------*    not returned *----*                        returned *-*
   * returned
   *
   * @return List of annotations that extend the given type, have the given tags, and intersect with
   * this annotation
   */
  <A extends SpanAnnotation> List<A> getIntersecting(Class<A> type, Collection<String> tags);
}

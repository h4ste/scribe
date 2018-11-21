package com.github.h4ste.scribe.n2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface AnnotationManager extends Describable {

  /**
   * Get annotation tags
   **/
  Stream<String> annotationTags();

  default Set<String> getAnnotationTags() {
    return annotationTags().collect(Collectors.toUnmodifiableSet());
  }


  default <A extends Annotation> A annotate(A annotation,
                                           int start,
                                           int end,
                                           String... tags) {
    return annotate(annotation, start, end, Arrays.asList(tags));
  }

  default <A extends Annotation> A annotateByLength(A annotation,
                                            int start,
                                            int length,
                                            String... tags) {
    return annotate(annotation, start, start + length, Arrays.asList(tags));
  }

  <A extends Annotation> A annotate(A annotation,
                                           int start,
                                           int end,
                                           Collection<String> tags);

  default <A extends Annotation> A annotateByLength(A annotation,
                                    int start,
                                    int length,
                                    Collection<String> tags) {
    return annotate(annotation, start, start + length, tags);
  }


  /**
   * Get annotations
   **/
  AnnotationStream<Annotation> annotations();

  default List<Annotation> getAnnotations() {
    return annotations().collect(Collectors.toUnmodifiableList());
  }

  default <A extends Annotation> List<A> getAnnotations(Class<A> type) {
    return annotations().withType(type).collect(Collectors.toUnmodifiableList());
  }

  default <A extends Annotation> List<A> getAnnotations(Class<A> type, String... tags) {
    return annotations().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }

  default <A extends Annotation> List<A> getAnnotations(Class<A> type, Collection<String> tags) {
    return annotations().withType(type).withTags(tags).collect(Collectors.toUnmodifiableList());
  }


  Optional<Annotation> getAnnotationByNid(final long nid);

  default <T extends Annotation> Optional<T> getAnnotationByNId(final long nid, final Class<T> type) {
    return getAnnotationByNid(nid).filter(type::isInstance).map(type::cast);
  }

  default boolean hasAnnotationTag(final String tag) {
    return getAnnotationTags().contains(tag);
  }

  boolean removeAnnotation(Annotation ann);

  default boolean removeAnnotations(Annotation... anns) {
    boolean changed = false;
    for (Annotation ann : anns)
      changed |= removeAnnotation(ann);
    return changed;
  }

  default boolean removeAnnotations(Collection<? extends Annotation> anns) {
    boolean changed = false;
    for (Annotation ann : anns)
      changed |= removeAnnotation(ann);
    return changed;
  }

  @Override
  default String describe() {
    return annotations()
        .map(Annotation::describe)
        .collect(Collectors.joining("\n • ", "Annotations:\n • ", "\n"));
  }
}

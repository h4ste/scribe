package com.github.h4ste.scribe.n;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

interface AnnotationBearer<T extends AnnotationBearer<T>> {
  void addAnnotation(Annotation<T> annotation);
  boolean removeAnnotation(Annotation<T> annotation);

  AnnotationStream<Annotation<T>> annotations(Collection<String> tags);
  default AnnotationStream<Annotation<T>> annotations(String... tags) {
    return annotations(Arrays.asList(tags));
  }

  default <A extends Annotation<T>> List<A> getAnnotations(Class<A> type, String... tags) {
    return annotations(tags).withType(type).collect(Collectors.toUnmodifiableList());
  }
  default <A extends Annotation<T>> List<A> getAnnotations(Class<A> type, Collection<String> tags) {
    return annotations(tags).withType(type).collect(Collectors.toUnmodifiableList());
  }
}

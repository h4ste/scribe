package com.github.h4ste.scribe;

import com.github.h4ste.scribe.util.Describable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseAnnotatable extends Describable  {

  void addAnnotation(Annotation annotation, Collection<String> tags);
  default void addAnnotation(Annotation annotation, String... tags) {
    addAnnotation(annotation, Arrays.asList(tags));
  }


  boolean removeAnnotation(Annotation annotation);

  AnnotationStream<Annotation> annotations(Collection<String> tags);
  default AnnotationStream<Annotation> annotations(String... tags) {
    return annotations(Arrays.asList(tags));
  }

  default <A extends Annotation> List<A> getAnnotations(Class<A> type, String... tags) {
    return annotations(tags).withType(type).collect(Collectors.toUnmodifiableList());
  }
  
  default <A extends Annotation> List<A> getAnnotations(Class<A> type, Collection<String> tags) {
    return annotations(tags).withType(type).collect(Collectors.toUnmodifiableList());
  }

  default boolean hasAnnotation(Class<? extends Annotation> type, String... tags) {
    return !getAnnotations(type, tags).isEmpty();
  }

  default boolean hasAnnotations(Class<? extends Annotation> type, Collection<String> tags) {
    return !getAnnotations(type, tags).isEmpty();
  }
}

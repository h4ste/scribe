package com.github.h4ste.scribe.n;

import java.util.Set;

public interface AnnotationManager<T> extends Annotatable, Decorator<T> {
  Annotation<T> newAnnotationImplementor();



//  abstract Set<String> getTags(Annotation<T> ann);
//
//  abstract boolean addTags(Annotation<T> ann, Set<T> tags);
//  abstract boolean removeTags(Annotation<T> ann, Set<T> tags);
//  abstract boolean setTags(Annotation<T> ann, Set<String> tags);
}

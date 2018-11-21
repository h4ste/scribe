package com.github.h4ste.scribe.legacy.text;

import com.google.common.collect.Lists;

import com.github.h4ste.scribe.legacy.text.annotation.Annotation;

import gate.AnnotationSet;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by travis on 7/15/14.
 */
public class AnnotationList<T extends Annotation<T>> extends AbstractList<T> {
  protected final List<T> inner;
  protected final AnnotationView parent;

  protected AnnotationList(AnnotationView parent, List<T> inner) {
    this.parent = parent;
    this.inner = inner;
  }

  public static <T extends Annotation<T>> AnnotationList<T> empty(AnnotationView parent) {
    return new AnnotationList<T>(parent, Lists.<T>newArrayListWithCapacity(parent.size()));
  }

  @SafeVarargs public static <T extends Annotation<T>> AnnotationList<T> create(AnnotationView parent, T... elems) {
    return new AnnotationList<T>(parent, Arrays.asList(elems));
  }

  public static <T extends Annotation<T>> AnnotationList<T> create(AnnotationView parent, Iterable<T> it) {
    return new AnnotationList<T>(parent, Lists.<T>newArrayList(it));
  }

  @Override public void add(int index, T element) {
    parent.add(element.asGate());
    inner.add(index, element);
  }

  @Override public T remove(int index) {
    parent.remove(get(index).asGate());
    return inner.remove(index);
  }

  /**
   * {@inheritDoc}
   *
   * @param index
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  @Override public T get(int index) {
    return inner.get(index);
  }

  @Override public int size() {
    return inner.size();
  }
}

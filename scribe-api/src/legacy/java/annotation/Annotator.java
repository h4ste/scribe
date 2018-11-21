package com.github.h4ste.scribe.annotation;

import com.github.h4ste.scribe.n2.Record;

import java.util.Collection;

/**
 * Created by travis on 7/15/14.
 */
@SuppressWarnings("unused")
public interface Annotator<T> extends AutoCloseable {

  void annotate(Record<? extends T> record);

  default void annotateAll(Collection<? extends Record<? extends T>> records) {
    for (Record<? extends T> record : records) {
      annotate(record);
    }
  }

  abstract class Builder<T, B extends Builder<T,B>> {
    protected abstract B self();
    public abstract Annotator<T> build();
  }

  @Override
  void close();
}
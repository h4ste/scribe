package com.github.h4ste.scribe.annotation;

import com.github.h4ste.scribe.n2.Record;

import java.util.List;

public interface EntityAnnotator<A extends Entity, T>  extends Annotator<T> {
  List<A> annotateEntities(Record<? extends T> record);
}
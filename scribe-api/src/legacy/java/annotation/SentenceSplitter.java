package com.github.h4ste.scribe.annotation;

import com.github.h4ste.scribe.n2.Record;

import java.util.List;

public interface SentenceSplitter<A extends Sentence, T>  extends Annotator<T> {
  List<A> annotateSentences(Record<? extends T> record);
}

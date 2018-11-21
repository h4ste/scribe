package com.github.h4ste.scribe.annotation;

import com.github.h4ste.scribe.n2.Record;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface TokenAnnotator<A extends Token, T>  extends Annotator<T> {

  <K extends T> List<A> annotateTokens(Record<K> record);


  List<A> annotateTokens(Sentence sentence);

  default <K extends T> List<A> annotateTokens(Record<K> record,
                                Function<? super Record<? super K>, ? extends Collection<? extends Sentence>> sentenceGetter) {
    return sentenceGetter.apply(record)
        .stream()
        .map(this::annotateTokens)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}

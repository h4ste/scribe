package com.github.h4ste.scribe.annotation;

import com.github.h4ste.scribe.n2.Annotation;
import com.github.h4ste.scribe.n2.AnnotationType;
import com.github.h4ste.scribe.n2.Attribute;

@AnnotationType("token")
public class Token extends Annotation {

  @Attribute("kind")
  private String partOfSpeech;

  @Attribute("lemma")
  private String lemma;

  public Token(String partOfSpeech, String lemma) {
    this.partOfSpeech = partOfSpeech;
    this.lemma = lemma;
  }

  public String getPartOfSpeech() {
    return partOfSpeech;
  }

  public String getLemma() {
    return lemma;
  }
}

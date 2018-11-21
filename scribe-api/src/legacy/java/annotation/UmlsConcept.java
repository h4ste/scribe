package com.github.h4ste.scribe.annotation;

import com.github.h4ste.scribe.n2.AnnotationType;
import com.github.h4ste.scribe.n2.Attribute;

import java.util.Set;

@AnnotationType("umls-concept")
public class UmlsConcept extends Entity implements Negatable {

  @Attribute("cui")
  private String cui;

  @Attribute("semantic-types")
  private Set<String> semanticTypes;

  @Attribute("negated")
  private boolean negated;

  public UmlsConcept(String cui) {
    this.cui = cui;
  }

  public String getCui() {
    return cui;
  }

  public Set<String> getSemanticTypes() {
    return semanticTypes;
  }

  @Override
  public boolean isNegated() {
    return negated;
  }
}

package com.github.h4ste.scribe;

import java.util.Set;

public class MetaMapEntity extends TextAnnotation implements HasSemanticTypes {
  public static class UmlsConcept {
    private final String cui;
    private final String preferredName;

    public UmlsConcept(String cui, String preferredName) {
      this.cui = cui;
      this.preferredName = preferredName;
    }

    public String getCui() {
      return cui;
    }

    public String getPreferredName() {
      return preferredName;
    }

    @Override
    public String toString() {
      return cui + "[\"" + preferredName + "\"]";
    }
  }

  private final Set<String> semanticTypes;
  private final Set<UmlsConcept> umlsConcepts;

  public MetaMapEntity(CharSequence source, int start, int end,
      Set<String> semanticTypes, Set<UmlsConcept> umlsConcepts) {
    super(source, start, end);
    this.semanticTypes = semanticTypes;
    this.umlsConcepts = umlsConcepts;
  }

  @Override
  public Set<String> getSemanticTypes() {
    return semanticTypes;
  }

  public Set<UmlsConcept> getUmlsConcepts() {
    return umlsConcepts;
  }
}

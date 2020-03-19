package com.github.h4ste.nlp;

import com.github.h4ste.scribe.AbstractAnnotation;
import com.github.h4ste.scribe.Annotation;
import com.github.h4ste.scribe.AnnotationStream;
import com.github.h4ste.scribe.HasSemanticType;
import com.github.h4ste.scribe.TextAnnotation;
import java.util.Set;

public class MetaMapEntity extends TextAnnotation {

  private final Set<UmlsConcept> umlsConcepts;

  public MetaMapEntity(CharSequence source, int start, int end, Set<UmlsConcept> umlsConcepts) {
    super(source, start, end);
    this.umlsConcepts = umlsConcepts;
  }

  public Set<UmlsConcept> getUmlsConcepts() {
    return umlsConcepts;
  }

  public AnnotationStream<UmlsConcept> concepts() {
    return AnnotationStream.of(umlsConcepts.stream());
  }

  public static class UmlsConcept extends AbstractAnnotation implements HasSemanticType {

    private final String cui;
    private final String preferredName;
    private final String semanticType;

    public UmlsConcept(String cui, String preferredName, String semanticType) {
      this.cui = cui;
      this.preferredName = preferredName;
      this.semanticType = semanticType;
    }

    public String getCui() {
      return cui;
    }

    public String getPreferredName() {
      return preferredName;
    }

    public String getSemanticType() {
      return semanticType;
    }

    @Override
    public String toString() {
      return cui + "[\"" + preferredName + "\"]";
    }
  }
}

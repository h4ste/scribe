package com.github.h4ste.scribe.legacy.text.relation;

import com.github.h4ste.scribe.legacy.text.annotation.Annotation;

import edu.utdallas.hltri.Describable;

public interface Relation<G extends Annotation, D extends Annotation> extends Describable {
  G getGovernor();
  D getDependant();
}

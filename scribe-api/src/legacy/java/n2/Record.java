package com.github.h4ste.scribe.n2;

import com.github.h4ste.scribe.legacy.text.Identifiable;

import java.util.stream.Collectors;

public interface Record<T> extends AnnotationManager, RelationManager<Annotation>, Describable, Identifiable, Attributeable, NumericIdentifiable, Typed<String> {

  T getSource();

  @Override
  default String describe() {
    return "Record[" + getType() + "]: #" + this.getId() + ':' + this.getNid() + '\n'
        + "Content:\n" + toString() + '\n'
        + mapAttributes((name, value) -> name + ": " + value)
        .collect(Collectors.joining("\n • ", "Attributes:\n • ", "\n"))
        + AnnotationManager.super.describe()
        + RelationManager.super.describe();
  }
}

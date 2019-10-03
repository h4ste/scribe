package com.github.h4ste.scribe;

import de.uka.ilkd.pp.DataLayouter;
import de.uka.ilkd.pp.NoExceptions;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public abstract class Annotation extends Annotatable implements BaseAnnotation, StringlyTyped,
    Comparable<Annotation> {

  protected abstract AnnotationHandle implementor();

  /* package-private */
  abstract void setImplementor(AnnotationHandle handle);

  @Override
  public String getAnnotationUid() {
    return implementor().getAnnotationUid();
  }

  @Override
  public final Set<String> getTags() {
    return implementor().getTags();
  }

  @Override
  public final Stream<String> tags() {
    return implementor().tags();
  }

  @Override
  public final boolean hasTag(String tag) {
    return implementor().hasTag(tag);
  }

  @Override
  public final boolean hasTags(String... tags) {
    return implementor().hasTags(tags);
  }

  @Override
  public boolean hasTags(Collection<String> tags) {
    return implementor().hasTags(tags);
  }

  protected void printAnnotationTo(DataLayouter<NoExceptions> layout) {
    layout.print("#").print(getAnnotationUid());
  }

  protected void printWithoutTypeTo(DataLayouter<NoExceptions> layout) {
    layout.beginCInd();
    printAnnotationTo(layout);
    layout.brk(1, 0);
    printAnnotationsTo(layout);
    layout.end();
  }

  @Override
  protected void printTo(DataLayouter<NoExceptions> layout) {
    layout.beginCInd();
    layout.print(getType());
    printAnnotationTo(layout);
    layout.brk(1, 0);
    printAnnotationsTo(layout);
    layout.end();
  }

  @Override
  public int compareTo(Annotation o) {
    return getAnnotationUid().compareTo(o.getAnnotationUid());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Annotation that = (Annotation) o;
    return Objects.equals(getAnnotationUid(), that.getAnnotationUid()) &&
        Objects.equals(getTags(), that.getTags()) &&
        Objects.equals(getType(), that.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTags(), getType(), getAnnotationUid());
  }
}

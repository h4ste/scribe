package com.github.h4ste.scribe;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.TreeMultimap;
import de.uka.ilkd.pp.DataLayouter;
import de.uka.ilkd.pp.NoExceptions;
import java.util.Collection;
import java.util.List;

public abstract class Annotatable extends Printable implements BaseAnnotatable {
  abstract AnnotationManager getManager();

  @Override
  public final void addAnnotation(Annotation annotation, Collection<String> tags) {
    getManager().addAnnotation(annotation, tags);
  }

  @Override
  public final void addAnnotation(Annotation annotation, String... tags) {
    getManager().addAnnotation(annotation, tags);
  }

  @Override
  public final boolean removeAnnotation(Annotation annotation) {
    return getManager().removeAnnotation(annotation);
  }

  @Override
  public final AnnotationStream<Annotation> annotations(Collection<String> tags) {
    return getManager().annotations(tags);
  }

  @Override
  public final AnnotationStream<Annotation> annotations(String... tags) {
    return getManager().annotations(tags);
  }

  @Override
  public final <A extends Annotation> List<A> getAnnotations(Class<A> type, String... tags) {
    return getManager().getAnnotations(type, tags);
  }

  @Override
  public final <A extends Annotation> List<A> getAnnotations(Class<A> type, Collection<String> tags) {
    return getManager().getAnnotations(type, tags);
  }

  protected void printAnnotationsTo(DataLayouter<NoExceptions> layout) {
    final Multimap<String, Annotation> annotations = annotations().collect(
        Multimaps.toMultimap(
            Annotation::getType,
            (Annotation a) -> a,
            TreeMultimap::create
        )
    );

    if (annotations.isEmpty()) {
      layout.print("{}");
    } else {
      layout.print("{").beginCInd();
      boolean firstType = true;
      for (String type : annotations.keySet()) {
        if (!firstType) {
          layout.print(";").brk(1, 0);
        } else {
          firstType = false;
        }
        final Collection<Annotation> annotsOfType = annotations.get(type);
        if (annotsOfType.size() == 1) {
          annotsOfType.iterator().next().printTo(layout);
        } else {
          layout.print(type).print(": {").beginCInd();
          boolean firstAnnot = true;
          for (Annotation ann : annotsOfType) {
            if (!firstAnnot) {
              layout.print(",").brk(1, 0);
            } else {
              firstAnnot = false;
            }
            ann.printWithoutTypeTo(layout);
          }
          layout.end().print("}");
        }
      }
      layout.end().print("}");
    }
  }

  @Override
  protected void printTo(DataLayouter<NoExceptions> layout) {
    layout.beginC();
    layout.print(this.getClass().getSimpleName()).brk(1, 0);
    printAnnotationsTo(layout);
    layout.end();
  }
}

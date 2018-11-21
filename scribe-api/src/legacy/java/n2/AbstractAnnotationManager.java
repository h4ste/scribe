package com.github.h4ste.scribe.n2;

import java.util.Collection;

public abstract class AbstractAnnotationManager implements AnnotationManager {


  protected abstract <A extends Annotation> A addAnnotation(A annotation);

  protected abstract AnnotationDelegate newAnnotationDelegate(int start, int end, Collection<String> tags);

  @Override
  public <A extends Annotation> A annotate(A annotation,
                                    int start,
                                    int end,
                                    Collection<String> tags) {
    final AnnotationDelegate delegate = newAnnotationDelegate(start, end, tags);
    annotation.setDelegate(delegate);
    delegate.setDelegator(annotation);
    return addAnnotation(annotation);
  }
}

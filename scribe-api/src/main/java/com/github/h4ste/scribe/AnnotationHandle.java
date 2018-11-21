package com.github.h4ste.scribe;

public interface AnnotationHandle extends Tagged<String> {
  AnnotationManager getManager();
  String getAnnotationUid();

  Annotation getHandler();
}

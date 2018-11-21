package com.github.h4ste.scribe.n2;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class AnnotationTypes {
  private static final Logger log = LogManager.getLogger();

  private static final LoadingCache<Class<? extends Annotation>, String> annotationTypes =
      CacheBuilder.newBuilder()
      .weakKeys()
      .build(
          new CacheLoader<Class<? extends Annotation>, String>() {
            @Override
            public String load(Class<? extends Annotation> key) throws Exception {
              final AnnotationType type = key.getAnnotation(AnnotationType.class);
              if (type == null) {
                final String name = key.getSimpleName().toLowerCase();
                log.warn("Annotation {} has no AnnotationType annotation. Inferring type as {}", key, name);
                return name;
              } else {
                return type.name();
              }
            }
          }
      );


  private AnnotationTypes() {
    throw new AssertionError();
  }

  public static String getAnnotationType(Class<? extends Annotation> annotationClass) {
    return annotationTypes.getUnchecked(annotationClass);
  }
}

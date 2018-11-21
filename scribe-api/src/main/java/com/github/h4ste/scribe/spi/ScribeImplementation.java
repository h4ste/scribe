package com.github.h4ste.scribe.spi;

import com.github.h4ste.scribe.AnnotationManagerProvider;

public interface ScribeImplementation extends AnnotationManagerProvider {
  String getRequestedApiVersion();
  void initialize();
}

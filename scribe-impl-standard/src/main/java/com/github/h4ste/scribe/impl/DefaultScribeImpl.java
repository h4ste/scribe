package com.github.h4ste.scribe.impl;

import com.github.h4ste.scribe.AnnotationManager;
import com.github.h4ste.scribe.RangeMapAnnotationManager;
import com.github.h4ste.scribe.spi.ScribeImplementation;

public class DefaultScribeImpl implements ScribeImplementation {

  private static final String REQUESTED_API_VERSION = "0.0.1";

  @Override
  public String getRequestedApiVersion() {
    return REQUESTED_API_VERSION;
  }

  @Override
  public AnnotationManager getOrCreateAnnotationManager() {
    return new RangeMapAnnotationManager();
  }

  @Override
  public void initialize() {
    /* Do nothing */
  }
}

package com.github.h4ste.scribe.corpus;

import com.google.common.collect.ForwardingObject;

public abstract class ForwardingAutoCloseable extends ForwardingObject implements AutoCloseable {

  /** Constructor for use by subclasses. */
  protected ForwardingAutoCloseable() {}

  @Override
  protected abstract AutoCloseable delegate();

  @Override
  public void close() throws Exception {
    delegate().close();
  }
}

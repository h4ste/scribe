package com.github.h4ste.scribe.legacy.annotators;

public class AnnotatorInitializationException extends RuntimeException {
  public AnnotatorInitializationException() {
    super();
  }

  public AnnotatorInitializationException(String message) {
    super(message);
  }

  public AnnotatorInitializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public AnnotatorInitializationException(Throwable cause) {
    super(cause);
  }
}

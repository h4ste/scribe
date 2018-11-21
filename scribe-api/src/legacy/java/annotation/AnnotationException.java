package com.github.h4ste.scribe.annotation;

public class AnnotationException extends RuntimeException  {
  public AnnotationException(String message) {
    super(message);
  }

  public AnnotationException() {
    super();
  }

  public AnnotationException(String message, Throwable cause) {
    super(message, cause);
  }

  public AnnotationException(Throwable cause) {
    super(cause);
  }
}

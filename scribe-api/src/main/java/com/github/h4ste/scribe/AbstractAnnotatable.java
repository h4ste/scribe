package com.github.h4ste.scribe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractAnnotatable extends Annotatable {
  private static final Logger log = LogManager.getLogger(AbstractAnnotatable.class);

  // Lazily-initialized
  private volatile AnnotationManager manager = null;


  /**
   * Gets the AnnotationManager that manages annotations for this class
   *
   * @return AnnotationManager
   */
  @Override
  AnnotationManager getManager() {
    AnnotationManager manager = this.manager;
    if (manager == null) { // First check
      synchronized (this) {
        if (this.manager == null) { // Second check (with locking)
          this.manager = manager = Scribe.createOrGetAnnotationManager();
//          log.debug("Using global annotation manager {}", this.manager);
        }
      }
    }
    return manager;
  }
}

package com.github.h4ste.scribe;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractAnnotation extends Annotation {
  private static final Logger log = LogManager.getLogger(AbstractAnnotation.class);

  AnnotationHandle implementor = null;

  private volatile AnnotationManager manager = null;

  @Override
  protected AnnotationHandle implementor() {
    return implementor;
  }

  @Override
  synchronized void setImplementor(AnnotationHandle handle) {
    // If have a manager, and this handle's implementor is different, we unset our cached
    // annotation manager
    if (this.manager != null && this.manager != handle.getManager()) {
      // Get any annotations currently on this annotation
      final List<Annotation> currentAnnotations = annotations().toList();

      if (!currentAnnotations.isEmpty()) {
        // We have stored annotations in our current manager, these annotations
        // need to be migrated to the new manager!
        final AnnotationManager newManager = handle.getManager().getOrCreateAnnotationManager();
        for (Annotation annot : currentAnnotations) {
          this.manager.removeAnnotation(annot);
          newManager.addAnnotation(annot, annot.getTags());
        }

        // Update this annotation's manager
        this.manager = newManager;
      } else {
        this.manager = null;
      }
    }

    this.implementor = handle;
  }

  @Override
  AnnotationManager getManager() {
    AnnotationManager manager = this.manager;
    if (manager == null) { // First check
      synchronized (this) {
        if (this.manager == null) { // Second check (with locking)
          if (this.implementor == null) {
            // If we don't have an implementor, use the global AnnotationManager
            this.manager = manager = Scribe.createOrGetAnnotationManager();
//            log.debug("Using global annotation manager {}", this.manager);
          } else {
            this.manager = manager = implementor.getManager().getOrCreateAnnotationManager();
//            log.debug("Using implementor's annotation manager {}", this.manager);
          }
        }
      }
    }
    return manager;
  }

  @Override
  public String getType() {
    return this.getClass().getSimpleName();
  }


}

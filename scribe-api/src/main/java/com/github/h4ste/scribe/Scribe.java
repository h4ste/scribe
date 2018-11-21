package com.github.h4ste.scribe;

import com.github.h4ste.scribe.spi.ScribeImplementation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public final class Scribe {

  private static final Logger log = LogManager.getLogger();

  private static final String[] API_COMPATIBILITY_LIST = new String[] { "0." };

  private enum INIT_STATE {
    UNINITIALIZED,
    FAILED,
    SUCCESSFUL
  }

  private static volatile INIT_STATE INITIALIZATION_STATE = INIT_STATE.UNINITIALIZED;

  private static volatile ScribeImplementation IMPLEMENTATION;

  private static List<ScribeImplementation> findScribeImplementations() {
    ServiceLoader<ScribeImplementation> implLoader = ServiceLoader.load(ScribeImplementation.class);
    List<ScribeImplementation> implementationList = new ArrayList<>();
    for (ScribeImplementation impl : implLoader) {
      implementationList.add(impl);
    }
    return implementationList;
  }

  private static void initialize() {
    bind();
    if (INITIALIZATION_STATE == INIT_STATE.SUCCESSFUL) {
      versionSanityCheck();
    }
  }

  private static void bind() {
    try {
      List<ScribeImplementation> implementationList = findScribeImplementations();
      reportMultipleImplementationAmbiguity(implementationList);
      if (implementationList != null && !implementationList.isEmpty()) {
        IMPLEMENTATION = implementationList.get(0);
        IMPLEMENTATION.initialize();
        log.debug("Using Scribe implementation {}: {}", IMPLEMENTATION.getClass().getSimpleName(), IMPLEMENTATION);
        reportActualImplementation(implementationList);
        INITIALIZATION_STATE = INIT_STATE.SUCCESSFUL;
      } else {
        log.error("No Scribe implementations were found.");
        throw new IllegalStateException("No Scribe implementation found");
      }
    } catch (Exception e) {
      failedBinding(e);
      throw new IllegalStateException("Unexpected initialization failure", e);
    }
  }

  private static void failedBinding(Throwable t) {
    INITIALIZATION_STATE = INIT_STATE.FAILED;
    log.error("Failed to instantiate Scribe implementation", t);
  }


  private static void versionSanityCheck() {
    String requested = IMPLEMENTATION.getRequestedApiVersion();

    boolean match = Arrays.stream(API_COMPATIBILITY_LIST).anyMatch(requested::startsWith);
    if (!match) {
      log.error("The Scribe version {} requested by your Scribe implementation is not compatible with {}",
          () -> requested, () -> Arrays.toString(API_COMPATIBILITY_LIST));
    }
  }

  private static boolean isAmbiguousImplementationList(List<ScribeImplementation> implementationList) {
    return implementationList.size() > 1;
  }

  private static void reportMultipleImplementationAmbiguity(List<ScribeImplementation> implementationList) {
    if (isAmbiguousImplementationList(implementationList)) {
      log.warn("Class path contains multiple Scribe implementations.");
          for (ScribeImplementation impl : implementationList) {
            log.warn("Found implementation [ {} ]", impl);
          }
    }
  }

  private static void reportActualImplementation(List<ScribeImplementation> implementationList) {
    if (!implementationList.isEmpty() && isAmbiguousImplementationList(implementationList)) {
      log.debug("Using Scribe implementation [ {} ]", implementationList.get(0));
    }
  }

  private static ScribeImplementation getScribeImplementation() {
    if (INITIALIZATION_STATE == INIT_STATE.UNINITIALIZED) {
      synchronized (ScribeImplementation.class) {
        if (INITIALIZATION_STATE == INIT_STATE.UNINITIALIZED) {
          initialize();
        }
      }
    }
    switch (INITIALIZATION_STATE) {
      case SUCCESSFUL:
        return IMPLEMENTATION;
      case FAILED:
        throw new IllegalStateException("Failed to initialize Scribe. Contributing exceptions were thrown earlier.");
    }
    throw new IllegalStateException("Unreachable code");
  }

  @SuppressWarnings("WeakerAccess")
  public static AnnotationManager createOrGetAnnotationManager() {
    return getScribeImplementation().getOrCreateAnnotationManager();
  }
}

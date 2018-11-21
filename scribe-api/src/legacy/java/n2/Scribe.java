package com.github.h4ste.scribe.n2;

import com.github.h4ste.scribe.legacy.text.Identifiable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

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



  @SuppressWarnings("unused")
  public static <T extends CharSequence & Identifiable & NumericIdentifiable & Typed<String>> Text<T> createText(T source) {
    return createText(source, source.getNid(), source.getId(), source.getType());
  }

  @SuppressWarnings("unused")
  public static <T extends Identifiable & CharSequence & NumericIdentifiable> Text<T> createText(T source, String type) {
    return createText(source, source.getNid(), source.getId(), type);
  }

  @SuppressWarnings("unused")
  public static <T extends CharSequence & Identifiable & Typed<String>> Text<T> createText(T source, long nid) {
    return createText(source, nid, source.getId(), source.getType());
  }

  @SuppressWarnings("unused")
  public static <T extends CharSequence & NumericIdentifiable & Typed<String>> Text<T> createText(T source, String id) {
    return createText(source, source.getNid(), id, source.getType());
  }

  @SuppressWarnings("unused")
  public static <T extends Identifiable & CharSequence> Text<T> createText(T source, long nid, String type) {
    return createText(source, nid, source.getId(), type);
  }

  @SuppressWarnings("unused")
  public static <T extends CharSequence & NumericIdentifiable> Text<T> createText(T source, String id, String type) {
    return createText(source, source.getNid(), id, type);
  }

  @SuppressWarnings("unused")
  public static <T extends CharSequence & Typed<String>> Text<T> createText(T source, long nid, String id) {
    return createText(source, nid, id, source.getType());
  }

  @SuppressWarnings("WeakerAccess")
  public static <T extends CharSequence> Text<T> createText(T source, long nid, String id, String type) {
    return getScribeImplementation().createText(source, nid, id, type);
  }

  @SuppressWarnings("unused")
  public static <T extends Identifiable & NumericIdentifiable & Typed<String>> Record<T> createRecord(T source) {
    return createRecord(source, source.getNid(), source.getId(), source.getType());
  }

  @SuppressWarnings("unused")
  public static <T extends Identifiable & NumericIdentifiable> Record<T> createRecord(T source, String type) {
    return createRecord(source, source.getNid(), source.getId(), type);
  }

  @SuppressWarnings("unused")
  public static <T extends Identifiable & Typed<String>> Record<T> createRecord(T source, long nid) {
    return createRecord(source, nid, source.getId(), source.getType());
  }

  @SuppressWarnings("unused")
  public static <T extends NumericIdentifiable & Typed<String>> Record<T> createRecord(T source, String id) {
    return createRecord(source, source.getNid(), id, source.getType());
  }

  @SuppressWarnings("unused")
  public static <T extends Identifiable> Record<T> createRecord(T source, long nid, String type) {
    return createRecord(source, nid, source.getId(), type);
  }

  @SuppressWarnings("unused")
  public static <T extends NumericIdentifiable> Record<T> createRecord(T source, String id, String type) {
    return createRecord(source, source.getNid(), id, type);
  }

  @SuppressWarnings("unused")
  public static <T extends Typed<String>> Record<T> createRecord(T source, long nid, String id) {
    return createRecord(source, nid, id, source.getType());
  }

  @SuppressWarnings("WeakerAccess")
  public static <T> Record<T> createRecord(T source, long nid, String id, String type) {
    return getScribeImplementation().createRecord(source, nid, id, type);
  }
}

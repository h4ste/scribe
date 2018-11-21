package com.github.h4ste.scribe.n2;

public abstract class AbstractScribeImpl implements ScribeImplementation {
  private final String requestedApiVersion;

  protected abstract RelationManager<Annotation> createRelationManager();
  protected abstract AnnotationManager createAnnotationManager();

  public AbstractScribeImpl(String requestedApiVersion) {
    this.requestedApiVersion = requestedApiVersion;
  }

  @Override
  public <T> Record<T> createRecord(T source, long nid, String id, String type) {
    return new Record<T>(createAnnotationManager(), createRelationManager(), source, nid, id, type);
  }

  @Override
  public <T extends CharSequence> Text<T> createText(T source, long nid, String id, String type) {
    return new Text<>(createAnnotationManager(), createRelationManager(), source, nid, id, type);
  }

  @Override
  public String getRequestedApiVersion() {
    return requestedApiVersion;
  }
}

package com.github.h4ste.scribe.n2;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class ForwardingRecord<T> implements Record<T>  {
  private transient final AnnotationManager annotations;
  private transient final RelationManager<Annotation> relations;

  protected final T source;
  private final long nid;
  private final String id;
  private final String type;

  public ForwardingRecord(AnnotationManager annotations, RelationManager<Annotation> relations,
             T source, long nid, String id, String type) {
    this.annotations = annotations;
    this.relations = relations;
    this.source = source;
    this.nid = nid;
    this.id = id;
    this.type = type;
  }

  /**
   * Get annotation tags
   **/
  @Override
  public Stream<String> annotationTags() {
    return annotations.annotationTags();
  }

  /**
   * Get annotations
   **/
  @Override
  public AnnotationStream<Annotation> annotations() {
    return annotations.annotations();
  }

  @Override
  public Optional<Annotation> getAnnotationByNid(long nid) {
    return annotations.getAnnotationByNid(nid);
  }

  @Override
  public <A extends Annotation> A annotate(A annotation, int start, int end, Collection<String> tags) {
    return annotations.annotate(annotation, start, end, tags);
  }

  @Override
  public boolean removeAnnotation(Annotation ann) {
    return annotations.removeAnnotation(ann);
  }

  @Override
  public Set<String> getAnnotationTags() {
    return annotations.getAnnotationTags();
  }

  @Override
  public long getNid() {
    return nid;
  }

  @Override
  public RelationStream<Relation<Annotation, Annotation>> relations() {
    return relations.relations();
  }

  @Override
  public <S extends Annotation> RelationStream<Relation<S, Annotation>> targetRelations(S source) {
    return relations.targetRelations(source);
  }

  @Override
  public <T extends Annotation> RelationStream<Relation<Annotation, T>> sourceRelations(T dependant) {
    return relations.sourceRelations(dependant);
  }

  @Override
  public void addRelation(Relation<? extends Annotation, ? extends Annotation> relation) {
    relations.addRelation(relation);
  }

  @Override
  public boolean removeRelation(Relation<? extends Annotation, ? extends Annotation> relation) {
    return relations.removeRelation(relation);
  }

  @Override
  public String getType() {
    return this.type;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public T getSource() {
    return source;
  }

  @Override
  public final String toString() {
    return this.source.toString();
  }
}

package com.github.h4ste.scribe.legacy.text;

import com.github.h4ste.scribe.legacy.text.annotation.Annotation;
import com.github.h4ste.scribe.legacy.text.relation.Relation;
import com.github.h4ste.scribe.legacy.text.relation.RelationType;

public interface AnnotationView {





  void addRelation()

  /**
   * Removes the passed relation from the document.
   * @param annSet the annotation set of the related annotations
   * @param relation the relation to be deleted
   */
  public <R extends Relation<R, ?, ?>> void removeRelation(
      final String annSet, final R relation) {
    setDirty();
    final RelationSet relSet = gateDocument.getAnnotations(annSet).getRelations();
    relSet.remove(relation.asGate());
  }

  /**
   * Removes all relations on annotations of the passed annotation set.
   * @param annSet the annotation set of the annotations whose relations are to be deleted.
   * @param type the RelationType of relations to be deleted.
   */
  public <R extends Relation<R, G, Dep>, G extends Annotation<G>, Dep extends Annotation<Dep>> void clear(
      final String annSet, final RelationType<R,G,Dep> type) {
    setDirty();
    final RelationSet relSet = gateDocument.getAnnotations(annSet).getRelations();
    relSet.removeAll(relSet.getRelations(type.getName()));
  }

}

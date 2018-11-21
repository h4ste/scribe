package com.github.h4ste.scribe.legacy.annotators;

import edu.utdallas.hltri.io.AC;

import com.github.h4ste.scribe.legacy.text.AbstractDocument;
import com.github.h4ste.scribe.legacy.text.Document;

/**
 * Created by travis on 7/15/14.
 */
@SuppressWarnings("unused")
public interface Annotator<D extends AbstractDocument> extends AC {
  <B extends D> void annotate(Document<B> document);

  default <B extends D> void annotateAll(Iterable<Document<B>> documents) {
    for (Document<B> document : documents) {
      annotate(document);
    }
  }

  abstract class Builder<D extends AbstractDocument, B extends Builder<D,B>> {
    protected abstract B self();
    public abstract Annotator<D> build();
  }
}
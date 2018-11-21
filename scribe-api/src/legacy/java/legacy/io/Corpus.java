package com.github.h4ste.scribe.legacy.io;

import com.github.h4ste.scribe.legacy.annotators.Annotator;
import com.github.h4ste.scribe.legacy.gate.GateUtils;
import com.github.h4ste.scribe.legacy.text.AbstractDocument;
import com.github.h4ste.scribe.legacy.text.Document;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ramon on 8/18/15.
 */
public abstract class Corpus<D extends AbstractDocument> {
//  private final Serializer<D> serializer;

  /**
   * Load the document with the passed id from the disk.
   * @param id the id of the document to be loaded.
   * @return the de-serialized document with id <code>id</code>.
   */
  protected abstract Document<D> loadDocument(final String id);

  /**
   * Serializes the passed document to the disk.
   * @param document the doucment to be saved.
   */
  public abstract void save(Document<D> document);

  /**
   * @return a stream of every document id in the corpus.
   */
  public abstract Stream<String> getIdStream();

  /**
   * Returns true if the passed document id can be loaded.
   * @param id the document id of the document to be found.
   * @return true if the passed document id can be loaded.
   */
  public abstract boolean canLoad(final String id);

  final public Document<D> load(final String id) {
    GateUtils.init();
    final Document<D> document = loadDocument(id);
    document.setCorpus(this);
    return document;
  }

  public void saveAll() {
    forEachDocument(Document::sync);
  }

  public void saveAll(Iterable<Document<D>> it) {
    for (Document<D> doc : it) {
      save(doc);
    }
  }

  /**
   * The passed consumer accepts each document in the corpus, without saving afterwards.
   * @param cons
   */
  public Corpus<D> forEachDocument(Consumer<Document<D>> cons) {
    getIdStream().forEach(id -> {
      try (Document<D> doc = load(id)) {
        cons.accept(doc);
      }
    });
    return this;
  }

  @Deprecated
  public <O> Stream<O> mapEachDocument(Predicate<String> filter, Function<Document<D>, O> fun) {
    return getIdStream().filter(filter).map(id -> {
      try (Document<D> doc = load(id)) {
        return fun.apply(doc);
      }
    });
  }

  @Deprecated
  public <O> Stream<O> mapEachDocument(Function<Document<D>, O> fun) {
    return getIdStream().map(id -> {
      try (Document<D> doc = load(id)) {
//        doc.setCorpus(this);
        return fun.apply(doc);
      }
    });
  }

  public List<Document<D>> getDocumentList() {
    return  getIdStream().map(this::load).collect(Collectors.toList());
  }

  @SafeVarargs
  public final Corpus<D> annotate(final Annotator<? super D>... annotators) {
    return annotate(Arrays.asList(annotators));
  }

  public Corpus<D> annotate(final Collection<? extends Annotator<? super D>> annotators) {
    return forEachDocument(doc -> {
      for (Annotator<? super D> annotator : annotators) {
        annotator.annotate(doc);
      }
      doc.sync();
    });
  }

  @Deprecated
  public Stream<Document<D>> stream() {
    return getIdStream().map(this::load);
  }
}

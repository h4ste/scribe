package com.github.h4ste.scribe.legacy.annotators;

import edu.utdallas.hltri.logging.Logger;
import com.github.h4ste.scribe.legacy.gate.GateUtils;
import com.github.h4ste.scribe.legacy.text.AbstractDocument;
import com.github.h4ste.scribe.legacy.text.Document;
import com.github.h4ste.scribe.legacy.text.annotation.Sentence;
import gate.LanguageAnalyser;

/**
 * Created by rmm120030 on 7/8/15.
 */
public class OpenNLPSentenceAnnotator<D extends AbstractDocument> implements Annotator<D> {
  private final static Logger log = Logger.get(GeniaAnnotator.class);
  public static final String ANNOTATION_SET_NAME = "opennlp";

  private final LanguageAnalyser onlp;
  private boolean clear = false;

  public OpenNLPSentenceAnnotator() {
    GateUtils.init();
    log.info("Opening OpenNLP Sentence Splitter...");
    onlp = GateUtils.loadResource(LanguageAnalyser.class, "gate.opennlp.OpenNlpSentenceSplit")
        .param("annotationSetName", ANNOTATION_SET_NAME)
        .build();
  }

  public OpenNLPSentenceAnnotator<D> clear() {
    clear = true;
    return this;
  }

  @Override
  public <B extends D> void annotate(Document<B> document) {
    if (clear || document.get("opennlp", Sentence.TYPE).isEmpty()) {
      log.trace("Annotating OpenNLP sentences on {}", document.get(AbstractDocument.id));
      document.clear("opennlp", Sentence.TYPE);
      onlp.setDocument(document.asGate());
      try {
        onlp.execute();
        log.trace("OpenNLP: split {}", document.get(AbstractDocument.id));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void close() {
    gate.Factory.deleteResource(onlp);
    log.debug("OpenNLP sentence splitter closed.");
  }
}

import com.github.h4ste.scribe.n2.Record;
import com.github.h4ste.scribe.annotation.AnnotationException;
import com.github.h4ste.scribe.annotation.Annotator;
import com.github.h4ste.scribe.annotation.Sentence;
import com.github.h4ste.scribe.legacy.annotators.AnnotatorInitializationException;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;

import bioc.BioCAnnotation;
import bioc.BioCDocument;
import bioc.BioCLocation;
import bioc.BioCPassage;
import bioc.BioCSentence;
import gov.nih.nlm.nls.metamap.document.FreeText;
import gov.nih.nlm.nls.ner.MetaMapLite;

public class MetaMapLiteAnnotator<T> implements Annotator<T> {
  private static final Logger log = LogManager.getLogger();

  private final Properties mmlProperties = new Properties();
  private final MetaMapLite mml;

  private static final String METAMAPLITE_TAG = "metamaplite";
  private static final String OPEN_NLP_TAG = "opennlp";

  private static final String INFON_ANNOT_TYPE_KEY = "type";
  private static final String INFON_ANNOT_TYPE_TOKEN = "token";

  public MetaMapLiteAnnotator(Properties mmlProperties) {
    try {
      this.mml = new MetaMapLite(mmlProperties);
    } catch (ClassNotFoundException | InstantiationException | NoSuchMethodException | IllegalAccessException | IOException e) {
      log.error("Failed to initialize MetaMapLite", e);
      throw new AnnotatorInitializationException(e);
    }
  }

  private static BioCDocument convertRecordToBioC(Record<?> record) {
    final BioCDocument bioCDocument = FreeText.instantiateBioCDocument(record.toString());
    bioCDocument.setID(record.getId());
    return bioCDocument;
  }


  @Override
  public void annotate(Record<? extends T> record) {
    try (final CloseableThreadContext.Instance ignored = CloseableThreadContext.put("RID", record.getId())) {
      final BioCDocument bioCDocument = convertRecordToBioC(record);
      try {
        this.mml.processDocument(bioCDocument);
      } catch (Exception e) {
        log.error("Failed to annotate record", e);
        throw new AnnotationException(e);
      }
      if (bioCDocument.getSize() != 1) {
        log.error("Invalid number of passages: {} != 1", bioCDocument.getSize());
        throw new AnnotationException("Invalid number of passages in document " + record.getId());
      }


      BioCPassage passage = bioCDocument.getPassage(0);
      for (BioCSentence sentence : passage.getSentences()) {
        int start = sentence.getOffset();
        int end = start + sentence.getText().length();
        record.annotate(new Sentence(), start, end, METAMAPLITE_TAG, OPEN_NLP_TAG);
        for (BioCAnnotation annot : sentence.getAnnotations())
            if (annot.getInfon(INFON_ANNOT_TYPE_KEY).equals(INFON_ANNOT_TYPE_TOKEN)) {
              final BioCLocation location = annot.getLocations().get(0);
              record.annotateByLength(new Token(), location.getOffset(), location.getLength());
            }
      }
    }
  }


  public static class Builder<T> extends Annotator.Builder<T, Builder<T>> {
    private Function<? super Record<? super T>, ? extends Collection<? extends Sentence>> sentenceSelector = null;
    private Set<String> sourceSet = null;
    private Set<String> semanticGroups = null;

    public Builder useSentences(Function<? super Record<? super T>, ? extends Collection<? extends Sentence>> sentenceSelector) {
      this.sentenceSelector = sentenceSelector;
      return this;
    }

    public Builder useSourceSet(Set<String> sourceSet) {
      this.sourceSet = sourceSet;
      return this;
    }

    public Builder useSemanticGroups(Set<String> semanticGroups) {
      this.semanticGroups = semanticGroups;
      return this;
    }




    private boolean annotateSentences = false;
    private boolean annotateTokens = false;
    private Function<Document<? extends D>, ? extends Iterable<Sentence>> sentenceProvider;
    private boolean clear = false;

    @Override
    protected GeniaAnnotator.Builder<D> self() {
      return this;
    }

    public GeniaAnnotator.Builder<D> clear() {
      this.clear = true;
      return self();
    }

    public GeniaAnnotator.Builder<D> withSentences(Function<Document<? extends D>, ? extends Iterable<Sentence>> sentenceProvider) {
      this.sentenceProvider = sentenceProvider;
      return self();
    }

    @SuppressWarnings("WeakerAccess")
    public GeniaAnnotator.Builder<D> annotateSentences() {
      this.annotateSentences = true;
      return self();
    }

    public GeniaAnnotator.Builder<D> annotateTokens() {
      this.annotateTokens = true;
      return self();
    }

    @Override
    public GeniaAnnotator<D> build() {
      return new GeniaAnnotator<>(self());
    }
  }
}

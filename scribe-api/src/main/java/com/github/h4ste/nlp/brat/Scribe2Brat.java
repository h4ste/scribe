package com.github.h4ste.nlp.brat;

import com.github.h4ste.scribe.Annotation;
import com.github.h4ste.scribe.Text;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Scribe2Brat<D extends Text> {

  private static class AnnotationProcessorMapping {
    private final Map<Class<? extends Annotation>, AnnotationProcessor<?>> map =
        new LinkedHashMap<>();

    @CanIgnoreReturnValue
    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    private <T extends Annotation> AnnotationProcessor<? super T> putMapping(Class<T> key,
        AnnotationProcessor<? super T> c) {
      return (AnnotationProcessor<? super T>) map.put(key, c);
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation> AnnotationProcessor<? super T> getMapping(Class<T> key) {
      return (AnnotationProcessor<? super T>) map.get(key);
    }

    private boolean containsMapping(Class<? extends Annotation> key) {
      return map.containsKey(key);
    }

    private boolean containsApplicableMapping(Class<? extends Annotation> key) {
      if (containsMapping(key)) {
        return true;
      } else {
        for (Map.Entry<Class<? extends Annotation>, AnnotationProcessor<?>> e : map.entrySet()) {
          if (e.getKey().isAssignableFrom(key)) {
            return true;
          }
        }
        return false;
      }
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation> AnnotationProcessor<? super T> findApplicableMapping(
        Class<T> key) {
      AnnotationProcessor<? super T> processor = getMapping(key);
      if (processor == null) {
        for (Map.Entry<Class<? extends Annotation>, AnnotationProcessor<?>> e : map.entrySet()) {
          if (e.getKey().isAssignableFrom(key)) {
            processor = (AnnotationProcessor<? super T>) e.getValue();
            break;
          }
        }
      }
      return processor;
    }

    private Set<Class<? extends Annotation>> mappedSet() {
      return map.keySet();
    }
  }

  public interface AnnotationProcessor<A extends Annotation> {
    void process(A a, BratDocument bratDocument);
  }

  private static final Logger log = LogManager.getLogger();

  private final Function<? super D, ? extends BratDocument> documentConverter;

  private final AnnotationProcessorMapping mappings = new AnnotationProcessorMapping();

  public Scribe2Brat(
      Function<? super D, ? extends BratDocument> documentConverter) {
    this.documentConverter = documentConverter;
  }


  public <A extends Annotation> Scribe2Brat<D> addMapping(Class<A> clazz,
      AnnotationProcessor<A> processor) {
    mappings.putMapping(clazz, processor);
    return this;
  }

  private void processAnnotation(Annotation annot, BratDocument bratDoc) {
    processAnnotation(annot.getClass(), annot, bratDoc);
  }

  private <A extends Annotation> void processAnnotation(Class<A> clazz, Annotation annot,
      BratDocument bratDoc) {
    if (!mappings.containsApplicableMapping(clazz)) {
      log.error("No mapping for annotation of type {}; available mappings are: {}", clazz,
          mappings.mappedSet());
      mappings.putMapping(clazz, null);
      return;
    }

    final AnnotationProcessor<? super A> mapping = mappings.findApplicableMapping(clazz);
    if (mapping != null) {
      mapping.process(clazz.cast(annot), bratDoc);
    }
  }

  public BratDocument convert(D text, String... tags) {
    final BratDocument bratDoc = documentConverter.apply(text);
    text.annotations(tags).forEach(ann -> processAnnotation(ann, bratDoc));
    return bratDoc;
  }
}

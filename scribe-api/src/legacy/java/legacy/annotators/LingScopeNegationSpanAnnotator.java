package com.github.h4ste.scribe.legacy.annotators;

import edu.utdallas.hltri.conf.Config;
import com.github.h4ste.scribe.legacy.text.AbstractDocument;
import com.github.h4ste.scribe.legacy.text.Document;
import com.github.h4ste.scribe.legacy.text.annotation.HedgeSpan;
import com.github.h4ste.scribe.legacy.text.annotation.NegationSpan;
import com.github.h4ste.scribe.legacy.text.annotation.Sentence;
import com.github.h4ste.scribe.legacy.text.annotation.Token;

import java.util.function.Function;

/**
* Annotates a {@link HedgeSpan} using LingScope.
* @author travis
*/
public class LingScopeNegationSpanAnnotator<T extends AbstractDocument> extends LingScopeAnnotator<T, NegationSpan> {
  public LingScopeNegationSpanAnnotator(Function<Document<? extends T>, ? extends Iterable<Sentence>> sentenceExtractor,
                                        Function<Sentence, ? extends Iterable<Token>> tokenExtractor) {
    super(Config.load("scribe.annotators.lingscope").getPath("negation-model-path"),
          sentenceExtractor,
          tokenExtractor,
          NegationSpan.TYPE);
  }
}

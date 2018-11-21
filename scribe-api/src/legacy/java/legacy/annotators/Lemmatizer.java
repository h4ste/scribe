package com.github.h4ste.scribe.legacy.annotators;

import edu.utdallas.hltri.util.Expander;
import edu.utdallas.hltri.util.Expansion;

/**
 *
 * @author travis
 */
public interface Lemmatizer extends Expander<CharSequence, String> {
   public String lemmatize(CharSequence phrase);

   @Override default Expansion<String> expand(CharSequence item) {
     String lemma = lemmatize(item);
     if (lemma.equals(item.toString())) {
       return Expansion.empty(getName());
     } else {
       return Expansion.singleton(getName(), lemma);
     }
   }
}

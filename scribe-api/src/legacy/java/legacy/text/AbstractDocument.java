package com.github.h4ste.scribe.legacy.text;

import com.google.common.base.CharMatcher;

import java.net.URL;


/**
 * Created by travis on 7/15/14.
 */
@SuppressWarnings("unused")
/**
 * Associates a given String with a set of optional Document features (e.g. filename, path)
 * as well as the potential for attaching and retrieving annotations.
 */
public abstract class AbstractDocument {
  public static final DocumentAttribute<AbstractDocument, String> fileName = DocumentAttribute.typed("filename", String.class);
  public static final DocumentAttribute<AbstractDocument, String> path = DocumentAttribute.typed("path", String.class);
  public static final DocumentAttribute<AbstractDocument, String> corpusName = DocumentAttribute.typed("corpus", String.class);
  public static final DocumentAttribute<AbstractDocument, String>  id =
      DocumentAttribute.specialized("id", String.class, (d, s) -> {
        if (CharMatcher.whitespace().matchesAnyOf(s)) {
          throw new IllegalArgumentException(
              "attempted to set id which contains whitespace: |" + s + '|');
        } else {
          d.asGate().setName(s);
          d.asGate().setLRPersistenceId(s);
        }
      });
  public static final DocumentAttribute<AbstractDocument, URL>     url        =
      DocumentAttribute.specialized("url", URL.class, (d, s) -> d.asGate().setSourceUrl(s));
  private static final long serialVersionUID = 1L;
}

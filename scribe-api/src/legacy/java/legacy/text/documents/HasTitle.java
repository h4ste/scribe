package com.github.h4ste.scribe.legacy.text.documents;

import com.github.h4ste.scribe.legacy.text.DocumentAttribute;

/**
 * Created by travis on 7/30/15.
 */
public interface HasTitle {
  DocumentAttribute<HasTitle, String> title = DocumentAttribute.typed("title", String.class);
}

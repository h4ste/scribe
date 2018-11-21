package com.github.h4ste.scribe.legacy.text.documents;

import com.github.h4ste.scribe.legacy.text.DocumentAttribute;

/**
 * Created by travis on 7/30/15.
 */
public interface HasDoi {
  DocumentAttribute<HasDoi, String> doi = DocumentAttribute.typed("doi", String.class);
}

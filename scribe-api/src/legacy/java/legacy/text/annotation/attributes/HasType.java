package com.github.h4ste.scribe.legacy.text.annotation.attributes;

import com.github.h4ste.scribe.legacy.text.Attribute;

/**
 * Created by travis on 8/15/14.
 */
public interface HasType {
  Attribute<HasType, String> type = Attribute.typed("type", String.class);
}

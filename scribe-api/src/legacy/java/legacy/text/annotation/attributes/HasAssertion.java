package com.github.h4ste.scribe.legacy.text.annotation.attributes;

import com.github.h4ste.scribe.legacy.text.Attribute;

/**
 * Created by travis on 2/17/15.
 */
public interface HasAssertion {
  public static Attribute<HasAssertion, String>
      ASSERTION = Attribute.typed("assertion", String.class);
}

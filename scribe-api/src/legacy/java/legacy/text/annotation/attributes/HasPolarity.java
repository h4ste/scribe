package com.github.h4ste.scribe.legacy.text.annotation.attributes;

import com.github.h4ste.scribe.legacy.text.Attribute;

/**
 * Created by rmm120030 on 9/14/16.
 */
public interface HasPolarity {
  Attribute<HasPolarity, String> polarity = Attribute.typed("polarity", String.class);
}

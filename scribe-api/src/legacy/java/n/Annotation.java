package com.github.h4ste.scribe.n;

import com.github.h4ste.scribe.n2.Taggable;

public interface Annotation<T> extends Decorator<T>, Annotatable<T>, Taggable<String> {
}

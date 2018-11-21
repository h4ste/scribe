package com.github.h4ste.scribe.n;

public interface Text<T extends CharSequence, S extends Text<T, S>> extends CharSequence, Annotatable<S> {

}

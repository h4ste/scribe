//package com.github.h4ste.scribe.corpus;
//
//import com.github.h4ste.scribe.Annotatable;
//import java.io.Closeable;
//import java.util.AbstractCollection;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Function;
//import java.util.stream.Stream;
//
//public interface Corpus<D extends Annotatable> extends Iterable<D>, AutoCloseable {
//  long size();
//
//  boolean contains(final D document);
//
//  default void close() {
//    /* Do nothing */
//  }
//}

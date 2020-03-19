//package com.github.h4ste.scribe.corpus;
//
//import com.github.h4ste.scribe.Annotatable;
//import java.nio.file.Path;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.Objects;
//import java.util.Spliterator;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.stream.Stream;
//
//public class FSCorpus<D extends Annotatable> extends Corpus<D> {
//  final Collection<Path> files;
//  final Function<? super Path, ? extends D> reader;
//
//  public FSCorpus(Collection<Path> files,
//      Function<? super Path, ? extends D> reader) {
//    this.files = files;
//    this.reader = reader;
//  }
//
//
//  public Stream<D> stream() {
//    return files.stream().map(reader);
//  }
//
//
//  @Override
//  public Iterator<D> iterator() {
//    return stream().iterator();
//  }
//
//  @Override
//  public void forEach(Consumer<? super D> action) {
//    stream().forEach(action);
//  }
//
//  @Override
//  public Spliterator<D> spliterator() {
//    return stream().spliterator();
//  }
//
//  @Override
//  public long size() {
//    return files.size();
//  }
//
//  @Override
//  public boolean contains(D document) {
//    return stream().anyMatch(d -> Objects.equals(d, document));
//  }
//}

//package com.github.h4ste.scribe.corpus;
//
//import com.github.h4ste.scribe.Annotatable;
//import com.google.common.collect.ImmutableList;
//import java.nio.file.Path;
//import java.util.Collection;
//import java.util.function.Function;
//
//public class FSCorpus<K, D extends Annotatable> extends ManagedCorpus<Path, D> {
//  private final Collection<Path> textDirectories;
//  private final Collection<Path> annotationDirectories;
//  private final Function<K, Path> toPathFunction;
//
//  protected FSCorpus(Collection<Path> textDirectories,
//      Collection<Path> annotationDirectories,
//      Function<K, Path> toPathFunction) {
//    this.textDirectories = textDirectories;
//    this.annotationDirectories = annotationDirectories;
//    this.toPathFunction = toPathFunction
//  }
//
//  public class FSCorpusBuilder {
//    private final ImmutableList.Builder<Path> textDirectories = ImmutableList.builder();
//    private final ImmutableList.Builder<Path> annotationDirectories = ImmutableList.builder();
//
//    private FSCorpusBuilder(Path textDirectory) {
//      this.textDirectories.add(textDirectory);
//    }
//
//    public FSCorpusBuilder addTextDirectory(Path directory) {
//      this.textDirectories.add(directory);
//      return this;
//    }
//
//    public FSCorpusBuilder addAnnotationDirectory(Path directory) {
//      this.annotationDirectories.add(directory);
//      return this;
//    }
//
//
//    public FSCorpus<D> build() {
//      return new FSCorpus<>(textDirectories.build(), annotationDirectories.build());
//    }
//  }
//
//  public D load(K key) {
//    return this.load(toPathFunction.apply(key));
//  }
//
//
//}

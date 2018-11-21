package com.github.h4ste.scribe.n;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Annotatable<T extends Annotatable<T>> extends AnnotationBearer<T> {
  AnnotationManager<T> getAnnotationManager();
}

package com.github.h4ste.scribe.corpus;

import java.nio.file.Path;

public interface ToPathFunction<T> {
  Path applyAsPath(T value);
}

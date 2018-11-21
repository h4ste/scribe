package com.github.h4ste.scribe.legacy.text;

import com.google.common.reflect.TypeToken;

import java.util.function.Function;

import edu.utdallas.hltri.util.Unsafe;

/**
 * Created by travis on 8/14/14.
 */
public class Attribute<S extends AttributeMap<S>, T> implements Function<S, T> {
  public final String   name;
  public final Class<T> type;

  protected Attribute(String name, Class<T> type) {
    this.name = name;
    this.type = type;
  }

  public static <S extends AttributeMap<S>, T> Attribute<S, T> typed(String name, Class<T> type) {
    return new Attribute<>(name, type);
  }

  public static <S extends AttributeMap<S>, T> Attribute<S, T> inferred(String name) {
    TypeToken<T> token = new TypeToken<T>(Attribute.class) {
    };
    return new Attribute<>(name, Unsafe.cast(token.getRawType()));
  }

  @Override
  public String toString() {
    return name + ": " + type.getName();
  }

  @Override
  public T apply(S o) {
    return o.get(this);
  }
}


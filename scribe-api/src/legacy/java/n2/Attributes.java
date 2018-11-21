package com.github.h4ste.scribe.n2;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Attributes {
  private static final Logger log = LogManager.getLogger();

  private static final MethodHandles.Lookup mhl = MethodHandles.lookup();

  public static class AttributeHandle {
    private final MethodHandle getter;
    private final MethodHandle setter;
    private final Type type;

    protected AttributeHandle(MethodHandle getter, MethodHandle setter, Type type) {
      this.getter = getter;
      this.setter = setter;
      this.type = type;
    }

    public static AttributeHandle fromField(Field field) throws IllegalAccessException {
      return new AttributeHandle(mhl.unreflectGetter(field), mhl.unreflectSetter(field), field.getType());
    }

    public Type getType() {
      return type;
    }

    public Object getValue(Attributeable source) {
      try {
        return getter.invokeExact(source);
      } catch (Throwable t) {
        throw new RuntimeException(t);
      }
    }

    public Object setValue(Attributeable source, Object value) {
      try {
        return setter.invokeWithArguments(source, value);
      } catch (Throwable t) {
        throw new RuntimeException(t);
      }
    }


  }

  private static final LoadingCache<Class<? extends Attributeable>, Map<String, AttributeHandle>>
    classAttributes = CacheBuilder.newBuilder()
      .weakKeys()
      .weakValues()
      .build(new CacheLoader<Class<? extends Attributeable>, AttributeManager<?>>() {
        @Override
        public Map<String, AttributeHandle> load(Class<? extends Attributeable> key) throws Exception {

          return Stream.<Class<?>>iterate(key, clazz -> clazz.getSuperclass() != null, Class::getSuperclass)
              .map(Class::getDeclaredFields)
              .flatMap(Arrays::stream)
              .filter(field -> field.isAnnotationPresent(Attribute.class))
              .collect(Collectors.toMap(
                  field -> field.getAnnotation(Attribute.class).value(),
                  (Field field) -> {
                    field.setAccessible(true);
                    try {
                      return AttributeHandle.fromField(field);
                    } catch (IllegalAccessException e) {
                      throw new RuntimeException(e);
                    }
                  }
              ));
        }
      });

  private Attributes() {
    throw new AssertionError();
  }

  static Map<String, AttributeHandle> getAttributeMap(Class<? extends Attributeable> attr) {
    return classAttributes.getUnchecked(attr);
  }

  static class InvalidAttributeException extends RuntimeException {
    public InvalidAttributeException(Class<? extends Attributeable> clazz, String name) {
      super("Invalid attribute '" + name + "' for class " + clazz.getCanonicalName());
    }
  }

  public static AttributeHandle getAttributeHandle(Class<? extends Attributeable> clazz, String name) {
    Segment

    try (final CloseableThreadContext.Instance ignored = CloseableThreadContext.put("class", clazz.getCanonicalName())) {
      try {
        final AttributeHandle handle = classAttributes.get(clazz).get(name);
        if (handle == null) {
          throw new InvalidAttributeException(clazz, name);
        } else {
          return handle;
        }
      } catch (ExecutionException e) {
        log.error("Failed to get attributes", e);
        throw new RuntimeException(e);
      }
    }

  }

  public static boolean hasAttribute(Class<? extends Attributeable> clazz)

  static void forEachAttribute(Attributeable self, BiConsumer<? super String, Object> consumer) {
    classAttributes.getUnchecked(self.getClass())
        .forEach((attr, ah) -> {
          try {
            Object value = ah.getValue(self);
            consumer.accept(attr, value);
          } catch (Throwable t) {
            throw new RuntimeException(t);
          }
        });
  }

  static <R> Stream<R> mapAttributes(Attributeable self, BiFunction<? super String, Object, ? extends R> mapper) {
    return classAttributes.getUnchecked(self.getClass())
        .entrySet()
        .stream()
        .map(entry -> {
          final String attr = entry.getKey();
          try {
            Object value = entry.getValue().getValue(self);
            return mapper.apply(attr, value);
          } catch (Throwable t) {
            throw new RuntimeException(t);
          }
        });
  }


}

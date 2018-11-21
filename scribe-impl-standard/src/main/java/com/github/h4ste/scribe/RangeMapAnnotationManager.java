package com.github.h4ste.scribe;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RangeMapAnnotationManager implements AnnotationManager {
  private static final Logger log = LogManager.getLogger(RangeMapAnnotationManager.class);


  private final RangeMap<SpanAnnotation> annotationsByInterval =
      new RangeMap<>(
          Span.<SpanAnnotation>topologicalOrder().thenComparing(Annotation::getAnnotationUid));

  private final Long2ObjectMap<Annotation> annotationsByNuid = new Long2ObjectOpenHashMap<>();
  private final Multimap<String, Annotation> annotationsByTag = HashMultimap.create();
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock write = lock.writeLock();

  private final AtomicLong nextAnnotationUid = new AtomicLong(0);

  @Override
  public void addAnnotation(Annotation annotation, Collection<String> tags) {
    final long nuid = nextAnnotationUid.incrementAndGet();
    // Handle managed annotations
    if (annotation instanceof SpanAnnotation) {
        final SpanAnnotation spanAnnotation = (SpanAnnotation) annotation;
        spanAnnotation.setImplementor(new SpanAnnotationImpl(spanAnnotation, tags, nuid));
      } else {
        annotation.setImplementor(new AnnotationImpl(annotation, tags, nuid));
    }

    write.lock();
    try {
      annotationsByNuid.put(nuid, annotation);
      for (String tag : tags) {
        annotationsByTag.put(tag, annotation);
      }

      if (annotation instanceof SpanAnnotation) {
        final SpanAnnotation spanAnnotation = (SpanAnnotation) annotation;
        annotationsByInterval.add(spanAnnotation.getStart(), spanAnnotation.getEnd(), spanAnnotation);
      }
    } finally {
      write.unlock();
    }
  }

  @Override
  public AnnotationStream<Annotation> annotations(Collection<String> tags) {
    if (tags.isEmpty()) {
      return AnnotationStream.of(annotationsByNuid.values().stream());
    } else {
      return AnnotationStream
          .of(tags.stream().map(annotationsByTag::get).flatMap(Collection::stream));
    }
  }

  @Override
  public AnnotationManager getOrCreateAnnotationManager() {
    return new RangeMapAnnotationManager();
  }

  @Override
  public boolean removeAnnotation(Annotation annotation) {
    if (annotation.implementor().getManager() != this) {
      log.warn("Attempted to remove annotation {} attached to a different manager!", annotation, annotation.implementor().getManager());
      return false;
    }
    if (!(annotation.implementor() instanceof AnnotationImpl)) {
      log.error("Attempted to remove annotation {} with incompatible handle {}", annotation, annotation.implementor());
      return false;
    }

    final long nuid = ((AnnotationImpl) annotation.implementor()).nuid;

    write.lock();
    final boolean changed;
    try {
      final Annotation old = annotationsByNuid.remove(nuid);
      boolean nuid_changed = old != null;

      boolean tag_changed = false;
      for (String tag : annotation.getTags()) {
        tag_changed |= annotationsByTag.remove(tag, annotation);
      }

      assert nuid_changed == tag_changed;
      if (annotation instanceof SpanAnnotation) {
        boolean range_changed = annotationsByInterval.remove(annotation);
        assert range_changed == nuid_changed;
      }

      changed = nuid_changed;
    } finally {
      write.unlock();
    }

    // We no longer need to implement this annotation
    annotation.setImplementor(null);

    return changed;
  }

  private class AnnotationImpl implements AnnotationHandle {
    private final Set<String> tags;
    private final long nuid;
    private final String uid;
    protected final Annotation handler;

    private AnnotationImpl(Annotation handler, Collection<String> tags, long nuid) {
      this.handler = handler;
      this.tags = ImmutableSet.copyOf(tags);
      this.nuid = nuid;
      this.uid = Long.toString(nuid);
    }

    @Override
    public AnnotationManager getManager() {
      return RangeMapAnnotationManager.this;
    }

    @Override
    public Set<String> getTags() {
      return tags;
    }

    @Override
    public String getAnnotationUid() {
      return uid;
    }

    @Override
    public Annotation getHandler() {
      return handler;
    }
  }

  private class SpanAnnotationImpl extends AnnotationImpl implements SpanAnnotationHandle {

    private SpanAnnotationImpl(SpanAnnotation handler, Collection<String> tags, long nuid) {
      super(handler, tags, nuid);
    }

    @Override
    public AnnotationStream<SpanAnnotation> contained() {
      final SpanAnnotation handler = getHandler();
      return AnnotationStream.of(annotationsByInterval.findContained(handler.getStart(), handler.getEnd()));
    }

    @Override
    public AnnotationStream<SpanAnnotation> containing() {
      final SpanAnnotation handler = getHandler();
      return AnnotationStream.of(annotationsByInterval.findContaining(handler.getStart(), handler.getEnd()));
    }

    @Override
    public AnnotationStream<SpanAnnotation> intersecting() {
      final SpanAnnotation handler = getHandler();
      return AnnotationStream.of(annotationsByInterval.findIntersection(handler.getStart(), handler.getEnd()));
    }

    @Override
    public SpanAnnotation getHandler() {
      return (SpanAnnotation) this.handler;
    }
  }
}

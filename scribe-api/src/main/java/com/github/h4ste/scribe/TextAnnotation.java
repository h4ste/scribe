package com.github.h4ste.scribe;

import de.uka.ilkd.pp.DataLayouter;
import de.uka.ilkd.pp.NoExceptions;
import java.util.Comparator;

public abstract class TextAnnotation extends AbstractSpanAnnotation implements CharSequence {

  public static <T extends TextAnnotation> Comparator<T> documentOrder() {
    return Span.<T>topologicalOrder().thenComparing(Annotation::getAnnotationUid);
  }


  private final CharSequence source;

  protected TextAnnotation(CharSequence source, int start, int end) {
    super(start, end);
    this.source = source;
  }

  protected CharSequence getSource() {
    return source;
  }

  @Override
  public final int length() {
    return end - start;
  }

  @Override
  public final char charAt(int index) {
    return getSource().charAt(start + index);
  }

  @Override
  public final CharSequence subSequence(int start, int end) {
    return getSource().subSequence(this.start + start, this.start + end);
  }

  @Override
  public final String toString() {
    return getSource().subSequence(start, end).toString();
  }

  @Override
  protected void printTo(DataLayouter<NoExceptions> layout) {
    layout.beginCInd();
    printAnnotationTo(layout);
    layout.print(":").brk(1, 0).print("\"").print(toString()).print("\"");
    layout.brk(1, 0).print(":").brk(1, 0);
    printAnnotationsTo(layout);
    layout.end();
  }
}

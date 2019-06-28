package com.github.h4ste.scribe;

import de.uka.ilkd.pp.DataLayouter;
import de.uka.ilkd.pp.NoExceptions;

public class Text extends AbstractAnnotatable implements CharSequence  {
  private final CharSequence text;

  public Text(CharSequence text) {
    this.text = text;
  }

  @Override
  public int length() {
    return text.length();
  }

  @Override
  public char charAt(int index) {
    return text.charAt(index);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return text.subSequence(start, end);
  }

  @Override
  public final String toString() {
    return text.toString();
  }

  @Override
  protected void printTo(DataLayouter<NoExceptions> layout) {
    layout.beginI(0);
    layout.print(this.getClass().getSimpleName()).brk(1, 0);
    layout.print("Text: ").beginI().print(toString()).end();
    layout.print("Annotations: ");
    printAnnotationsTo(layout);
    layout.end();
  }
}

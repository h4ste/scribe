package com.github.h4ste.scribe;

import com.github.h4ste.scribe.util.Describable;
import de.uka.ilkd.pp.DataLayouter;
import de.uka.ilkd.pp.NoExceptions;
import de.uka.ilkd.pp.StringBackend;

public abstract class Printable implements Describable {

  abstract protected void printTo(DataLayouter<NoExceptions> printer);

  @Override
  public final String describe() {
    final StringBackend sb = new StringBackend(100);
    final DataLayouter<NoExceptions> dl = new DataLayouter<NoExceptions>(sb, 2);
    printTo(dl);
    return sb.getString();
  }
}

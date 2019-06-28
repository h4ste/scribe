package com.github.h4ste.nlp.brat;

import static com.github.h4ste.collect.IteratorUtils.nextOrNull;
import static com.github.h4ste.collect.IteratorUtils.nextOrIOException;
import static com.github.h4ste.util.ParseUtils.parsePositiveInt;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Pattern;

public class BratTextAnnotation extends BratAnnotation {

  private static final Splitter ATTR_SPLITTER = Splitter.on(' ').limit(3);

  private int start;
  private int end;

  private final String text;

  public BratTextAnnotation(int index, String name, int start, int end, String text) {
    super('T', index, name);
    this.start = start;
    this.end = end;
    this.text = text;
  }


  public boolean hasText() {
    return text != null;
  }

  public static String format(String text) {
    return CharMatcher.is('\n').replaceFrom(text, "\\n");
  }

  private static final Pattern escapedNewline = Pattern.compile(Pattern.quote("\\n"));

  public static String unformat(String text) {
    return escapedNewline.matcher(text).replaceAll("\n");
  }

  public static BratTextAnnotation fromString(String line) {
    final Iterator<String> fields = FIELD_SPLITTER.limit(3).split(line).iterator();

    try {

      final String key = nextOrIOException(fields, "missing annotation id");
      final char bratType = key.charAt(0);
      assert bratType == 'T';
      final int index = parsePositiveInt(key.substring(1), "invalid annotation index");

      final String attr = nextOrIOException(fields, "missing text annotation fields");
      final Iterator<String> attrFields = ATTR_SPLITTER.split(attr).iterator();

      final String annotType = nextOrIOException(attrFields, "missing annotation type/name");
      final int start = parsePositiveInt(nextOrIOException(attrFields, "missing start offset"),
          "invalid start offset");
      final int end = parsePositiveInt(nextOrIOException(attrFields, "missing end offset"),
          "invalid end offset");

      String text = nextOrNull(fields);
      if (text != null) {
        text = unformat(text);
      }

      return new BratTextAnnotation(index, annotType, start, end, text);

    } catch (IOException ioe) {
      throw new UncheckedIOException("Failed to parse line \"" + line + '"', ioe);
    }
  }

  public void setStart(int start) {
    this.start = start;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  public void setPosition(int start, int end) {
    setStart(start);
    setEnd(end);
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    if (text != null) {
      return FIELD_JOINER.join(
          this.getId(),
          this.getType() + ' ' + Integer.toString(start) + ' ' + Integer.toString(end),
          format(text));
    } else {
      return FIELD_JOINER.join(this.getId(),
          this.getType() + ' ' + Integer.toString(start) + ' ' + Integer.toString(end));
    }
  }

  @Override
  BratTextAnnotation shiftIndex(int shift) {
    return (BratTextAnnotation) super.shiftIndex(shift);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    BratTextAnnotation that = (BratTextAnnotation) o;
    return start == that.start &&
        end == that.end &&
        Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), start, end, text);
  }
}

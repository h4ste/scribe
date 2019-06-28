package com.github.h4ste.nlp.brat;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.Objects;


public abstract class BratAnnotation {
  private final String name;
  private String id;

  static final char FIELD_DELIM = '\t';
  static final Splitter FIELD_SPLITTER = Splitter.on(FIELD_DELIM);
  static final Joiner FIELD_JOINER = Joiner.on(FIELD_DELIM);

  @SuppressWarnings("WeakerAccess")
  protected BratAnnotation(char type, int index, String name) {
    this.name = name;
    this.id = type + Integer.toString(index);
  }

  public String getType() {
    return this.name;
  }

  public String getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return FIELD_JOINER.join(getId(), name);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BratAnnotation that = (BratAnnotation) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  /**
   * Shifts the index of this annotations ID by the given amount,
   * preserving the annotation prefix (e.g., A, T, N).
   *
   * Formally, the new id will have the same prefix, with the index increased by {@code shift}
   *
   * @param shift amount to adjust (positive or negative integer)
   */
  BratAnnotation shiftIndex(int shift) {
    final char annotType = this.id.charAt(0);

    // Get current/original index
    int index = Integer.parseInt(this.id.substring(1));

    // Update id by shifting index;
    this.id = annotType + Integer.toString(index + shift);

    return this;
  }


}

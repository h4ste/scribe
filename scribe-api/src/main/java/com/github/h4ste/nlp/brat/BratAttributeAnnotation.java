package com.github.h4ste.nlp.brat;

import static com.github.h4ste.collect.IteratorUtils.nextOrIOException;
import static com.github.h4ste.collect.IteratorUtils.nextOrNull;
import static com.github.h4ste.util.ParseUtils.parsePositiveInt;

import com.google.common.base.Splitter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Objects;

public class BratAttributeAnnotation extends BratAnnotation implements BratModifierAnnotation {

  private static final Splitter ATTR_SPLITTER = Splitter.on(' ').limit(3);
  private static final char ANNOT_TYPE = 'A';

  private String parentId;
  private final String value;

  public BratAttributeAnnotation(int index, String name, String parentId) {
    this(index, name, parentId, null);
  }

  public BratAttributeAnnotation(int index, String name, String parentId, String value) {
    super(ANNOT_TYPE, index, name);
    this.parentId = parentId;
    this.value = value;
  }

  public static BratAttributeAnnotation fromString(String line) {
    final Iterator<String> fields = FIELD_SPLITTER.limit(2).split(line).iterator();

    try {
      final String key = nextOrIOException(fields, "missing annotation id");
      final char bratType = key.charAt(0);
      assert bratType == ANNOT_TYPE : "invalid normalization annotation id \"" + key
          + "\" (should maybeStart with '" + bratType + "' not '" + bratType + "'";
      final int index = parsePositiveInt(key.substring(1), "invalid annotation index");

      final String attr = nextOrIOException(fields, "missing annotation fields");
      final Iterator<String> attrFields = ATTR_SPLITTER.split(attr).iterator();

      final String attrType = nextOrIOException(attrFields, "missing annotation type/name");

      final String parentId = nextOrIOException(attrFields, "missing parent ID");

      final String value = nextOrNull(attrFields);

      return new BratAttributeAnnotation(index, attrType, parentId, value);

    } catch (IOException ioe) {
      throw new UncheckedIOException("Failed to parse line \"" + line + '"', ioe);
    }
  }

  public String getValue() {
    return value;
  }

  @Override
  public String getParentId() {
    return parentId;
  }

  @Override
  public String toString() {
    if (value == null) {
      return FIELD_JOINER.join(this.getId(), this.getType() + ' ' + parentId);
    } else {
      return FIELD_JOINER.join(this.getId(), this.getType() + ' ' + parentId + ' ' + value);
    }
  }

  @Override
  BratAttributeAnnotation shiftIndex(int shift) {
    return (BratAttributeAnnotation) super.shiftIndex(shift);
  }

  BratAttributeAnnotation shiftParent(int shift) {
    final char annotType = this.parentId.charAt(0);

    // Get current/original index
    int index = Integer.parseInt(this.parentId.substring(1));

    // Update id by shifting index;
    this.parentId = annotType + Integer.toString(index + shift);

    return this;
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
    BratAttributeAnnotation that = (BratAttributeAnnotation) o;
    return Objects.equals(parentId, that.parentId) &&
        Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), parentId, value);
  }
}

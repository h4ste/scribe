package com.github.h4ste.scribe;

public class Section extends TextAnnotation {
  private final String sectionName;

  public Section(CharSequence source, int start, int end, String sectionName) {
    super(source, start, end);
    this.sectionName = sectionName;
  }

  public String getSectionName() {
    return sectionName;
  }
}

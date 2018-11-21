package com.github.h4ste.scribe.legacy.text.relation;

import com.github.h4ste.scribe.legacy.text.annotation.AnnotationType;
import com.github.h4ste.scribe.legacy.text.annotation.Token;
import com.github.h4ste.scribe.legacy.text.Attribute;
import com.github.h4ste.scribe.legacy.text.Document;

public class Dependency extends AbstractRelation<Dependency, Token, Token> {

  public static final Attribute<Dependency, String> Label = Attribute.typed("label", String.class);

  public Dependency(final Document<?> document, final gate.relations.Relation relation, final String annset) {
    super(document, relation, annset);
  }

  @Override
  public RelationType<Dependency, Token, Token> relationType() {
    return TYPE;
  }

  public static RelationType<Dependency, Token, Token> TYPE = new AbstractRelationType<Dependency, Token, Token>("Dependency") {
    @Override
    public AnnotationType<Token> governorType() {
      return Token.TYPE;
    }

    @Override
    public AnnotationType<Token> dependantType() {
      return Token.TYPE;
    }

    @Override
    public Dependency wrap(Document<?> parent, gate.relations.Relation relation, String annSet) {
      return new Dependency(parent, relation, annSet);
    }
  };
}

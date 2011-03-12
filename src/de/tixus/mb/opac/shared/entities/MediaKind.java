package de.tixus.mb.opac.shared.entities;

public enum MediaKind {
  Book {

    @Override
    public String getCountAsString() {
      return "S.";
    }

    @Override
    public String getDescription() {
      return "Buch";
    }
  },
  BigFont {

    @Override
    public String getCountAsString() {
      return "S.";
    }

    @Override
    public String getDescription() {
      return "Gro\u00DFdruck";
    }
  },
  CompactDisc {

    @Override
    public String getCountAsString() {
      return "Stk.";
    }

    @Override
    public String getDescription() {
      return "CD";
    }
  };

  private MediaKind() {
  }

  public abstract String getCountAsString();

  public abstract String getDescription();
}

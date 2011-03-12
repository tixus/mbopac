package de.tixus.mb.opac.server;

import java.io.FileInputStream;

public class CSVImporter {

  public void parse(final String fileName) {

    final CSVParser csvParser = new CSVParser(new FileInputStream(fileName));
    for (String t; (t = csvParser.nextValue()) != null;)
      System.out.println(csvParser.lastLineNumber() + " " + t);
  }
}

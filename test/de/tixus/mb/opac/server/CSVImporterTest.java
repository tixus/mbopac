package de.tixus.mb.opac.server;

import junit.framework.TestCase;
import de.tixus.mb.opac.shared.entities.MediaKind;

public class CSVImporterTest extends TestCase {

  CSVImporter csvImporter = new CSVImporter();

  public void testBookImport() throws Exception {
    //    Nachtragskatalog_2011-book.csv
    csvImporter.parse("Nachtragskatalog_2011-book.csv", MediaKind.Book);
  }
}

package de.tixus.mb.opac.server;

import junit.framework.TestCase;
import de.tixus.mb.opac.shared.entities.MediaKind;

public class CSVImporterTest extends TestCase {

  CSVImporter csvImporter = new CSVImporter();

  public void testBookImport() throws Exception {
    csvImporter.parse("Katalog-Book-Tester.csv", MediaKind.Book);
    //    csvImporter.parse("Nachtragskatalog_2011-book.csv", MediaKind.Book);
  }
}

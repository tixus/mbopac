package de.tixus.mb.opac.server;

import junit.framework.TestCase;
import de.tixus.mb.opac.shared.entities.MediaKind;

public class CsvImporterTest extends TestCase {

  CsvImporter csvImporter = new CsvImporter();

  public void testMultipleErrorsImport() throws Exception {
    csvImporter.parse("C:/project/selfstudy/opac/mbopac/test/resource/Katalog-Book-Tester.csv", MediaKind.Book);
  }

  public void testBookImport() throws Exception {
    csvImporter.parse("C:/project/selfstudy/opac/mbopac/src/de/tixus/mb/opac/server/resource/Nachtragskatalog_2011-book.csv",
                      MediaKind.Book);
  }

  public void testBigFontImport() throws Exception {
    csvImporter.parse("C:/project/selfstudy/opac/mbopac/src/de/tixus/mb/opac/server/resource/Nachtragskatalog_2011-bigfont.csv",
                      MediaKind.BigFont);
  }

  public void testCdImport() throws Exception {
    csvImporter.parse("C:/project/selfstudy/opac/mbopac/src/de/tixus/mb/opac/server/resource/Nachtragskatalog_2011-cd.csv",
                      MediaKind.CompactDisc);
  }
}

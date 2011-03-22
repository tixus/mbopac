package de.tixus.mb.opac.server;

import junit.framework.TestCase;
import de.tixus.mb.opac.shared.entities.MediaKind;

public class CSVImporterTest extends TestCase {

  CSVImporter csvImporter = new CSVImporter();

  public void testMultipleErrorsImport() throws Exception {
    csvImporter.parse("C:/project/selfstudy/opac/mbopac/war/WEB-INF/Katalog-Book-Tester.csv", MediaKind.Book);
  }

  public void testBookImport() throws Exception {
    csvImporter.parse("C:/project/selfstudy/opac/mbopac/war/WEB-INF/Nachtragskatalog_2011-book.csv", MediaKind.Book);
  }

  public void testBigFontImport() throws Exception {
    csvImporter.parse("C:/project/selfstudy/opac/mbopac/war/WEB-INF/Nachtragskatalog_2011-bigfont.csv", MediaKind.BigFont);
  }

  public void testCdImport() throws Exception {
    csvImporter.parse("C:/project/selfstudy/opac/mbopac/war/WEB-INF/Nachtragskatalog_2011-cd.csv", MediaKind.CompactDisc);
  }
}

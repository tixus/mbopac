package de.tixus.mb.opac.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;

import de.tixus.mb.opac.shared.entities.MediaKind;

public class CSVImporter {

  public void parse(final String fileName, final MediaKind mediaKind) throws IOException {

    // Mediennummer, Autor, Titel, Erscheinungsjahr, Umfang, Genre, Kurze Inhaltsangabe
    final LabeledCSVParser labeledCSVParser = new LabeledCSVParser(new CSVParser(new FileInputStream(fileName), ';'));

    final String[] labels = labeledCSVParser.getLabels();
    System.out.println("Spaltenamen: " + Arrays.asList(labels));

    while (labeledCSVParser.getLine() != null) {
      labeledCSVParser.getValueByLabel("Mediennummer");
      labeledCSVParser.getValueByLabel("Autor");
      labeledCSVParser.getValueByLabel("Titel");
      labeledCSVParser.getValueByLabel("Erscheinungsjahr");
      labeledCSVParser.getValueByLabel("Umfang");
      // Set
      labeledCSVParser.getValueByLabel("Genre");
      labeledCSVParser.getValueByLabel("Kurze Inhaltsangabe");
    }
  }
}

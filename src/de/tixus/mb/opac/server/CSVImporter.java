package de.tixus.mb.opac.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;

import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;

public class CSVImporter {

  public List<MediaItem> parse(final String fileName, final MediaKind mediaKind) throws IOException {

    // Mediennummer, Autor, Titel, Erscheinungsjahr, Umfang, Genre, Kurze Inhaltsangabe
    final LabeledCSVParser labeledCSVParser = new LabeledCSVParser(new CSVParser(new FileInputStream(fileName), ';'));

    final String[] labels = labeledCSVParser.getLabels();
    // TODO assert labels present
    System.out.println("Spaltenamen: " + Arrays.asList(labels));
    final List<MediaItem> mediaItems = new ArrayList<MediaItem>();
    while (labeledCSVParser.getLine() != null) {

      final String mediaNumber = labeledCSVParser.getValueByLabel("Mediennummer");
      final String author = labeledCSVParser.getValueByLabel("Autor");
      final String title = labeledCSVParser.getValueByLabel("Titel");
      final String publicationYear = labeledCSVParser.getValueByLabel("Erscheinungsjahr");
      final String count = labeledCSVParser.getValueByLabel("Umfang");
      // Set
      final String genres = labeledCSVParser.getValueByLabel("Genre");
      final String shortDescription = labeledCSVParser.getValueByLabel("Kurze Inhaltsangabe");

      // holder trying to cleanup input data
      // toMediaItem utilizing new MediaItem validations
      final MediaItemHolder mediaItemHolder = new MediaItemHolder(mediaNumber, title, shortDescription, author, publicationYear, mediaKind,
                                                                  count, genres);
      final MediaItem mediaItem = mediaItemHolder.toMediaItem();
      if (mediaItem != null) {
        mediaItems.add(mediaItem);
      }
    }

    return mediaItems.isEmpty() ? java.util.Collections.EMPTY_LIST : mediaItems;
  }
}

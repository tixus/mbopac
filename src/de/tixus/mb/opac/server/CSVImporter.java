package de.tixus.mb.opac.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;

import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;

public class CsvImporter {

  public List<MediaItem> parse(final String fileName, final MediaKind mediaKind) throws IOException {
    System.err.println("Lese Datei: " + fileName);

    return parse(new FileInputStream(fileName), mediaKind);
  }

  public List<MediaItem> parse(final InputStream inputStream, final MediaKind mediaKind) throws IOException {
    // Mediennummer, Autor, Titel, Erscheinungsjahr, Umfang, Genre, Kurze Inhaltsangabe

    final InputStreamReader utf8InputStreamReader = new InputStreamReader(inputStream, "UTF-8");

    final LabeledCSVParser labeledCSVParser = new LabeledCSVParser(new CSVParser(utf8InputStreamReader, ';'));

    final String[] parsedLabels = labeledCSVParser.getLabels();
    final String[] requiredLabels = { "Mediennummer", "Autor", "Titel", "Erscheinungsjahr", "Umfang", "Genre", "Kurze Inhaltsangabe" };

    System.err.println("Erforderliche Spaltenamen: " + Arrays.asList(requiredLabels));
    System.err.println("Gefundene Spaltennamen: " + Arrays.asList(parsedLabels));

    //    final List<String> requiredLabelsList = Arrays.asList(requiredLabels);
    //    if (!Arrays.deepEquals(parsedLabels, requiredLabels)) {
    //      System.err.println("Erforderliche Spaltenamen: " + Arrays.asList(requiredLabels));
    //      System.err.println("Gefundene Spaltennamen: " + Arrays.asList(parsedLabels));
    //      return Collections.EMPTY_LIST;
    //    }

    final List<MediaItem> mediaItems = new ArrayList<MediaItem>();
    final Set<String> allGenres = new HashSet<String>();
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
        allGenres.addAll(mediaItem.getGenres());
      }
    }

    return mediaItems.isEmpty() ? java.util.Collections.EMPTY_LIST : mediaItems;
  }
}

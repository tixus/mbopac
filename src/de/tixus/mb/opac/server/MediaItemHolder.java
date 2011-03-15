package de.tixus.mb.opac.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;

public class MediaItemHolder {

  private final String mediaNumber;
  private final String title;
  private final String shortDescription;
  private final String author;
  private final String publicationYear;
  private final MediaKind mediaKind;
  private final String count;
  private final String genres;

  public MediaItemHolder(final String mediaNumber, final String title, final String shortDescription, final String author,
                         final String publicationYear, final MediaKind mediaKind, final String count, final String genres) {
    this.mediaNumber = mediaNumber;
    this.title = title;
    this.shortDescription = shortDescription;
    this.author = author;
    this.publicationYear = publicationYear;
    this.mediaKind = mediaKind;
    this.count = count;
    this.genres = genres;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("MediaItemHolder [mediaNumber=");
    builder.append(mediaNumber);
    builder.append(", title=");
    builder.append(title);
    builder.append(", shortDescription=");
    builder.append(shortDescription);
    builder.append(", author=");
    builder.append(author);
    builder.append(", publicationYear=");
    builder.append(publicationYear);
    builder.append(", mediaKind=");
    builder.append(mediaKind);
    builder.append(", count=");
    builder.append(count);
    builder.append(", genres=");
    builder.append(genres);
    builder.append("]");
    return builder.toString();
  }

  public MediaItem toMediaItem() {
    final List<String> validation = new ArrayList<String>();

    final String mediaNumberFixed = getMediaNumber(validation);
    final String authorTrimmed = author.replace(" ", "");
    //    author=Schmidt, Helmut / Stern, Fritz =>?
    final String[] multipleAuthorsSplit = authorTrimmed.split("/");
    final String[] authorSplit = multipleAuthorsSplit[0].split(",");
    if (multipleAuthorsSplit.length > 2) {
      System.err.println(" zu viele Autoren: " + author + "; verarbeite ersten Autor: " + Arrays.asList(authorSplit));
    }
    Author authorFixed = null;
    if (authorSplit.length == 0) {
      validation.add(" Autor zu kurz: " + author);
    } else if (authorSplit.length == 1) {
      //      Klein Georg -> Vorname Nachname
      //      Loriot ->Nachname

    } else {
      //    author=Raabe, Wilhelm
      //    André-Lang/Rast, Harald
      String firstName = authorSplit[1];
      String lastName = authorSplit[0];
      //    author=Hampel, Thomas (Hrsg.) => Thomas Hampel (Hrsg.)
      final int hrsgIndex = firstName.indexOf("(Hrsg.)");
      if (hrsgIndex > 0) {
        firstName = firstName.substring(0, hrsgIndex);
        lastName += "(Hrsg.)";
      }
      authorFixed = new Author(firstName, lastName);
    }

    final String titleFixed = title.trim();
    if (titleFixed.isEmpty()) {
      validation.add("Titel ist leer");
    }
    //    shortDescription= linefeed
    final String shortDescriptionFixed = shortDescription.trim();
    if (shortDescriptionFixed.isEmpty()) {
      validation.add("Kurzbeschreibung ist leer");
    }

    //    genres=Humor, Satire
    final String[] genresSplit = genres.trim().split(",");
    final Set<String> genresFixed = new HashSet<String>(Arrays.asList(genresSplit));

    Date publicationYearFixed = null;
    try {
      publicationYearFixed = new Date(Integer.valueOf(publicationYear), 0, 0);
    } catch (final NumberFormatException nfe) {
      validation.add("Erscheinungsjahr ist keine Zahl: " + publicationYear);
    }
    Integer countFixed = null;
    try {
      countFixed = Integer.valueOf(count);
    } catch (final NumberFormatException nfe) {
      validation.add("Umfang ist keine Zahl: " + count);
    }

    if (!validation.isEmpty()) {
      System.err.println("Datensatz ungültig: " + validation.toString());
      return null;
    }

    final String id = UUID.nameUUIDFromBytes(mediaNumberFixed.getBytes()).toString();

    return new MediaItem(id, mediaNumberFixed, titleFixed, shortDescriptionFixed, authorFixed, publicationYearFixed, mediaKind, countFixed,
                         genresFixed);
  }

  private String getMediaNumber(final List<String> validation) {
    //    M60 126 377 X
    //    ""
    final String mediaNumberTrimmed = mediaNumber.replace(" ", "");
    if (mediaNumberTrimmed.isEmpty()) {
      validation.add(" ist leer");
      return null;
    }

    //    M595661201 M601263781
    if (!mediaNumberTrimmed.startsWith("M")) {
      validation.add(" beginnt nicht mit 'M'");
      return null;
    }

    // M59566580X
    // validate 9 chars, "X" is valid checksum
    if (mediaNumberTrimmed.substring(1).length() != 9) {
      validation.add(" enthält nicht 9 Stellen nach führendem 'M'");
      return null;
    }

    return mediaNumberTrimmed;
  }
}

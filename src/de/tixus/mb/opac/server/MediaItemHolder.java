package de.tixus.mb.opac.server;

import java.util.ArrayList;
import java.util.Arrays;
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
    final List<String> validationError = new ArrayList<String>();
    final List<String> validationInfo = new ArrayList<String>();

    final String mediaNumberFixed = getMediaNumber(validationError, validationInfo);
    final Author authorFixed = getAuthor(validationError, validationInfo);

    final String titleFixed = title.trim();
    if (titleFixed.isEmpty()) {
      validationInfo.add("Titel ist leer");
    }
    //    shortDescription= linefeed
    final String shortDescriptionFixed = shortDescription.trim();
    if (shortDescriptionFixed.isEmpty()) {
      validationInfo.add("Kurzbeschreibung ist leer");
    }

    //    genres=Humor, Satire
    final String[] genresSplit = genres.trim().split(",");
    final Set<String> genresFixed = new HashSet<String>(Arrays.asList(genresSplit));
    if (genresFixed.isEmpty()) {
      validationInfo.add("Genres ist leer");
    }

    Integer publicationYearFixed = null;
    try {
      publicationYearFixed = Integer.valueOf(publicationYear);
    } catch (final NumberFormatException nfe) {
      validationError.add("Erscheinungsjahr ist keine Zahl: " + publicationYear);
    }
    Integer countFixed = null;
    try {
      countFixed = Integer.valueOf(count.replace(".", ""));
    } catch (final NumberFormatException nfe) {
      validationError.add("Umfang ist keine Zahl: " + count);
    }

    System.out.println(this.toString());

    if (!validationError.isEmpty()) {
      System.out.println("Datensatz ungültig: " + validationError.toString());
      return null;
    }

    final String id = UUID.nameUUIDFromBytes(mediaNumberFixed.getBytes()).toString();

    final MediaItem mediaItem = new MediaItem(id, mediaNumberFixed, titleFixed, shortDescriptionFixed, authorFixed, publicationYearFixed,
                                              mediaKind, countFixed, genresFixed);
    if (!validationInfo.isEmpty()) {
      System.out.println("Datensatz gültig aber Warnungen: " + validationInfo.toString());
    }

    System.out.println("Erkannter Datensatz: " + mediaItem);
    return mediaItem;
  }

  private Author getAuthor(final List<String> validationError, final List<String> validationInfo) {
    final String authorTrimmed = author.replace(" ", "");
    //    author=Schmidt, Helmut / Stern, Fritz =>?
    final String[] multipleAuthorsSplit = authorTrimmed.split("/");
    final String[] authorSplit = multipleAuthorsSplit[0].split(",");
    if (multipleAuthorsSplit.length > 1) {
      validationInfo.add(" zu viele Autoren: " + author + "; verarbeite ersten Autor: " + Arrays.asList(authorSplit));
    }

    Author authorFixed = null;
    if (authorSplit.length == 0) {
      validationError.add(" Autor zu kurz: " + author);
    } else if (authorSplit.length == 1) {
      //      Klein Georg -> Vorname Nachname
      //      Loriot ->Nachname
      //    André-Lang/Rast, Harald
      final String firstName = "";
      final String lastName = authorSplit[0];
      authorFixed = new Author(firstName, lastName);

      validationInfo.add("Nur ein Name zu Autor angegeben, verarbeite als Nachname: " + lastName);
    } else {
      //    author=Raabe, Wilhelm
      String firstName = authorSplit[1];
      String lastName = authorSplit[0];
      //    author=Hampel, Thomas (Hrsg.) => Thomas Hampel (Hrsg.)
      final int hrsgIndex = firstName.indexOf("(Hrsg.)");
      if (hrsgIndex > 0) {
        firstName = firstName.substring(0, hrsgIndex);
        lastName += " (Hrsg.)";
      }
      authorFixed = new Author(firstName, lastName);
    }
    return authorFixed;
  }

  private String getMediaNumber(final List<String> validationError, final List<String> validationInfo) {
    //    M60 126 377 X
    //    ""
    final String mediaNumberTrimmed = mediaNumber.replace(" ", "");
    if (mediaNumberTrimmed.isEmpty()) {
      validationError.add(" ist leer");
      return null;
    }

    //    M595661201 M601263781
    if (!mediaNumberTrimmed.startsWith("M")) {
      validationError.add(" beginnt nicht mit 'M'");
      return null;
    }

    // M59566580X
    // validate 9 chars, "X" is valid checksum
    if (mediaNumberTrimmed.substring(1).length() != 9) {
      validationError.add(" enthält nicht 9 Stellen nach führendem 'M'");
      return null;
    }

    return mediaNumberTrimmed;
  }
}

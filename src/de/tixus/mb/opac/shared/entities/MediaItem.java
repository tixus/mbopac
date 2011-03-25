/**
 * 
 */
package de.tixus.mb.opac.shared.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;

/**
 * @author TSP
 * 
 */

//@Entity
public class MediaItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Transient
  public final static String[] genresType = new String[] { "Familie", "Reisen", "Biografie", "Hist. Roman", "Tagebuch", "Tragikkomödie",
                                                          "Hamburg", "Psychologie", "Anthologie", "Satire", "Medizin", "Fantasy", "Roman",
                                                          "Naturwissensch.", "med. Erlebnisbericht", "Thriller", "Ratgeber", "Reportage",
                                                          "Plattdeutsch", "Biographie", "Aufsatzsammlung", "Autobiografie", "Religion",
                                                          "Hist. Krimi", "Erzählung", "Klassiker", "Meistererzählung", "Historisch",
                                                          "Geschichte", "Reisebericht", "Wirtschaft", "Krimi", "Humor", "Belletristik",
                                                          "Liebe", "Wissenschaft", "Frauen", "Erlebnisbericht", "Märchen", "Politik",
                                                          "Autobiographie", "Lyrik" };

  @Id
  private String id;
  private String mediaNumber;
  private String title;
  private String shortDescription;
  @Embedded
  private Author author;
  private Integer publicationYear;
  private MediaKind mediaKind;
  private Integer count;
  private Set<String> genres;
  private Key<Person> lentTo;

  public MediaItem() {
  }

  public MediaItem(final String id, final String mediaNumber, final String title, final String shortDescription, final Author author,
                   final Integer publicationYear, final MediaKind kind, final Integer count, final Set<String> genres) {
    this.id = id;
    this.mediaNumber = mediaNumber;
    this.title = title;
    this.shortDescription = shortDescription;
    this.author = author;
    this.publicationYear = publicationYear;
    this.mediaKind = kind;
    this.count = count;
    this.genres = genres;
  }

  public Author getAuthor() {
    return author;
  }

  public Set<String> getGenres() {
    return genres;
  }

  public String getId() {
    return id;
  }

  public MediaKind getKind() {
    return mediaKind;
  }

  public Integer getCount() {
    return count;
  }

  public String getMediaNumber() {
    return mediaNumber;
  }

  public Integer getPublicationYear() {
    return publicationYear;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("MediaItem [id=");
    builder.append(id);
    builder.append(", Mediennummer=");
    builder.append(mediaNumber);
    builder.append(", Titel=");
    builder.append(title);
    builder.append(", Kurzbeschreibung=");
    builder.append(shortDescription);
    builder.append(", Autor=");
    builder.append(author);
    builder.append(", Erscheinungsjahr=");
    builder.append(publicationYear);
    builder.append(", Art=");
    builder.append(mediaKind);
    builder.append(", Umfang=");
    builder.append(count);
    builder.append(", Genres=");
    builder.append(genres);
    builder.append("]");
    return builder.toString();
  }

  public void update(final String title,
                     final String shortDescription,
                     final Author author,
                     final Integer publicationYear,
                     final MediaKind kind,
                     final Set<String> genres) {
    this.title = title;
    this.shortDescription = shortDescription;
    this.author = author;
    this.publicationYear = publicationYear;
    this.mediaKind = kind;
    this.genres = genres;
  }

  public void lendTo(final Person person) {
    this.lentTo = new Key<Person>(Person.class, person.getId());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final MediaItem other = (MediaItem) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public boolean isLent() {
    return lentTo != null;
  }

  public void endLending(final Lending lending) {
    lentTo = null;
    lending.end();
  }
}

/**
 * 
 */
package de.tixus.mb.opac.client.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;

/**
 * @author TSP
 * 
 */

//@Entity
public class MediaItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String id;
  public String mediaNumber;
  public String title;
  public String shortDescription;
  @Embedded
  public Author author;
  public Date publicationYear;
  public MediaKind kind;
  public Set<String> genres;
  public Key<Person> lentTo;

  public MediaItem() {
  }

  public MediaItem(final String id, final String mediaNumber, final String title, final String shortDescription, final Author author,
                   final Date publicationYear, final MediaKind kind, final Set<String> genres) {
    this.id = id;
    this.mediaNumber = mediaNumber;
    this.title = title;
    this.shortDescription = shortDescription;
    this.author = author;
    this.publicationYear = publicationYear;
    this.kind = kind;
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
    return kind;
  }

  public String getMediaNumber() {
    return mediaNumber;
  }

  public Date getPublicationYear() {
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
    builder.append(", mediaNumber=");
    builder.append(mediaNumber);
    builder.append(", title=");
    builder.append(title);
    builder.append(", shortDescription=");
    builder.append(shortDescription);
    builder.append(", author=");
    builder.append(author);
    builder.append(", publicationYear=");
    builder.append(publicationYear);
    builder.append(", kind=");
    builder.append(kind);
    builder.append(", genres=");
    builder.append(genres);
    builder.append("]");
    return builder.toString();
  }

  public void update(final String title,
                     final String shortDescription,
                     final Author author,
                     final Date publicationYear,
                     final MediaKind kind,
                     final Set<String> genres) {
    this.title = title;
    this.shortDescription = shortDescription;
    this.author = author;
    this.publicationYear = publicationYear;
    this.kind = kind;
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

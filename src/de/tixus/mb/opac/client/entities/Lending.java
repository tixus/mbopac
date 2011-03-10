/**
 * 
 */
package de.tixus.mb.opac.client.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

/**
 * A lent item is a relationship between one person and one mediaItem.
 * 
 * @author TSP
 * 
 */
//@Entity
public class Lending implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String id;
  private Key<Person> person;
  private Key<MediaItem> mediaItem;
  private Date dateLent;
  private Date dateReturned;

  public Lending() {
  }

  public Lending(final String id, final Person person, final MediaItem mediaItem) {
    this.id = id;
    this.person = new Key<Person>(Person.class, person.getId());
    this.mediaItem = new Key<MediaItem>(MediaItem.class, mediaItem.getId());
    dateLent = new Date();
  }

  public void end() {
    dateReturned = new Date();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Lending other = (Lending) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public String getId() {
    return id;
  }

  public Key<Person> getPerson() {
    return this.person;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("Lending [id=");
    builder.append(id);
    builder.append(", person=");
    builder.append(person);
    builder.append(", dateLent=");
    builder.append(dateLent);
    builder.append(", dateReturned=");
    builder.append(dateReturned);
    builder.append("]");
    return builder.toString();
  }
}

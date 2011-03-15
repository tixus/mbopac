package de.tixus.mb.opac.factory;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.googlecode.objectify.Key;

import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.Gender;
import de.tixus.mb.opac.shared.entities.Lending;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;
import de.tixus.mb.opac.shared.entities.Person;
import de.tixus.mb.opac.shared.entities.TypeOfPerson;

public class ObjectifyTestFactory {

  public Person createMaleCustomer(final String firstName, final String lastName) {

    final String userName = lastName;
    final String password = firstName;
    final String email = firstName + "." + lastName + "@example.com";

    return new Person(UUID.nameUUIDFromBytes(userName.getBytes()).toString(), TypeOfPerson.Customer, firstName, lastName, Gender.Male,
                      userName, password, email);
  }

  public MediaItem createMediaItem(final String title, final String mediaNumber) {
    final String shortDescription = null;
    final Author author = null;
    final Date publicationYear = null;
    final MediaKind kind = null;
    final Set<String> genres = null;
    return new MediaItem(UUID.nameUUIDFromBytes(mediaNumber.getBytes()).toString(), mediaNumber, title, shortDescription, author,
                         publicationYear, kind, 1, genres);
  }

  public Lending createLending(final Person person, final MediaItem mediaItem) {
    final Key<Person> key = new Key<Person>(Person.class, person.getId());

    return new Lending(UUID.randomUUID().toString(), person, mediaItem);
  }
}

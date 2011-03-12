package de.tixus.mb.opac.server;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.Gender;
import de.tixus.mb.opac.shared.entities.Lending;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;
import de.tixus.mb.opac.shared.entities.Person;
import de.tixus.mb.opac.shared.entities.TypeOfPerson;

public class DataImporter {

  final String[] genres = new String[] { "Belletristik", "Krimi", "Technik", "Thriller", "Frauen", "Gesellschaft und Politik" };
  final String[] author = new String[] { "FrankA", "FrankB", "FrankC", "FrankD", "FrankF" };

  private final ObjectifyDao dao;

  public DataImporter(final ObjectifyDao dao) {
    this.dao = dao;
  }

  public void clear() {
    final List listAll = dao.listAll(MediaItem.class);
    listAll.addAll(dao.listAll(Person.class));
    listAll.addAll(dao.listAll(Lending.class));

    dao.delete(listAll.toArray());
  }

  public void importMediaItemData() {
    final String number = "M123456789";
    final MediaKind[] mediaKind = MediaKind.values();

    for (int i = 0; i < author.length; i++) {
      final String mediaNumber = number + i;
      final String id = UUID.nameUUIDFromBytes(mediaNumber.getBytes()).toString();
      final Set<String> genreSet = new HashSet<String>(Arrays.asList(genres[genres.length % (i + 1)]));
      final MediaItem mediaItem = new MediaItem(id, mediaNumber, author[i] + "Titel", author[i] + "Kurzbeschreibung",
                                                new Author("Vorname", author[i]), new Date(), mediaKind[i % mediaKind.length], genreSet);

      dao.create(mediaItem);
    }

  }

  public void importPersonData() {
    final Gender[] gender = Gender.values();

    for (int i = 0; i < 10; i++) {

      final String lastName = "Musterfrau" + i;
      final String firstName = "Suse" + i;
      final String email = firstName + "." + lastName + "@example.com";
      final String userName = "11007411" + i;
      final String password = "1970";

      final Person person = new Person(UUID.nameUUIDFromBytes(userName.getBytes()).toString(), TypeOfPerson.Customer, firstName, lastName,
                                       gender[i % gender.length], userName, password, email);
      dao.create(person);
    }
  }

  public void importLendingData() {
    final List<Lending> listAllLendings = dao.listAll(Lending.class);
    if (listAllLendings.isEmpty()) {
      final List<MediaItem> allMediaItems = dao.listAll(MediaItem.class);
      final List<Person> listAllPersons = dao.listAll(Person.class);
      int i = 0;
      for (final MediaItem mediaItem : allMediaItems) {
        final Person person = listAllPersons.get(i++);
        final Lending lending = new Lending(UUID.randomUUID().toString(), person, mediaItem);

        mediaItem.lendTo(person);

        dao.create(lending);
        dao.update(mediaItem);
      }
    }
  }
}

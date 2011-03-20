package de.tixus.mb.opac.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.googlecode.objectify.Key;

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
  private final CSVImporter csvImporter = new CSVImporter();

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
    //    final String number = "M123456789";
    //    final MediaKind[] mediaKind = MediaKind.values();
    //
    //    for (int i = 0; i < author.length; i++) {
    //      final String mediaNumber = number + i;
    //      final String id = UUID.nameUUIDFromBytes(mediaNumber.getBytes()).toString();
    //      final Set<String> genreSet = new HashSet<String>(Arrays.asList(genres[genres.length % (i + 1)]));
    //      final Integer count = i;
    //      final MediaItem mediaItem = new MediaItem(id, mediaNumber, author[i] + "Titel", author[i] + "Kurzbeschreibung",
    //                                                new Author("Vorname", author[i]), 2010, mediaKind[i % mediaKind.length], count, genreSet);
    //
    //      dao.create(mediaItem);
    //    }

    try {
      final List<MediaItem> mediaItems = csvImporter.parse("C:/project/selfstudy/opac/mbopac/war/WEB-INF/Nachtragskatalog_2011-book.csv",
                                                           MediaKind.Book);

      // TODO 
      for (final MediaItem mediaItem : mediaItems) {
        final Key<MediaItem> key = new Key<MediaItem>(MediaItem.class, mediaItem.getId());
        if (dao.contains(key)) {
          System.out.println("Ignoring existing entity: " + mediaItem);
          continue;
        }
        dao.create(mediaItem);
      }

    } catch (final IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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
      for (final Person person : listAllPersons) {
        final MediaItem mediaItem = allMediaItems.get(i++);
        final Lending lending = new Lending(UUID.randomUUID().toString(), person, mediaItem);

        mediaItem.lendTo(person);

        dao.create(lending);
        dao.update(mediaItem);
      }
    }
  }
}

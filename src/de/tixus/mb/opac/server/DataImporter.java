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

    dao.delete(listAll);
  }

  public void importMediaItemData() {
    final List<MediaItem> allMediaItems = dao.listAll(MediaItem.class);
    try {
      final String fileNameBook = "C:/project/selfstudy/opac/mbopac/war/WEB-INF/Nachtragskatalog_2011-book.csv";
      final String fileNameCd = "C:/project/selfstudy/opac/mbopac/war/WEB-INF/Nachtragskatalog_2011-cd.csv";
      final String fileNameBigFont = "C:/project/selfstudy/opac/mbopac/war/WEB-INF/Nachtragskatalog_2011-bigfont.csv";

      final List<MediaItem> mediaItems = csvImporter.parse(fileNameBook, MediaKind.Book);
      mediaItems.addAll(csvImporter.parse(fileNameCd, MediaKind.CompactDisc));
      mediaItems.addAll(csvImporter.parse(fileNameBigFont, MediaKind.BigFont));

      int i = 0;
      for (final MediaItem mediaItem : mediaItems) {
        final Key<MediaItem> key = new Key<MediaItem>(MediaItem.class, mediaItem.getId());
        if (dao.contains(key)) {
          System.out.println("Ignoring existing entity: " + mediaItem);
          continue;
        }
        dao.create(mediaItem);
        System.out.println("Imported: #" + i++ + "." + mediaItem);
      }

    } catch (final IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void importPersonData() {
    final Gender[] gender = Gender.values();

    final String[] lastNames = { "Zeisig", "Sperling", "Taube", "Spatz", "Amsel", "Drossel", "Fink", "Meise", "Specht", "Habicht" };
    final String[] firstNames = { "Frank", "Tino", "Gerhard", "Uwe", "Ulla", "Erika", "Katrin", "Peter", "Petra", "Silke" };

    for (int i = 0; i < 10; i++) {

      final String lastName = lastNames[i];
      final String firstName = firstNames[i];
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

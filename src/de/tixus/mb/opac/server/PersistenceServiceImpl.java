package de.tixus.mb.opac.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;

import de.tixus.mb.opac.client.PersistenceService;
import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.Lending;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;
import de.tixus.mb.opac.shared.entities.Person;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PersistenceServiceImpl extends RemoteServiceServlet implements PersistenceService {

  private final ObjectifyDao dao = new ObjectifyDao();
  private final DataImporter dataImporter = new DataImporter(dao);

  public PersistenceServiceImpl() {
  }

  public void setUp() {
    dataImporter.clear();
    dataImporter.importMediaItemData();
    dataImporter.importPersonData();
    dataImporter.importLendingData();
  }

  public MediaItem createMediaItem(final String titleParam,
                                   final String mediaNumberParam,
                                   final String shortDescription,
                                   final Author author,
                                   final Integer publicationYear,
                                   final MediaKind kind,
                                   final Integer count,
                                   final Set<String> genres) throws IllegalArgumentException {

    // Escape data from the client to avoid cross-site script vulnerabilities.
    final String title = escapeHtml(titleParam);
    final String mediaNumber = escapeHtml(mediaNumberParam);
    final String id = UUID.nameUUIDFromBytes(mediaNumber.getBytes()).toString();

    final MediaItem mediaItem = new MediaItem(id, mediaNumberParam, title, shortDescription, author, publicationYear, kind, count, genres);

    dao.create(mediaItem);

    return mediaItem;
  }

  public void delete(final Serializable serializable) throws IllegalArgumentException {
    dao.delete(serializable);
  }

  public List<MediaItem> listAllMediaItems() {
    final List<MediaItem> listAll = dao.listAll(MediaItem.class);
    return listAll;
  }

  @Override
  public List<Person> listAllPersons() {
    final List<Person> listAll = dao.listAll(Person.class);
    return listAll;
  }

  @Override
  public List<MediaItem> listAllLendings(final Person person) {
    final List<MediaItem> listAll = dao.find(MediaItem.class, "lentTo", person);
    return listAll;
  }

  @Override
  public Lending endLending(final MediaItem mediaItem) {
    final Lending lending = dao.findUnique(Lending.class, "mediaItem", mediaItem);
    assert lending != null;

    mediaItem.endLending(lending);
    dao.update(mediaItem, lending);
    return lending;
  }

  public <T> List<T> listByProperty(final Class<T> clazz, final String propName, final Object propValue) {
    return dao.listByProperty(clazz, propName, propValue);
  }

  public void update(final Serializable serializable) throws IllegalArgumentException {
    dao.update(serializable);
  }

  public <T extends Serializable> T get(final Key<T> key) {
    return dao.get(key);
  }

  public <T extends Serializable> boolean contains(final Key<T> key) {
    return dao.contains(key);
  }

  /**
   * Escape an html string. Escaping data received from the client helps to prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(final String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
  }

  @Override
  public List<MediaItem> search(final String mediaNumber,
                                final String title,
                                final Author author,
                                final Integer publicationYear,
                                final MediaKind mediaKind,
                                final Set<String> genreSet) {

    final Map<String, Object> filterMap = new HashMap<String, Object>();
    // like match - only one allowed per query: "Only one inequality filter per query is supported.  Encountered both mediaNumber and title"
    // http://googlecode.blogspot.com/2010/05/google-app-engine-basic-text-search.html
    if (mediaNumber != null && !mediaNumber.isEmpty()) {
      filterMap.put("mediaNumber >=", mediaNumber);
      filterMap.put("mediaNumber <", mediaNumber + "\ufffd");
    } else if (title != null && !title.isEmpty()) {
      filterMap.put("title >=", title);
      filterMap.put("title <", title + "\ufffd");
    }

    if (author != null) {
      final String firstName = author.getFirstName();
      if (firstName != null) {
        filterMap.put("author.firstName", firstName);
      }
      final String lastName = author.getLastName();
      if (lastName != null) {
        filterMap.put("author.lastName", lastName);
      }
    }
    // exakt match or null
    filterMap.put("publicationYear", publicationYear);
    filterMap.put("mediaKind", mediaKind);
    filterMap.put("genres IN", genreSet);

    return dao.find(MediaItem.class, filterMap);
  }
}

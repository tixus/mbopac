package de.tixus.mb.opac.client;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.Lending;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;
import de.tixus.mb.opac.shared.entities.Person;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface PersistenceServiceAsync {

  void update(Serializable serializable, AsyncCallback<Void> callback);

  void delete(Serializable serializable, AsyncCallback<Void> callback);

  void listAllMediaItems(AsyncCallback<List<MediaItem>> callback);

  void listAllPersons(AsyncCallback<List<Person>> asyncCallback);

  void listAllLendings(Person person, AsyncCallback<List<MediaItem>> asyncCallback);

  void endLending(MediaItem mediaItem, AsyncCallback<Lending> callback);

  <T extends Serializable> void get(Key<T> key, AsyncCallback<T> callback);

  void search(final String mediaNumber,
              final String title,
              final Author author,
              final Date publicationYear,
              final MediaKind selectedMediaKind,
              final Set<String> genreSet,
              AsyncCallback<List<MediaItem>> asyncCallback);

  void setUp(AsyncCallback<Void> callback);

  void createMediaItem(String titleParam,
                       String mediaNumberParam,
                       String shortDescription,
                       Author author,
                       Date publicationYear,
                       MediaKind kind,
                       Integer count,
                       Set<String> genres,
                       AsyncCallback<MediaItem> callback);

}

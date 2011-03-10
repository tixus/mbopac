package de.tixus.mb.opac.client;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.tixus.mb.opac.client.entities.Author;
import de.tixus.mb.opac.client.entities.Lending;
import de.tixus.mb.opac.client.entities.MediaItem;
import de.tixus.mb.opac.client.entities.MediaKind;
import de.tixus.mb.opac.client.entities.Person;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface PersistenceServiceAsync {

  void update(Serializable serializable, AsyncCallback<Void> callback);

  void delete(Serializable serializable, AsyncCallback<Void> callback);

  void listAllMediaItems(AsyncCallback<List<MediaItem>> callback);

  void createMediaItem(String titleParam,
                       String mediaNumberParam,
                       String shortDescription,
                       Author author,
                       Date publicationYear,
                       MediaKind kind,
                       Set<String> genres,
                       AsyncCallback<MediaItem> callback);

  void listAllPersons(AsyncCallback<List<Person>> asyncCallback);

  void listAllLendings(Person person, AsyncCallback<List<MediaItem>> asyncCallback);

  void endLending(MediaItem mediaItem, AsyncCallback<Lending> callback);
}

package de.tixus.mb.opac.client;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.tixus.mb.opac.client.entities.Author;
import de.tixus.mb.opac.client.entities.Lending;
import de.tixus.mb.opac.client.entities.MediaItem;
import de.tixus.mb.opac.client.entities.MediaKind;
import de.tixus.mb.opac.client.entities.Person;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface PersistenceService extends RemoteService {

  void update(Serializable serializable) throws IllegalArgumentException;

  void delete(Serializable serializable) throws IllegalArgumentException;

  MediaItem createMediaItem(String titleParam,
                            String mediaNumberParam,
                            String shortDescription,
                            Author author,
                            Date publicationYear,
                            MediaKind kind,
                            Set<String> genres) throws IllegalArgumentException;

  List<MediaItem> listAllMediaItems();

  List<Person> listAllPersons();

  List<MediaItem> listAllLendings(Person person);

  Lending endLending(MediaItem mediaItem);
}

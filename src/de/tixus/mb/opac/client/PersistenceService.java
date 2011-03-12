package de.tixus.mb.opac.client;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;

import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.Lending;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;
import de.tixus.mb.opac.shared.entities.Person;

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

  <T extends Serializable> T get(Key<T> key);

  /**
   * Performs an search with "AND" all non-null non-empty parameters.
   * 
   * @param mediaNumber
   * @param title
   * @param author
   * @param publicationYear
   * @param selectedMediaKind
   * @param genreSet
   * @return
   */
  List<MediaItem> search(String mediaNumber,
                         String title,
                         Author author,
                         Date publicationYear,
                         MediaKind selectedMediaKind,
                         Set<String> genreSet);

  void setUp();
}

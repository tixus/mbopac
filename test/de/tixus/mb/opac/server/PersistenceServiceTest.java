/**
 * 
 */
package de.tixus.mb.opac.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import de.tixus.mb.opac.client.PersistenceService;
import de.tixus.mb.opac.factory.ObjectifyTestFactory;
import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.Lending;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;
import de.tixus.mb.opac.shared.entities.Person;

/**
 * @author TSP
 * 
 */
public class PersistenceServiceTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
  private final ObjectifyTestFactory testFactory = new ObjectifyTestFactory();

  // CUD
  private final PersistenceService persistenceService = new PersistenceServiceImpl();

  /**
   * Test method for
   * {@link de.tixus.mb.opac.server.PersistenceServiceImpl#createMediaItem(java.lang.String, java.lang.String, java.lang.String, de.tixus.mb.opac.client.entities.Author, java.util.Date, de.tixus.mb.opac.client.entities.MediaKind, java.util.Set)}
   * .
   */
  @Test
  public void testCreateMediaItem() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link de.tixus.mb.opac.server.PersistenceServiceImpl#listAllMediaItems()}.
   */
  @Test
  public void testListAllMediaItems() {
    final List<MediaItem> allMediaItems = persistenceService.listAllMediaItems();
    Assert.assertFalse(allMediaItems.isEmpty());
    final List<MediaItem> allMediaItems2 = persistenceService.listAllMediaItems();
    Assert.assertFalse(allMediaItems2.isEmpty());
    final List<MediaItem> allMediaItems3 = persistenceService.listAllMediaItems();
    Assert.assertFalse(allMediaItems3.isEmpty());
  }

  @Test
  public void testListLendingsByPerson() {
    final List<MediaItem> allMediaItems = persistenceService.listAllMediaItems();
    final List<Person> listAllPersons = persistenceService.listAllPersons();
    int i = 0;
    for (final Person person : listAllPersons) {
      final MediaItem mediaItem = allMediaItems.get(i++);
      final Lending lending = testFactory.createLending(person, mediaItem);
      mediaItem.lendTo(person);
      persistenceService.update(mediaItem);
      System.out.println("Lent: " + mediaItem + " to " + person);
    }

    //    for (final Person person : listAllPersons) {
    //    persistenceService.listAllLendings();
    //    Assert.assertFalse(allMediaItems.isEmpty());
  }

  /**
   * Test method for
   * {@link de.tixus.mb.opac.server.PersistenceServiceImpl#search(String, String, de.tixus.mb.opac.shared.entities.Author, java.util.Date, de.tixus.mb.opac.shared.entities.MediaKind, java.util.Set)}
   * .
   */
  @Test
  public void testSearch() {
    String mediaNumber = null;
    final String title = null;
    final Author author = null;
    final Date publicationYear = null;
    final MediaKind selectedMediaKind = null;
    final Set<String> genreSet = null;
    // null test
    final List<MediaItem> emptySearchResult = persistenceService.search(mediaNumber, title, author, publicationYear, selectedMediaKind,
                                                                        genreSet);

    assertTrue(emptySearchResult.isEmpty());

    mediaNumber = "M123456789";
    final List<MediaItem> singleSearchResult = persistenceService.search(mediaNumber, title, author, publicationYear, selectedMediaKind,
                                                                         genreSet);
    assertFalse(singleSearchResult.isEmpty());
    assertEquals(1, singleSearchResult.size());
    assertEquals(mediaNumber, singleSearchResult.get(0).getMediaNumber());

  }

  /**
   * Test method for
   * {@link de.tixus.mb.opac.server.PersistenceServiceImpl#listByProperty(java.lang.Class, java.lang.String, java.lang.Object)}
   * .
   */
  @Test
  public void testListByProperty() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link de.tixus.mb.opac.server.PersistenceServiceImpl#update(java.io.Serializable)}.
   */
  @Test
  public void testUpdate() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link de.tixus.mb.opac.server.PersistenceServiceImpl#delete(java.io.Serializable)}.
   */
  @Test
  public void testDelete() {
    fail("Not yet implemented");
  }

  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }
}

package de.tixus.mb.opac.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;

import de.tixus.mb.opac.factory.ObjectifyTestFactory;
import de.tixus.mb.opac.shared.entities.MediaItem;

public class ObjectifyDaoTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
  private final ObjectifyTestFactory testFactory = new ObjectifyTestFactory();

  // CUD
  ObjectifyDao objectifyDao = new ObjectifyDao();

  @Before
  public void setUp() throws Exception {
    helper.setUp();
  }

  @After
  public void tearDown() throws Exception {
    helper.tearDown();
  }

  @Test
  public void testCreate() {
    fail("Not yet implemented");
  }

  @Test
  public void testUpdate() {
    fail("Not yet implemented");
  }

  @Test
  public void testDelete() {
    fail("Not yet implemented");
  }

  @Test
  public void testGet() {
    fail("Not yet implemented");
  }

  @Test
  public void testContains() {
    final String mediaNumber = "M1234567890";
    final MediaItem createdMediaItemOne = testFactory.createMediaItem("Tino auf Reisen", mediaNumber);
    final Key<MediaItem> key = new Key<MediaItem>(MediaItem.class, createdMediaItemOne.getId());

    assertFalse(objectifyDao.contains(key));
    objectifyDao.create(createdMediaItemOne);
    assertTrue(objectifyDao.contains(key));
  }

  @Test
  public void testListAll() {
    fail("Not yet implemented");
  }

  @Test
  public void testFindClassOfTStringObject() {
    fail("Not yet implemented");
  }

  @Test
  public void testFindUnique() {
    fail("Not yet implemented");
  }

  @Test
  public void testListByProperty() {
    fail("Not yet implemented");
  }

  @Test
  public void testFindClassOfMediaItemMap() {
    fail("Not yet implemented");
  }

}

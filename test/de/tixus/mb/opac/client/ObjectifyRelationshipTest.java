package de.tixus.mb.opac.client;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import de.tixus.mb.opac.factory.ObjectifyTestFactory;
import de.tixus.mb.opac.shared.entities.Lending;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.Person;

public class ObjectifyRelationshipTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
  private ObjectifyFactory objectifyFactory;
  private ObjectifyTestFactory testFactory;

  @Before
  public void setUp() {
    helper.setUp();

    objectifyFactory = ObjectifyService.factory();
    objectifyFactory.register(MediaItem.class);
    objectifyFactory.register(Person.class);
    objectifyFactory.register(Lending.class);
    testFactory = new ObjectifyTestFactory();
  }

  @After
  public void tearDown() {
    objectifyFactory = null;
    testFactory = null;

    helper.tearDown();
  }

  @Test
  public void createLendingRelationship() {
    final Objectify objectify = objectifyFactory.begin();
    final Person rainerErnst = testFactory.createMaleCustomer("Rainer", "Ernst");
    final MediaItem mediaItem = testFactory.createMediaItem("Tyrannen müssen nicht sein", "M123456789");

    // TODO provide generic entity.getKey()
    final Lending lending = testFactory.createLending(rainerErnst, mediaItem);
    mediaItem.lendTo(rainerErnst);

    objectify.put(rainerErnst);
    objectify.put(lending);
    objectify.put(mediaItem);

    assertEquals(lending, objectify.query(Lending.class).filter("person", rainerErnst).list().get(0));
    assertEquals(mediaItem, objectify.query(MediaItem.class).filter("lentTo", rainerErnst).list().get(0));
  }
}

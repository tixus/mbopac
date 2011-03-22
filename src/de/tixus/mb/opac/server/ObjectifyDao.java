/**
 * 
 */
package de.tixus.mb.opac.server;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;

import de.tixus.mb.opac.shared.entities.Lending;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.Person;

/**
 * Persistent layer DAO, abstracting from specific implementation
 * 
 * @author TSP
 * 
 */
public class ObjectifyDao {

  static {
    ObjectifyService.register(MediaItem.class);
    ObjectifyService.register(Person.class);
    ObjectifyService.register(Lending.class);
  }

  private final DAOBase daoBase;

  public ObjectifyDao() {
    final ObjectifyOpts opts = new ObjectifyOpts();
    // ensure same object for entity
    opts.setSessionCache(true);
    daoBase = new DAOBase(opts);
  }

  public <T> Key<T> create(final T object) {
    // TODO check EntityExistsException 
    final Key<T> key = daoBase.ofy().put(object);

    return key;
  }

  public <T> void update(final T... object) {
    // TODO check throws EntityNotFoundException 
    daoBase.ofy().put(object);
  }

  public <T> void delete(final T... object) {
    // TODO check throws EntityNotFoundException 
    daoBase.ofy().delete(object);
  }

  public <T> void delete(final List<T> objects) {
    // TODO check throws EntityNotFoundException 
    daoBase.ofy().delete(objects);
  }

  public <T> T get(final Key<T> key) {
    // TODO throws EntityNotFoundException 
    return daoBase.ofy().get(key);
  }

  public <T> boolean contains(final Key<T> key) {
    try {
      final T t = daoBase.ofy().get(key);
      return true;
    } catch (final NotFoundException e) {
      return false;
    }
  }

  public <T> List<T> listAll(final Class<T> clazz) {
    final Query<T> query = daoBase.ofy().query(clazz);
    final List<T> list = query.list();
    log(list.toArray());

    return list;
  }

  private void log(final Object... objs) {
    // log nothing
    //    for (final Object object : objs) {
    //      System.out.println(object.getClass() + "=" + object.toString() + "@" + Integer.toHexString(object.hashCode()));
    //    }
  }

  public <T> List<T> find(final Class<T> clazz, final String expression, final Object value) {
    final Query<T> query = daoBase.ofy().query(clazz);
    final Query<T> filteredQuery = query.filter(expression, value);
    final List<T> list = filteredQuery.list();
    log(list.toArray());

    return list;
  }

  public <T> T findUnique(final Class<T> clazz, final String expression, final Object value) {
    final Query<T> query = daoBase.ofy().query(clazz);
    final Query<T> filteredQuery = query.filter(expression, value);

    final T t = filteredQuery.get();
    log(t);

    return t;
  }

  public <T> List<T> listByProperty(final Class<T> clazz, final String propName, final Object propValue) {
    final Query<T> query = daoBase.ofy().query(clazz);
    query.filter(propName, propValue);
    final List<T> list = query.list();
    log(list.toArray());

    return list;
  }

  public List<MediaItem> find(final Class<MediaItem> clazz, final Map<String, Object> filterMap) {
    final Query<MediaItem> query = daoBase.ofy().query(clazz);

    final Set<Entry<String, Object>> entrySet = filterMap.entrySet();
    for (final Iterator<Entry<String, Object>> iterator = entrySet.iterator(); iterator.hasNext();) {
      final Map.Entry<String, Object> entry = iterator.next();

      final Object value = entry.getValue();
      if (value == null || "".equals(value)) {
        continue;
      }

      query.filter(entry.getKey(), entry.getValue());
    }

    final List<MediaItem> list = query.list();

    return list;
  }
}

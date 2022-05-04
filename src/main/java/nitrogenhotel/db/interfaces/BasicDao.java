package nitrogenhotel.db.interfaces;

import java.util.List;

/**
 * A generic data access object interface that defines the basic operations for a table.
 *
 * @param <T> Any data access object entry class.
 */
public interface BasicDao<T> {

  List<T> getAll() throws Exception;

  T get(T obj) throws Exception;

  void delete(T obj) throws Exception;

  void add(T obj) throws Exception;
}

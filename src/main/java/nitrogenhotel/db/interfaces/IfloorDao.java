package nitrogenhotel.db.interfaces;

import java.util.List;
import nitrogenhotel.db.entries.Floor;

/**
 * Used to define floor methods.
 */
public interface IfloorDao {

  List<Floor> getAll() throws Exception;

  void removeFloor(Floor number) throws Exception;

  void addFloor(Floor number) throws Exception;

}

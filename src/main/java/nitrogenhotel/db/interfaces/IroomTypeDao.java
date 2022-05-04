package nitrogenhotel.db.interfaces;

import java.util.List;
import nitrogenhotel.db.entries.RoomType;

/** Defines the methods that are specific to a roomType. */
public interface IroomTypeDao {
  List<RoomType> getAll() throws Exception;

  void removeRoomType(String type) throws Exception;

  void addRoomType(String type) throws Exception;

}

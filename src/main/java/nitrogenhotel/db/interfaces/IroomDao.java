package nitrogenhotel.db.interfaces;

import java.util.List;
import nitrogenhotel.db.entries.Room;


/**
 * Defines the methods that are specific to a room.
 */
public interface IroomDao {

  void updateNbBeds(Room room, int nbBeds) throws Exception;

  void updateNote(Room room, String note) throws Exception;

  void updateSize(Room room, String size) throws Exception;

  void updateFloor(Room room, int floor) throws Exception;

  void updateRoomNumber(Room room, int number) throws Exception;

  List<Room> getRoomsBySize(Room obj) throws Exception;
}

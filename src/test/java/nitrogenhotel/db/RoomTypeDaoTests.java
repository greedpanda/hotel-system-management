package nitrogenhotel.db;

import nitrogenhotel.db.dataaccessobjects.RoomTypeDao;
import nitrogenhotel.db.dataaccessobjects.RoomDao;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.room.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTypeDaoTests extends dbTest {

  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllRoomTypesException {
      RoomTypeDao roomTypeDao = new RoomTypeDao();
      List<RoomType> allRoomTypes = roomTypeDao.getAll();

      assertFalse(allRoomTypes.isEmpty());

    }

    @Test
    public void getAll_ShouldReturnListContainingTheRoomsTypesInTheDB() throws CouldNotFetchAllRoomTypesException {
      RoomTypeDao roomTypeDao = new RoomTypeDao();
      List<RoomType> allRoomTypes = roomTypeDao.getAll();

      assertTrue(
              allRoomTypes.stream()
                      .anyMatch(
                              roomType ->
                                      roomType == RoomType.DOUBLE));
      assertTrue(
              allRoomTypes.stream()
                      .anyMatch(
                              roomType ->
                                      roomType == RoomType.SINGLE));
    }
  }

  @Nested
  public class addRoomTypeMethodTests {

    @Test
    public void addRoomType_StringAsParameter_ShouldReturnSpecifiedRoomTypeFromDbAfterAddingIt()
            throws InvalidRoomTypeException, CouldNotAddRoomTypeException, CouldNotAddRoomException,
            InvalidRoomInputException, CouldNotFetchRoomException,
            CouldNotDeleteRoomException, CouldNotRemoveRoomTypeException {

      RoomTypeDao roomTypeDao = new RoomTypeDao();
      RoomDao roomDao = new RoomDao();
      roomTypeDao.addRoomType("King");
      Room room = new Room(9999, "King", 8, 2);
      roomDao.add(room);

      Room room1 = roomDao.get(new Room(9999));

      assertEquals(room1.getSize(), "KING");

      roomDao.delete(room);
      roomTypeDao.removeRoomType("King");
    }

    @Test
    public void addRoomType_NullAsParameter_ShouldThrowInvalidRoomTypeException() {
      RoomTypeDao roomTypeDao = new RoomTypeDao();
      String roomType = null;

      assertThrows(InvalidRoomTypeException.class, () -> roomTypeDao.addRoomType(roomType));
    }

    @Test
    public void addRoomType_ExistingRoomTypeInDbAsParameter_ShouldThrowInvalidRoomTypeException() {
      RoomTypeDao roomTypeDao = new RoomTypeDao();
      String roomType = "Double";

      assertThrows(CouldNotAddRoomTypeException.class, () -> roomTypeDao.addRoomType(roomType));
    }
  }

  @Nested
  public class removeRoomTypeMethodTests {

    @Test
    public void removeRoomType_StringAsParameter_ShouldThrowCouldNotAddRoomExceptionWhenAddingRoomWithDeletedRoomType()
            throws InvalidRoomTypeException, CouldNotAddRoomTypeException, CouldNotRemoveRoomTypeException {

      RoomTypeDao roomTypeDao = new RoomTypeDao();
      RoomDao roomDao = new RoomDao();

      roomTypeDao.addRoomType("KING");
      roomTypeDao.removeRoomType("KING");

      Room room = new Room(6966, "KING", 2, 1);

      assertThrows(CouldNotAddRoomException.class, () -> roomDao.add(room));

    }

    @Test
    public void removeRoomType_NullAsParameter_ShouldThrowInvalidRoomTypeException() {
      RoomTypeDao roomTypeDao = new RoomTypeDao();
      String roomType = null;

      assertThrows(InvalidRoomTypeException.class, () -> roomTypeDao.removeRoomType(roomType));
    }

    @Test
    public void removeRoomType_NoneExistingRoomTypeInDbAsParameter_ShouldNotThrowException() {
      RoomTypeDao roomTypeDao = new RoomTypeDao();
      String roomType = "BANG";

      assertDoesNotThrow(() -> roomTypeDao.removeRoomType(roomType));
    }
  }
}
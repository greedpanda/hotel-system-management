package nitrogenhotel.db;

import java.util.List;
import nitrogenhotel.db.dataaccessobjects.RoomDao;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.exceptions.room.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomDaoTests extends dbTest {

  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllRoomsException {
      RoomDao roomDao = new RoomDao();
      List<Room> allRooms = roomDao.getAll();

      assertFalse(allRooms.isEmpty());
    }

    @Test
    public void getAll_ShouldReturnListContainingTheRoomsInTheDB() throws CouldNotFetchAllRoomsException {
      RoomDao roomDao = new RoomDao();
      List<Room> allRooms = roomDao.getAll();

      assertTrue(
              allRooms.stream()
                      .anyMatch(
                              room ->
                                      room.getFloor() == 1
                                              && room.getNumber() == 100
                                              && room.getNote().equals("nice designed room")
                                              && room.getSize().equals("DOUBLE")
                                              && room.getNbBeds() == 2));
      assertTrue(
              allRooms.stream()
                      .anyMatch(
                              room ->
                                      room.getFloor() == 1
                                              && room.getNumber() == 101
                                              && room.getNote() == null
                                              && room.getSize().equals("SINGLE")
                                              && room.getNbBeds() == 1));
    }
  }

  @Nested
  public class getMethodTests {

    @Test
    public void get_RoomAsParameter_ShouldReturnSpecifiedRoomFromDB()
            throws CouldNotFetchRoomException, InvalidRoomInputException {

      RoomDao roomDao = new RoomDao();
      Room room1 = new Room(100);

      Room room = roomDao.get(room1);

      assertAll(() -> assertEquals(room.getFloor(), 1),
                () -> assertEquals(room.getNumber(), 100),
                () -> assertEquals(room.getNote(), "nice designed room"),
                () -> assertEquals(room.getSize(), "DOUBLE"),
                () -> assertEquals(room.getNbBeds(), 2));
    }

    @Test
    public void get_NullAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = null;

      assertThrows(InvalidRoomInputException.class, () -> roomDao.get(room));
    }

    @Test
    public void get_RoomWithRoomNumberZeroAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(0);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.get(room));
    }

    @Test
    public void get_RoomWithNegativeIntegerRoomNumberAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(-1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.get(room));
    }

    @Test
    public void get_NoneExistingRoomInDbAsParameter_ShouldThrowCouldNotFetchRoomException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(111111);

      assertThrows(CouldNotFetchRoomException.class, () -> roomDao.get(room));
    }
  }

  @Nested
  public class deleteMethodTests {

    @Test
    public void delete_RoomAsParameter_ShouldThrowCouldNotFetchRoomExceptionWhenFetchingDeletedRoom()
            throws InvalidRoomInputException, CouldNotDeleteRoomException, CouldNotAddRoomException {

      RoomDao roomDao = new RoomDao();
      Room room = new Room(666, "Double", 2, 1);
      roomDao.add(room);
      roomDao.delete(room);

      assertThrows(CouldNotFetchRoomException.class, () -> roomDao.get(new Room(666)));
    }

    @Test
    public void delete_NullAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = null;

      assertThrows(InvalidRoomInputException.class, () -> roomDao.delete(room));
    }

    @Test
    public void delete_RoomWithRoomNumberZeroAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(0, "Double", 2, 1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.delete(room));
    }

    @Test
    public void delete_RoomWithNegativeIntegerRoomNumberAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(-1, "Double", 2, 1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.delete(room));
    }

    @Test
    public void delete_NoneExistingRoomInDbAsParameter_ShouldNotThrowException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(999999, "Double", 2, 1);

      assertDoesNotThrow(() -> roomDao.delete(room));
    }

  }

  @Nested
  public class addMethodTests {

    @Test
    public void add_RoomAsParameter_ShouldReturnSpecifiedRoomFromDbAfterAddingIt()
            throws CouldNotAddRoomException, InvalidRoomInputException,
            CouldNotFetchRoomException, CouldNotDeleteRoomException {

      RoomDao roomDao = new RoomDao();
      Room room = new Room(666, "Double", 2, 1);
      roomDao.add(room);

      Room room1 = roomDao.get(new Room(666));

      assertAll(() -> assertEquals(666, room1.getNumber()),
                () -> assertEquals("DOUBLE", room1.getSize()),
                () -> assertEquals(2, room1.getNbBeds()),
                () -> assertEquals(1, room1.getFloor()));

      roomDao.delete(room);
    }

    @Test
    public void add_NullAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = null;

      assertThrows(InvalidRoomInputException.class, () -> roomDao.add(room));
    }

    @Test
    public void add_RoomWithRoomNumberZeroAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(0, "Double", 2, 1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.add(room));
    }

    @Test
    public void add_RoomWithNegativeIntegerRoomNumberAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(-1, "Double", 2, 1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.add(room));
    }

    @Test
    public void add_RoomWithNullSizeAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(6966, null, 2, 1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.add(room));
    }

    @Test
    public void add_RoomWithIncorrectSizeAsParameter_ShouldThrowCouldNotAddRoomException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(6966, "small", 2, 1);

      assertThrows(CouldNotAddRoomException.class, () -> roomDao.add(room));
    }

    @Test
    public void add_RoomWithNegativeIntegerNbBedsAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(6966, "Double", -1, 1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.add(room));
    }

    @Test
    public void add_RoomWithFloorZeroAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(6966, "Double", 1, 0);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.add(room));
    }


    @Test
    public void add_RoomWithNegativeIntegerFloorAsParameter_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(6966, "Double", 1, -1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.add(room));
    }

    @Test
    public void add_RoomWithNoneExistingFloorInDbAsParameter_ShouldThrowCouldNotAddRoomException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(6966, "Double", 1, 999);

      assertThrows(CouldNotAddRoomException.class, () -> roomDao.add(room));
    }

    @Test
    public void add_ExistingRoomInDbAsParameter_ShouldThrowCouldNotAddRoomException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(100, "Double", 1, 1);

      assertThrows(CouldNotAddRoomException.class, () -> roomDao.add(room));
    }
  }

  @Nested
  public class updateNbBedsMethodTests {

    @Test
    public void updateNbBeds_RoomAs1ParameterPositiveIntegerAs2_ShouldChangeTheNbBedsInSpecifiedRoom()
            throws CouldNotUpdateNbBedsException, InvalidRoomInputException, CouldNotFetchRoomException {

      RoomDao roomDao = new RoomDao();
      Room room = new Room(100);
      roomDao.updateNbBeds(room, 4);

      assertEquals(roomDao.get(room).getNbBeds(), 4);

      roomDao.updateNbBeds(room, 2);
    }

    @Test
    public void updateNbBeds_NullAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = null;

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateNbBeds(room, 2));
    }

    @Test
    public void updateNbBeds_RoomAs1ParameterZeroAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(100);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateNbBeds(room, 0));
    }

    @Test
    public void updateNbBeds_RoomAs1ParameterNegativeIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(100);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateNbBeds(room, -1));
    }


    @Test
    public void updateNbBeds_NegativeRoomNumberAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(-1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateNbBeds(room, 3));
    }

    @Test
    public void updateNbBeds_RoomNumberZeroAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(0);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateNbBeds(room, 3));
    }

    @Test
    public void updateNbBeds_NoneExistingRoomInDbAs1ParameterPositiveIntegerAs2_ShouldNotThrowException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(9999);

      assertDoesNotThrow(() -> roomDao.updateNbBeds(room, 3));
    }
  }

  @Nested
  public class updateNoteMethodTests {

  @Test
  public void updateNote_RoomAs1ParameterStringAs2_ShouldChangeTheNoteOfTheSpecifiedRoom()
          throws CouldNotUpdateNoteException, InvalidRoomInputException, CouldNotFetchRoomException {

    RoomDao roomDao = new RoomDao();
    Room room = new Room(201);
    roomDao.updateNote(room, "In renovation");

    assertEquals(roomDao.get(room).getNote(), "In renovation");

    roomDao.updateNote(room, "has a nice view");
  }

  @Test
  public void updateNote_NullAs1ParameterStringAs2_ShouldThrowInvalidRoomInputException() {
    RoomDao roomDao = new RoomDao();
    Room room = null;

    assertThrows(InvalidRoomInputException.class, () -> roomDao.updateNote(room, "In renovation."));
  }

  @Test
  public void  updateNote_RoomNumberZeroAs1ParameterStringAs2_ShouldThrowInvalidRoomInputException() {
    RoomDao roomDao = new RoomDao();
    Room room = new Room(0);

    assertThrows(InvalidRoomInputException.class, () -> roomDao.updateNote(room, "In renovation."));
  }

    @Test
    public void  updateNote_NegativeRoomNumberAs1ParameterStringAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(-1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateNote(room, "In renovation."));
    }

    @Test
    public void  updateNote_NoneExistingRoomInDbAs1ParameterStringAs2_ShouldNotThrowException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(99999);

      assertDoesNotThrow(() -> roomDao.updateNote(room, "In renovation."));
    }
}

  @Nested
  public class updateSizeMethodTests {

    @Test
    public void updateSize_RoomAs1ParameterStringAs2_ShouldChangeTheSizeOfTheSpecifiedRoom()
            throws CouldNotUpdateRoomSizeException, InvalidRoomInputException, CouldNotFetchRoomException {

      RoomDao roomDao = new RoomDao();
      Room room = new Room(101);
      roomDao.updateSize(room, "quad");
      Room room1 = roomDao.get(room);

      assertEquals("QUAD", room1.getSize());

      roomDao.updateSize(room, "single");
    }

    @Test
    public void updateSize_NullAs1ParameterStringAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = null;

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateSize(room, "single"));
    }

    @Test
    public void updateSize_RoomAs1ParameterNullAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(101);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateSize(room, null));
    }

    @Test
    public void updateSize_RoomAs1ParameterNoneExistingSizeAs2_ShouldThrowCouldNotUpdateRoomSizeException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(101);

      assertThrows(CouldNotUpdateRoomSizeException.class, () -> roomDao.updateSize(room, "Queen"));
    }

    @Test
    public void updateSize_RoomNumberZeroAs1ParameterStringAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(0);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateSize(room, "single"));
    }

    @Test
    public void updateSize_NegativeRoomNumberAs1ParameterStringAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(-1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateSize(room, "single"));
    }

    @Test
    public void updateSize_NoneExistingRoomInDbAs1ParameterStringAs2_ShouldNotThrowException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(99999);

      assertDoesNotThrow(() -> roomDao.updateNote(room, "In renovation."));
    }
  }

  @Nested
  public class updateFloorMethodTests {

    @Test
    public void updateFloor_RoomAs1ParameterPositiveIntegerAs2_ShouldChangeTheFloorOfTheSpecifiedRoom()
            throws InvalidRoomInputException, CouldNotFetchRoomException, CouldNotUpdateFloorNumberException {

      RoomDao roomDao = new RoomDao();
      Room room = new Room(101);
      roomDao.updateFloor(room, 3);
      Room room1 = roomDao.get(room);

      assertEquals(3, room1.getFloor());

      roomDao.updateFloor(room, 1);
    }

    @Test
    public void updateFloor_NullAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = null;

      assertThrows(InvalidRoomInputException.class, () -> roomDao. updateFloor(room, 2));
    }

    @Test
    public void updateFloor_RoomAs1ParameterZeroAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(100);

      assertThrows(InvalidRoomInputException.class, () -> roomDao. updateFloor(room, 0));
    }

    @Test
    public void updateFloor_RoomAs1ParameterNegativeIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(100);

      assertThrows(InvalidRoomInputException.class, () -> roomDao. updateFloor(room, -1));
    }


    @Test
    public void updateFloor_NegativeRoomNumberAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(-1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao. updateFloor(room, 3));
    }

    @Test
    public void updateFloor_RoomNumberZeroAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(0);

      assertThrows(InvalidRoomInputException.class, () -> roomDao. updateFloor(room, 3));
    }

    @Test
    public void updateFloor_NoneExistingRoomInDbAs1ParameterPositiveIntegerAs2_ShouldNotThrowException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(9999);

      assertDoesNotThrow(() -> roomDao. updateFloor(room, 3));
    }

    @Test
    public void updateFloor_DoesNotExist() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(101);

      assertThrows(CouldNotUpdateFloorNumberException.class, () -> roomDao.updateFloor(room, 9999999));
    }
  }

  @Nested
  public class updateRoomNumberMethodTests {

    @Test
    public void updateRoomNumber_RoomAs1ParameterPositiveIntegerAs2_ShouldChangeTheRoomNumberOfTheSpecifiedRoom()
            throws InvalidRoomInputException, CouldNotFetchRoomException,
            CouldNotUpdateRoomNumberException {

      RoomDao roomDao = new RoomDao();
      Room room = new Room(100);
      roomDao.updateRoomNumber(room, 3);
      Room room1 = roomDao.get(new Room(3));

      assertEquals(3, room1.getNumber());

      roomDao.updateRoomNumber(room1, 100);
    }

    @Test
    public void updateRoomNumber_NullAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = null;

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateRoomNumber(room, 2));
    }

    @Test
    public void updateRoomNumber_RoomAs1ParameterZeroAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(100);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateRoomNumber(room, 0));
    }

    @Test
    public void updateRoomNumber_RoomAs1ParameterNegativeIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(100);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateRoomNumber(room, -1));
    }

    @Test
    public void updateRoomNumber_NegativeRoomNumberAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(-1);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateRoomNumber(room, 3));
    }

    @Test
    public void updateRoomNumber_RoomNumberZeroAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidRoomInputException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(0);

      assertThrows(InvalidRoomInputException.class, () -> roomDao.updateRoomNumber(room, 3));
    }

    @Test
    public void updateRoomNumber_NoneExistingRoomInDbAs1ParameterPositiveIntegerAs2_ShouldNotThrowException() {
      RoomDao roomDao = new RoomDao();
      Room room = new Room(9999);

      assertDoesNotThrow(() -> roomDao.updateRoomNumber(room, 3));
    }

    @Test
    public void updateRoomNumber_RoomAs1ParameterExistingRoomNumberInDbAs2_ShouldNotThrowException(){
      RoomDao roomDao = new RoomDao();
      Room room = new Room(101);

      assertThrows(CouldNotUpdateRoomNumberException.class, () -> roomDao.updateRoomNumber(room, 100));
    }
  }

  @Test
  public void getRoomsBySizeTest() throws CouldNotFetchAllRoomsBySizeException {
    RoomDao roomDao = new RoomDao();
    List<Room> allRooms = roomDao.getRoomsBySize(new Room("SINGLE"));
    assertFalse(allRooms.isEmpty());
    assertFalse(
            allRooms.stream()
                    .anyMatch(
                            room ->
                                    room.getFloor() == 1
                                            && room.getNumber() == 100
                                            && room.getNote().equals("nice designed room")
                                            && room.getSize().equals("DOUBLE")
                                            && room.getNbBeds() == 2));
    assertTrue(
            allRooms.stream()
                    .anyMatch(
                            room ->
                                    room.getFloor() == 1
                                            && room.getNumber() == 101
                                            && room.getNote() == null
                                            && room.getSize().equals("SINGLE")
                                            && room.getNbBeds() == 1));
  }
}

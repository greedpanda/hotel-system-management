package nitrogenhotel.db;

import java.util.List;
import nitrogenhotel.db.dataaccessobjects.FloorDao;
import nitrogenhotel.db.dataaccessobjects.RoomDao;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.exceptions.room.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloorDaoTests extends dbTest {

  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllFloorException {
      FloorDao floorDao = new FloorDao();
      List<Floor> allFloors = floorDao.getAll();

      assertFalse(allFloors.isEmpty());
    }

    @Test
    public void getAll_ShouldReturnListContainingTheAdminsInDB() throws CouldNotFetchAllFloorException {
      FloorDao floorDao = new FloorDao();
      List<Floor> allFloors = floorDao.getAll();

      assertTrue(
              allFloors.stream()
                      .anyMatch(
                              floor ->
                                      floor.getFloorNumber() == 1));
      assertTrue(
              allFloors.stream()
                      .anyMatch(
                              floor ->
                                      floor.getFloorNumber() == 2));
    }
  }

  @Nested
  public class addFloorMethodTests {

    @Test
    public void  addFloor_FloorAsParameter_ShouldReturnSpecifiedFloorFromDbAfterAddingIt()
            throws InvalidFloorInputException, CouldNotAddFloorException,
            CouldNotAddRoomException, InvalidRoomInputException,
            CouldNotFetchRoomException, CouldNotRemoveFloorException, CouldNotDeleteRoomException {

      FloorDao floorDao = new FloorDao();
      RoomDao roomDao = new RoomDao();
      Floor floor = new Floor(99);
      Room room = new Room(6966, "Double", 2, 99);

      floorDao.addFloor(floor);
      roomDao.add(room);

      assertEquals(roomDao.get(new Room(6966)).getFloor(), 99);

      roomDao.delete(room);
      floorDao.removeFloor(floor);
    }

    @Test
    public void addFloor_FloorNumberZeroAsParameter_ShouldThrowInvalidFloorInputException() {
      FloorDao floorDao = new FloorDao();
      Floor floor = new Floor(0);

      assertThrows(InvalidFloorInputException.class, () -> floorDao.addFloor(floor));
    }

    @Test
    public void addFloor_FloorNumberNegativeIntegerAsParameter_ShouldThrowInvalidFloorInputException() {
      FloorDao floorDao = new FloorDao();
      Floor floor = new Floor(-1);

      assertThrows(InvalidFloorInputException.class, () -> floorDao.addFloor(floor));
    }

    @Test
    public void addFloor_ExistingFloorInDbAsParameter_ShouldThrowCouldNotAddFloorException() {
      FloorDao floorDao = new FloorDao();
      Floor floor = new Floor(1);

      assertThrows(CouldNotAddFloorException.class, () -> floorDao.addFloor(floor));
    }
  }

  @Nested
  public class removeFloor {

    @Test
    public void removeFloor_FloorAsParameter_ShouldThrowCouldNotAddRoomExceptionWhenAddingRoomWithDeletedFloor()
            throws InvalidFloorInputException, CouldNotAddFloorException,
            CouldNotRemoveFloorException {

      FloorDao floorDao = new FloorDao();
      RoomDao roomDao = new RoomDao();

      Floor floor = new Floor(99);
      floorDao.addFloor(floor);
      floorDao.removeFloor(floor);
      Room room = new Room(6966, "Double", 2, 99);

      assertThrows(CouldNotAddRoomException.class, () -> roomDao.add(room));
    }

    @Test
    public void removeFloor_FloorNumberZeroAsParameter_ShouldThrowInvalidFloorInputException() {
      FloorDao floorDao = new FloorDao();
      Floor floor = new Floor(0);

      assertThrows(InvalidFloorInputException.class, () -> floorDao.removeFloor(floor));
    }

    @Test
    public void removeFloor_FloorNumberNegativeIntegerAsParameter_ShouldThrowInvalidFloorInputException() {
      FloorDao floorDao = new FloorDao();
      Floor floor = new Floor(-1);

      assertThrows(InvalidFloorInputException.class, () -> floorDao.removeFloor(floor));
    }

    @Test
    public void removeFloor_NoneExistingFloorInDbAsParameter_ShouldNotThrowException() {
      FloorDao floorDao = new FloorDao();
      Floor floor = new Floor(999);

      assertDoesNotThrow(() -> floorDao.removeFloor(floor));
    }

  }
}

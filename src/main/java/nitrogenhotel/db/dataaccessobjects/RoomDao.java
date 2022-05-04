package nitrogenhotel.db.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.exceptions.room.CouldNotAddRoomException;
import nitrogenhotel.db.exceptions.room.CouldNotDeleteRoomException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllRoomsBySizeException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllRoomsException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchRoomException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateFloorNumberException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateNbBedsException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateNoteException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateRoomNumberException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateRoomSizeException;
import nitrogenhotel.db.exceptions.room.InvalidRoomInputException;
import nitrogenhotel.db.interfaces.BasicDao;
import nitrogenhotel.db.interfaces.IroomDao;

/**
 * This class is used to access data related to room or rooms in the database.
 */
public class RoomDao implements BasicDao<Room>, IroomDao {

  /**
   * Fetches all the rooms available in the database.
   *
   * @return It returns a list containing all the Room objects in the database.
   * @throws CouldNotFetchAllRoomsException for errors while fetching a list of users.
   */
  @Override
  public List<Room> getAll() throws CouldNotFetchAllRoomsException {
    Connection conn = DataBaseManager.getConnection();
    List<Room> allRooms = new ArrayList<Room>();
    Room room;

    String stringQuery = "SELECT * FROM RoomV;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(stringQuery)) {

      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        room =
            new Room(
                result.getInt("roomID"),
                result.getInt("number"),
                result.getString("size"),
                result.getInt("beds"),
                result.getInt("floor"),
                result.getString("note"));
        allRooms.add(room);
      }
      preparedStmnt.close();
      result.close();


    } catch (SQLException e) {
      throw new CouldNotFetchAllRoomsException(
          "There was an error while fetching the list of rooms available in the database.", e);
    }

    return allRooms;
  }

  /**
   * Returns a room from the database.
   *
   * @param obj the name of the Room object to be retrieved from the database.
   * @return It returns an Room object.
   * @throws InvalidRoomInputException  for room input error.
   * @throws CouldNotFetchRoomException for errors while fetching a specific room.
   */
  @Override
  public Room get(Room obj) throws InvalidRoomInputException, CouldNotFetchRoomException {

    if (obj == null || obj.getNumber() <= 0) {
      throw new InvalidRoomInputException(
          "The room must not be null and a room number must be provided");
    }

    Connection conn = DataBaseManager.getConnection();
    ResultSet result;
    Room room;

    String queryString = "SELECT * FROM RoomV WHERE number=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setInt(1, obj.getNumber());
      result = preparedStmnt.executeQuery();
      result.next();
      room =
          new Room(
              result.getInt("roomID"),
              result.getInt("number"),
              result.getString("size"),
              result.getInt("beds"),
              result.getInt("floor"),
              result.getString("note"));

      preparedStmnt.close();
      result.close();
    } catch (SQLException e) {
      throw new CouldNotFetchRoomException(
          "There was an error while fetching the room " + obj.getNumber(), e);
    }
    return room;
  }

  /**
   * Deletes a room from the database.
   *
   * @param obj the name of the Room object to be deleted from the database.
   * @throws InvalidRoomInputException   for room input error.
   * @throws CouldNotDeleteRoomException for errors during room deletion.
   */
  @Override
  public void delete(Room obj) throws InvalidRoomInputException, CouldNotDeleteRoomException {

    if (obj == null || obj.getNumber() <= 0) {
      throw new InvalidRoomInputException(
          "The room must not be null and a room number must be provided");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM Room WHERE number=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setInt(1, obj.getNumber());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotDeleteRoomException(
          "There was an error while deleting the room" + obj.getNumber(), e);
    }
  }

  /**
   * Adds a room to the database. This method supposes that the floor and the room type are already
   * added to the database. use addRoomType and addFloor to add new floors and room types.
   *
   * @param obj The room.
   * @throws InvalidRoomInputException for room input error.
   * @throws CouldNotAddRoomException  for errors during room addition.
   */
  @Override
  public void add(Room obj) throws InvalidRoomInputException, CouldNotAddRoomException {

    if (obj == null
        || obj.getSize() == null
        || obj.getNumber() <= 0
        || obj.getFloor() <= 0
        || obj.getNbBeds() <= 0) {
      throw new InvalidRoomInputException(
          "You must provide a room with all the relevant attributes");
    }

    int typeId = obj.getSize().toUpperCase(Locale.ROOT).hashCode();

    Connection conn = DataBaseManager.getConnection();
    String roomQuery = "INSERT INTO Room (number,note,typeID,floor,beds) VALUES(?,?,?,?,?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(roomQuery)) {
      preparedStmnt.setInt(1, obj.getNumber());
      preparedStmnt.setString(2, obj.getNote());
      preparedStmnt.setInt(3, typeId);
      preparedStmnt.setInt(4, obj.getFloor());
      preparedStmnt.setInt(5, obj.getNbBeds());
      preparedStmnt.executeUpdate();

    } catch (SQLException | NullPointerException e) {
      throw new CouldNotAddRoomException(
          "There was an error while adding the room " + obj.getNumber(), e);
    }
  }

  /**
   * Updates the number of beds of the specified room.
   *
   * @param room   the room to be updated.
   * @param nbBeds new number of beds.
   * @throws InvalidRoomInputException     thrown when an invalid input is given.
   * @throws CouldNotUpdateNbBedsException thrown when there is an error while updating number of
   *                                       beds.
   */
  @Override
  public void updateNbBeds(Room room, int nbBeds)
      throws InvalidRoomInputException, CouldNotUpdateNbBedsException {

    if (room == null || room.getNumber() <= 0 || nbBeds <= 0) {
      throw new InvalidRoomInputException(
          "The room cannot be null, and a room number with the number of beds must be provided.");
    }
    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Room SET beds = (?) WHERE number=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setInt(1, nbBeds);
      preparedStmnt.setInt(2, room.getNumber());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotUpdateNbBedsException(
          "There was an error while updating number of beds for the room:" + " " + room.getNumber(),
          e);
    }
  }

  /**
   * Updates the note of the specified room.
   *
   * @param room the room to be updated.
   * @param note the new note to be added.
   * @throws InvalidRoomInputException   thrown when an invalid input is given.
   * @throws CouldNotUpdateNoteException thrown when there is an error while updating a note.
   */
  @Override
  public void updateNote(Room room, String note)
      throws InvalidRoomInputException, CouldNotUpdateNoteException {

    if (room == null || room.getNumber() <= 0) {
      throw new InvalidRoomInputException(
          "The room cannot be null, and a room number must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Room SET note = (?) WHERE number=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, note);
      preparedStmnt.setInt(2, room.getNumber());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotUpdateNoteException(
          "There was an error while updating a note for the room:" + " " + room.getNumber(), e);
    }
  }

  /**
   * Updates the size of the room. The size to be updated must already exist in the database.
   *
   * @param room the room to be updated.
   * @param size the new size of the room.
   * @throws InvalidRoomInputException       thrown when an invalid input is given.
   * @throws CouldNotUpdateRoomSizeException thrown when there was an error while updating.
   */
  @Override
  public void updateSize(Room room, String size)
      throws CouldNotUpdateRoomSizeException, InvalidRoomInputException {

    if (room == null || room.getNumber() <= 0 || size == null) {
      throw new InvalidRoomInputException(
          "The room cannot be null, and a room number with size must be provided.");
    }

    int typeId = size.toUpperCase(Locale.ROOT).hashCode();

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Room SET typeID = (?) WHERE number=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setInt(1, typeId);
      preparedStmnt.setInt(2, room.getNumber());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotUpdateRoomSizeException(
          "There was an error while updating size of the room: " + room.getNumber(), e);
    }
  }

  /**
   * Updates the floor for the room specified. NOTE: The floor number provided must already exist in
   * the database.
   *
   * @param room  the specified room.
   * @param floor the floor number.
   * @throws InvalidRoomInputException          thrown when an invalid input is given.
   * @throws CouldNotUpdateFloorNumberException thrown when there was an error while updating.
   */
  @Override
  public void updateFloor(Room room, int floor)
      throws InvalidRoomInputException, CouldNotUpdateFloorNumberException {

    if (room == null || room.getNumber() <= 0 || floor <= 0) {
      throw new InvalidRoomInputException(
          "The room cannot be null, and a room number with the floor number must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Room SET floor = (?) WHERE number=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setInt(1, floor);
      preparedStmnt.setInt(2, room.getNumber());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotUpdateFloorNumberException(
          "There was an error while updating the floor number for the room: " + room.getNumber(),
          e);
    }
  }

  /**
   * Updates the room number for a specified room.
   *
   * @param room   the room to be updated.
   * @param number the new number of the room.
   * @throws InvalidRoomInputException         thrown when an invalid input is given.
   * @throws CouldNotUpdateRoomNumberException thrown when there was an error while updating.
   */
  @Override
  public void updateRoomNumber(Room room, int number)
      throws CouldNotUpdateRoomNumberException, InvalidRoomInputException {

    if (room == null || room.getNumber() <= 0 || number <= 0) {
      throw new InvalidRoomInputException(
          "The room cannot be null, and a room number with the floor number must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Room SET number = (?) WHERE number=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setInt(1, number);
      preparedStmnt.setInt(2, room.getNumber());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotUpdateRoomNumberException(
              "There was an error while updating the room number for the room: " + room.getNumber(), e);
    }
  }

  /**
   * Fetches all the rooms available in the database with the specified size.
   *
   * @return It returns a list containing all the Room objects in the database.
   * @throws CouldNotFetchAllRoomsBySizeException for errors while fetching a list of rooms with
   *                                              specified size.
   */
  @Override
  public List<Room> getRoomsBySize(Room obj) throws CouldNotFetchAllRoomsBySizeException {
    Connection conn = DataBaseManager.getConnection();
    List<Room> allRooms = new ArrayList<Room>();
    Room room;

    String stringQuery = "SELECT * FROM RoomV WHERE (typeId =(?));";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(stringQuery)) {
      int typeId = obj.getSize().toUpperCase(Locale.ROOT).hashCode();
      preparedStmnt.setInt(1, typeId);

      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        room =
                new Room(
                        result.getInt("roomID"),
                        result.getInt("number"),
                        result.getString("size"),
                        result.getInt("beds"),
                        result.getInt("floor"),
                        result.getString("note"));
        allRooms.add(room);
      }
      preparedStmnt.close();
      result.close();


    } catch (SQLException e) {
      throw new CouldNotFetchAllRoomsBySizeException(
              "There was an error while fetching the list of rooms with given size"
                      + "available in the database.", e);
    }
    return allRooms;
  }
}

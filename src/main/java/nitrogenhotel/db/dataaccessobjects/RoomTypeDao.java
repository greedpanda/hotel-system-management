package nitrogenhotel.db.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.room.CouldNotAddRoomTypeException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllRoomTypesException;
import nitrogenhotel.db.exceptions.room.CouldNotRemoveRoomTypeException;
import nitrogenhotel.db.exceptions.room.InvalidRoomTypeException;
import nitrogenhotel.db.interfaces.IroomTypeDao;


/**
 * This class is used to access data related to room types in the database.
 */
public class RoomTypeDao implements IroomTypeDao {

  /**
   * Fetches all the room types available in the database.
   *
   * @return It returns a list containing all the Size objects in the database.
   * @throws CouldNotFetchAllRoomTypesException for errors while fetching a list of room types.
   */
  @Override
  public List<RoomType> getAll() throws CouldNotFetchAllRoomTypesException {
    Connection conn = DataBaseManager.getConnection();
    List<RoomType> allRoomTypes = new ArrayList<RoomType>();
    RoomType roomType;

    String stringQuery = "SELECT * FROM RoomType;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(stringQuery)) {

      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        roomType = RoomType.fromSize(result.getString("size"));
        allRoomTypes.add(roomType);
      }
      preparedStmnt.close();
      result.close();


    } catch (SQLException e) {
      throw new CouldNotFetchAllRoomTypesException(
          "There was an error while fetching the list of room types available in the database.", e);
    }
    return allRoomTypes;
  }


  /**
   * Removes a room type from the database.
   *
   * @param type the room type.
   * @throws InvalidRoomTypeException        if the type given is invalid.
   * @throws CouldNotRemoveRoomTypeException thrown when an error happens.
   */
  @Override
  public void removeRoomType(String type)
          throws InvalidRoomTypeException, CouldNotRemoveRoomTypeException {

    Connection con = DataBaseManager.getConnection();

    if (type == null) {
      throw new InvalidRoomTypeException("Please provide a string for the type");
    }

    String queryString = "DELETE FROM RoomType WHERE typeID=(?);";

    try (PreparedStatement preparedStatement = con.prepareStatement(queryString)) {

      preparedStatement.setInt(1, type.toUpperCase(Locale.ROOT).hashCode());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotRemoveRoomTypeException(
            "There was a problem removing this room type " + type, e);
    }
  }

  /**
   * Adds a new room type to the database. Only call to add a type.
   *
   * @param type such as DOUBLE, TRIPLE etc.
   * @throws CouldNotAddRoomTypeException for errors during type addition.
   * @throws InvalidRoomTypeException     for type input error.
   */
  @Override
  public void addRoomType(String type)
          throws CouldNotAddRoomTypeException, InvalidRoomTypeException {
    Connection con = DataBaseManager.getConnection();

    if (type == null) {
      throw new InvalidRoomTypeException("Please provide a string for the type");
    }

    String queryString = "INSERT INTO RoomType (typeID, Size) VALUES (?,?);";

    try (PreparedStatement preparedStatement = con.prepareStatement(queryString)) {

      preparedStatement.setInt(1, type.toUpperCase(Locale.ROOT).hashCode());
      preparedStatement.setString(2, type.toUpperCase(Locale.ROOT));
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotAddRoomTypeException(
              "There was a problem adding this room type " + type, e);
    }
  }
}

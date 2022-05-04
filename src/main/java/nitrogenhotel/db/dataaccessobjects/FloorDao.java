package nitrogenhotel.db.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.exceptions.room.CouldNotAddFloorException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllFloorException;
import nitrogenhotel.db.exceptions.room.CouldNotRemoveFloorException;
import nitrogenhotel.db.exceptions.room.InvalidFloorInputException;
import nitrogenhotel.db.interfaces.IfloorDao;

/** Used to add, remove and get all floors. */
public class FloorDao implements IfloorDao {

  /**
   * Fetchs all the floors in the database.
   *
   * @return A list of floors.
   * @throws CouldNotFetchAllFloorException Thrown when an error occurs.
   */
  @Override
  public List<Floor> getAll() throws CouldNotFetchAllFloorException {
    Connection conn = DataBaseManager.getConnection();
    List<Floor> allFloors = new ArrayList<Floor>();
    Floor floor;

    String stringQuery = "SELECT * FROM Location;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(stringQuery)) {

      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        floor =
            new Floor(
                result.getInt("floor"));
        allFloors.add(floor);
      }
      preparedStmnt.close();
      result.close();

    } catch (SQLException e) {
      throw new CouldNotFetchAllFloorException(
          "There was an error while fetching the list of room types available in the database.", e);
    }
    return allFloors;
  }


  /**
   * Removes the floor from the database.
   *
   * @param obj the floor
   * @throws InvalidFloorInputException   when an invalid input is given.
   * @throws CouldNotRemoveFloorException when there was an error.
   */
  @Override
  public void removeFloor(Floor obj)
      throws CouldNotRemoveFloorException, InvalidFloorInputException {
    if (obj.getFloorNumber() <= 0) {
      throw new InvalidFloorInputException("Please provide correct floor number.");
    }

    Connection con = DataBaseManager.getConnection();
    String queryString = "DELETE FROM Location WHERE floor=(?);";

    try (PreparedStatement preparedStatement = con.prepareStatement(queryString)) {

      preparedStatement.setInt(1, obj.getFloorNumber());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotRemoveFloorException(
          "There was a problem removing this floor " + obj.getFloorNumber(), e);
    }
  }

  /**
   * Adds a new floor to the hotel database.
   *
   * @param obj The floor.
   * @throws InvalidFloorInputException for floor input error.
   * @throws CouldNotAddFloorException  for errors during floor addition.
   */
  @Override
  public void addFloor(Floor obj) throws CouldNotAddFloorException, InvalidFloorInputException {
    Connection con = DataBaseManager.getConnection();

    if (obj.getFloorNumber() <= 0) {
      throw new InvalidFloorInputException("Please provide correct floor number.");
    }
    String queryString = "INSERT INTO Location (floor) VALUES (?);";
    try (PreparedStatement preparedStatement = con.prepareStatement(queryString)) {

      preparedStatement.setInt(1, obj.getFloorNumber());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotAddFloorException(
          "There was a problem adding this floor " + obj.getFloorNumber(), e);
    }
  }
}

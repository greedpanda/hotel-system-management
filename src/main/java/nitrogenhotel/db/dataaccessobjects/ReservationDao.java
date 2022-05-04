package nitrogenhotel.db.dataaccessobjects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.entries.Reservation;
import nitrogenhotel.db.exceptions.reservation.CouldNotAddReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotDeleteReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotFetchAllReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotFetchReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateCustomerForReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateDateOfReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdatePaidForReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateRoomForReservationException;
import nitrogenhotel.db.exceptions.reservation.InvalidReservationInputException;
import nitrogenhotel.db.interfaces.BasicDao;
import nitrogenhotel.db.interfaces.IreservationDao;

/**
 * This class is used to access data related to reservation or reservations in the database.
 */
public class ReservationDao implements BasicDao<Reservation>, IreservationDao {

  /**
   * Fetches all the reservations available in the database.
   *
   * @return It returns a list containing all the Reservation objects in the database.
   * @throws CouldNotFetchAllReservationException for exceptions.
   */
  @Override
  public List<Reservation> getAll() throws CouldNotFetchAllReservationException {
    Connection conn = DataBaseManager.getConnection();
    List<Reservation> allReservations = new ArrayList<Reservation>();
    Reservation reservation;

    String queryString = "SELECT * FROM Reservation;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {

        reservation =
            new Reservation(
                result.getInt("reservationID"),
                result.getInt("customerID"),
                result.getInt("roomID"),
                Instant.ofEpochMilli(result.getDate("startDate").getTime()),
                Instant.ofEpochMilli(result.getDate("endDate").getTime()),
                result.getBoolean("paid"));
        allReservations.add(reservation);
      }
      result.close();
      return allReservations;
    } catch (SQLException e) {
      throw new CouldNotFetchAllReservationException(
          "There was an error while fetching the list of reservations available in the database.",
          e);
    }
  }

  /**
   * Returns a Reservation from the database.
   *
   * @param obj the name of the Reservation object to be retrieved from the database.
   * @return It returns a Reservation object.
   * @throws InvalidReservationInputException for input errors.
   * @throws CouldNotFetchReservationException   for errors occurring while fetching the object.
   */
  @Override
  public Reservation get(Reservation obj)
          throws InvalidReservationInputException, CouldNotFetchReservationException {

    if (obj == null || obj.getReservationID() <= 0) {
      throw new InvalidReservationInputException(
          "The reservation must not be null and a reservationID of the customer must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();
    Reservation reservation;

    String queryString = "SELECT * FROM Reservation WHERE reservationID=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setInt(1, obj.getReservationID());
      ResultSet result = preparedStmnt.executeQuery();
      result.next();
      reservation =
          new Reservation(
              result.getInt("reservationID"),
              result.getInt("customerID"),
              result.getInt("roomID"),
              Instant.ofEpochMilli(result.getDate("startDate").getTime()),
              Instant.ofEpochMilli(result.getDate("endDate").getTime()),
              result.getBoolean("paid"));

      result.close();
      return reservation;
    } catch (SQLException e) {
      throw new CouldNotFetchReservationException(
          "There was an error while fetching the reservation " + obj
              .getReservationID(), e);
    }
  }

  /**
   * Deletes a Reservation from the database.
   *
   * @param obj the reservation.
   * @throws InvalidReservationInputException   for input errors.
   * @throws CouldNotDeleteReservationException for errors occurring while deleting the object.
   */
  @Override
  public void delete(Reservation obj)
      throws InvalidReservationInputException, CouldNotDeleteReservationException {
    if (obj == null || obj.getReservationID() <= 0) {
      throw new InvalidReservationInputException(
          "The reservation must not be null and a reservationID must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM Reservation WHERE reservationID=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setInt(1, obj.getReservationID());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotDeleteReservationException(
          "There was an error while deleting the reservation "
              + "with ID" + " " + obj.getReservationID(), e);
    }
  }

  /**
   * Adds a reservation to the database.
   *
   * @param obj The reservation.
   * @throws InvalidReservationInputException for input errors.
   * @throws CouldNotAddReservationException  for errors occurring while adding the object.
   */
  @Override
  public void add(Reservation obj)
      throws InvalidReservationInputException, CouldNotAddReservationException {
    if (obj == null
        || obj.getCustomerID() <= 0
        || obj.getRoomID() <= 0
        || obj.getStartDate() == null
        || obj.getEndDate() == null
    ) {
      throw new InvalidReservationInputException(
          "The reservation must not be null and all attributes must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "INSERT INTO Reservation (customerID, roomID, startDate, endDate, paid) VALUES(?,?,?,?,?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setInt(1, obj.getCustomerID());
      preparedStmnt.setInt(2, obj.getRoomID());
      preparedStmnt.setDate(3, new Date(obj.getStartDate().toEpochMilli()));
      preparedStmnt.setDate(4, new Date(obj.getEndDate().toEpochMilli()));
      preparedStmnt.setBoolean(5, obj.getPaid());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotAddReservationException(
          "There was an error while adding the reservation" + " " + obj.getReservationID(), e);
    }
  }

  /**
   * Updates the roomID of the reservation.
   *
   * @param res    The reservation to be updated.
   * @param roomID The new room. Must already be in the database.
   * @throws InvalidReservationInputException          for invalid input.
   * @throws CouldNotUpdateRoomForReservationException for errors while updating.
   */
  @Override
  public void updateRoomID(Reservation res, int roomID)
      throws InvalidReservationInputException, CouldNotUpdateRoomForReservationException {
    if (res == null || res.getReservationID() <= 0 || roomID <= 0) {
      throw new InvalidReservationInputException(
          "The Reservation must not be null, a reservationID and a roomID must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Reservation SET roomID = (?) WHERE reservationID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setInt(1, roomID);
      preparedStmnt.setInt(2, res.getReservationID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdateRoomForReservationException(
          "There was an error while updating the room of the reservation: " + res
              .getReservationID(),
          e);
    }
  }

  /**
   * Updates the customerID of the reservation.
   *
   * @param res        The reservation to be updated.
   * @param customerID The new customer.
   * @throws CouldNotUpdateCustomerForReservationException For errors while updating.
   * @throws InvalidReservationInputException              When bad input is given.
   */
  @Override
  public void updateCustomerID(Reservation res, int customerID)
      throws InvalidReservationInputException, CouldNotUpdateCustomerForReservationException {

    if (res == null || res.getReservationID() <= 0 || customerID <= 0) {
      throw new InvalidReservationInputException(
          "The Reservation must not be null, a reservationID and a customerID must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Reservation SET customerID = (?) WHERE reservationID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setInt(1, customerID);
      preparedStmnt.setInt(2, res.getReservationID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdateCustomerForReservationException(
          "There was an error while updating the customer of the reservation: " + res
              .getReservationID(),
          e);
    }

  }

  /**
   * Updates the paid status of a reservation.
   *
   * @param res  The reservation to be updated.
   * @param paid The new boolean value.
   * @throws InvalidReservationInputException          for invalid input.
   * @throws CouldNotUpdatePaidForReservationException for errors while updating.
   */
  @Override
  public void updatePaid(Reservation res, boolean paid)
      throws InvalidReservationInputException, CouldNotUpdatePaidForReservationException {
    if (res == null || res.getReservationID() <= 0) {
      throw new InvalidReservationInputException(
          "The Reservation must not be null, a reservationID and a boolean must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Reservation SET paid = (?) WHERE reservationID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setBoolean(1, paid);
      preparedStmnt.setInt(2, res.getReservationID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdatePaidForReservationException(
          "There was an error while updating the paid status of the reservation: " + res
              .getReservationID(),
          e);
    }

  }

  /**
   * Updates the start date of the reservation.
   *
   * @param res       reservation to be updated.
   * @param startDate The new start date.
   * @throws CouldNotUpdateDateOfReservationException  When an error occurs while updating.
   * @throws InvalidReservationInputException When a bad input is given.
   */
  @Override
  public void updateStartDate(Reservation res, Instant startDate)
      throws InvalidReservationInputException, CouldNotUpdateDateOfReservationException {
    if (res == null || res.getReservationID() <= 0 || startDate == null) {
      throw new InvalidReservationInputException(
          "The Reservation must not be null, a reservationID and a valid instant must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Reservation SET startDate = (?) WHERE reservationID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setDate(1, new Date(startDate.toEpochMilli()));
      preparedStmnt.setInt(2, res.getReservationID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdateDateOfReservationException(
          "There was an error while updating the start date of the reservation: " + res
              .getReservationID(),
          e);
    }
  }

  /**
   * Updates the end date of a reservation.
   *
   * @param res     Reservation to be updated.
   * @param endDate The new end date.
   * @throws InvalidReservationInputException When bad input is given.
   * @throws CouldNotUpdateDateOfReservationException  When an error occurs while updating.
   */
  @Override
  public void updateEndDate(Reservation res, Instant endDate)
      throws InvalidReservationInputException, CouldNotUpdateDateOfReservationException {
    if (res == null || res.getReservationID() <= 0 || endDate == null) {
      throw new InvalidReservationInputException(
          "The Reservation must not be null, a reservationID and a valid instant must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Reservation SET endDate = (?) WHERE reservationID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setDate(1, new Date(endDate.toEpochMilli()));
      preparedStmnt.setInt(2, res.getReservationID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdateDateOfReservationException(
          "There was an error while updating the end date of the reservation: " + res
              .getReservationID(),
          e);
    }
  }
}

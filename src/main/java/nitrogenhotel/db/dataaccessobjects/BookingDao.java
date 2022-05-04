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
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.entries.Reservation;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.exceptions.booking.CouldNotAddBookingException;
import nitrogenhotel.db.exceptions.booking.CouldNotDeleteBookingException;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllActiveBookingByRoomException;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllActiveBookingException;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllBookingException;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchBookingException;
import nitrogenhotel.db.exceptions.booking.InvalidBookingInputException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateCustomerForReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateDateOfReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdatePaidForReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateRoomForReservationException;
import nitrogenhotel.db.exceptions.reservation.InvalidReservationInputException;
import nitrogenhotel.db.interfaces.BasicDao;
import nitrogenhotel.db.interfaces.IbookingDao;


/**
 * This class is used to access data related to booking or bookings in the database.
 */
public class BookingDao implements BasicDao<Booking>, IbookingDao {

  /**
   * Fetches all the bookings available in the database.
   *
   * @return It returns a list containing all the Booking objects in the database.
   * @throws  CouldNotFetchAllBookingException thrown for errors while fetching a list of bookings.
   */
  @Override
  public List<Booking> getAll() throws CouldNotFetchAllBookingException {
    Connection conn = DataBaseManager.getConnection();
    List<Booking> allBookings = new ArrayList<Booking>();
    Booking booking;

    String stringQuery = "SELECT * FROM BookingV;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(stringQuery)) {
      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        booking =
                new Booking(new Reservation(result.getInt("reservationID"),
                                            result.getInt("customerID"),
                                            result.getInt("roomID"),
                                            Instant.ofEpochMilli(result.getDate("startDate").getTime()),
                                            Instant.ofEpochMilli(result.getDate("endDate").getTime()),
                                            result.getBoolean("paid")),

                            new Room(result.getInt("roomID"),
                                     result.getInt("number"),
                                     result.getString("size"),
                                     result.getInt("beds"),
                                     result.getInt("floor"),
                                     result.getString("note")),

                            new Customer(result.getInt("customerID"),
                                         result.getString("publicID"),
                                         result.getString("customerName"),
                                         result.getString("address"),
                                         result.getString("paymentMethod")));

        allBookings.add(booking);
      }
      preparedStmnt.close();
      result.close();

    } catch (SQLException e) {
      throw new CouldNotFetchAllBookingException(
            "There was an error while fetching the list of bookings available in the database. ", e);
    }
    return allBookings;
  }


  /**
   * Returns a Booking from the database.
   *
   * @param obj the name of the Booking object to be retrieved from the database.
   * @return It returns a Booking object.
   * @throws InvalidBookingInputException thrown when an invalid booking or a wrong reservationID is given.
   * @throws CouldNotFetchBookingException thrown when an error occurs while fetching the booking.
   */
  @Override
  public Booking get(Booking obj) throws InvalidBookingInputException, CouldNotFetchBookingException {

    if (obj == null || obj.getReservation().getReservationID() <= 0) {
      throw new InvalidBookingInputException(
              "The booking must not be null and a valid reservationID must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();
    Booking booking;

    String stringQuery = "SELECT * FROM BookingV WHERE reservationID=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(stringQuery)) {

      preparedStmnt.setInt(1, obj.getReservation().getReservationID());
      ResultSet result = preparedStmnt.executeQuery();
      result.next();
      booking =
         new Booking(new Reservation(result.getInt("reservationID"),
                      result.getInt("customerID"),
                      result.getInt("roomID"),
                      Instant.ofEpochMilli(result.getDate("startDate").getTime()),
                      Instant.ofEpochMilli(result.getDate("endDate").getTime()),
                      result.getBoolean("paid")),

              new Room(result.getInt("roomID"),
                      result.getInt("number"),
                      result.getString("size"),
                      result.getInt("beds"),
                      result.getInt("floor"),
                      result.getString("note")),

              new Customer(result.getInt("customerID"),
                      result.getString("publicID"),
                      result.getString("customerName"),
                      result.getString("address"),
                      result.getString("paymentMethod")));

      result.close();
      return booking;
    } catch (SQLException e) {
      throw new CouldNotFetchBookingException("There was an error while fetching the booking "
              + obj.getReservation().getReservationID(), e);
    }
  }

  /**
   * Deletes a booking from the database.
   *
   * @param obj the booking.
   * @throws InvalidBookingInputException   thrown when an invalid booking or a wrong reservationID is given.
   * @throws CouldNotDeleteBookingException thrown when there is an error while deleting a booking.
   */
  @Override
  public void delete(Booking obj) throws InvalidBookingInputException, CouldNotDeleteBookingException {

    if (obj == null || obj.getReservation().getReservationID() <= 0) {
      throw new InvalidBookingInputException(
              "The booking must not be null and a valid reservationID must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM Reservation WHERE reservationID=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setInt(1, obj.getReservation().getReservationID());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotDeleteBookingException(
              "There was an error while deleting the booking "
                      + obj.getReservation().getReservationID(), e);
    }
  }

  /**
   * Adds a booking to the database.
   *
   * @param obj The booking.
   * @throws InvalidBookingInputException thrown for invalid booking input.
   * @throws CouldNotAddBookingException  thrown when there is an error while adding a booking.
   */
  @Override
  public void add(Booking obj) throws InvalidBookingInputException, CouldNotAddBookingException {
    if (obj == null
            || obj.getReservation().getCustomerID() <= 0
            || obj.getReservation().getRoomID() <= 0
            || obj.getReservation().getStartDate() == null
            || obj.getReservation().getEndDate() == null
    ) {
      throw new InvalidBookingInputException(
              "The booking must not be null and all attributes must be provided.");
    }
    Connection conn = DataBaseManager.getConnection();

    String queryString = "INSERT INTO BookingV(customerID, roomID, startDate, endDate, paid) VALUES(?, ?, ?, ?, ?);";


    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setInt(1, obj.getReservation().getCustomerID());
      preparedStmnt.setInt(2, obj.getReservation().getRoomID());
      preparedStmnt.setDate(3, new Date(obj.getReservation().getStartDate().toEpochMilli()));
      preparedStmnt.setDate(4, new Date(obj.getReservation().getEndDate().toEpochMilli()));
      preparedStmnt.setBoolean(5, obj.getReservation().getPaid());
      preparedStmnt.executeUpdate();


    } catch (SQLException e) {
      throw new CouldNotAddBookingException(
        "There was an error while adding the booking for the room with ID "
                + obj.getReservation().getReservationID(), e);
    }
  }

  /**
   * Updates the roomID of the booking.
   *
   * @param obj    The booking to be updated.
   * @param roomID The new room. Must already be in the database.
   * @throws InvalidReservationInputException for invalid reservation input
   * @throws InvalidReservationInputException for invalid booking input.
   * @throws CouldNotUpdateRoomForReservationException for errors while updating the roomID.
   */
  @Override
  public void updateRoomID(Booking obj, int roomID)
      throws InvalidBookingInputException, CouldNotUpdateRoomForReservationException,
            InvalidReservationInputException {

    if (obj == null || obj.getReservation().getReservationID() <= 0 || roomID <= 0) {
      throw new InvalidBookingInputException(
              "The booking must not be null, a reservationID and a roomID must be provided.");
    }
    ReservationDao reservationDao = new ReservationDao();
    reservationDao.updateRoomID(obj.getReservation(), roomID);
  }

  /**
   * Updates the roomID of the booking.
   *
   * @param obj The booking to be updated.
   * @param customerID  the new customer. Must already be in the database.
   * @throws InvalidReservationInputException for invalid reservation input
   * @throws InvalidReservationInputException for invalid booking input.
   * @throws CouldNotUpdateCustomerForReservationException for errors while updating the roomID.
   */
  @Override
  public void updateCustomerID(Booking obj, int customerID)
          throws InvalidBookingInputException,
          CouldNotUpdateCustomerForReservationException, InvalidReservationInputException {

    if (obj == null || obj.getReservation().getReservationID() <= 0 || customerID <= 0) {
      throw new InvalidBookingInputException(
              "The booking must not be null, a reservationID and a customerID must be provided.");
    }

    ReservationDao reservationDao = new ReservationDao();
    reservationDao.updateCustomerID(obj.getReservation(), customerID);
  }

  @Override
  public void updatePaid(Booking obj, boolean paid)
          throws InvalidBookingInputException, CouldNotUpdatePaidForReservationException,
          InvalidReservationInputException {

    if (obj == null || obj.getReservation().getReservationID() <= 0) {
      throw new InvalidBookingInputException(
              "The booking must not be null, a reservationID and a boolean must be provided.");
    }
    ReservationDao reservationDao = new ReservationDao();
    reservationDao.updatePaid(obj.getReservation(), paid);
  }

  @Override
  public void updateStartDate(Booking obj, Instant startDate)
          throws InvalidBookingInputException, CouldNotUpdateDateOfReservationException,
          InvalidReservationInputException {

    if (obj == null || obj.getReservation().getReservationID() <= 0 || startDate == null) {
      throw new InvalidBookingInputException(
              "The booking must not be null, a reservationID and a startDate must be provided.");
    }

    ReservationDao reservationDao = new ReservationDao();
    reservationDao.updateStartDate(obj.getReservation(), Instant.ofEpochMilli(startDate.toEpochMilli()));
  }

  @Override
  public void updateEndDate(Booking obj, Instant endDate)
          throws InvalidBookingInputException, InvalidReservationInputException,
          CouldNotUpdateDateOfReservationException {

    if (obj == null || obj.getReservation().getReservationID() <= 0 || endDate == null) {
      throw new InvalidBookingInputException(
              "The booking must not be null, a reservationID and an endDate must be provided.");
    }

    ReservationDao reservationDao = new ReservationDao();
    reservationDao.updateEndDate(obj.getReservation(), Instant.ofEpochMilli(endDate.toEpochMilli()));
  }

  /**
   * Fetches all the current bookings available in the database.
   *
   * @return It returns a list containing all the Booking objects in the database.
   * @throws  CouldNotFetchAllActiveBookingException thrown for errors
   *                                                 while fetching a list of current bookings.
   */
  @Override
  public List<Booking> getActiveBookings() throws CouldNotFetchAllActiveBookingException {
    Connection conn = DataBaseManager.getConnection();
    List<Booking> allBookings = new ArrayList<Booking>();
    Booking booking;

    String stringQuery = "SELECT * FROM BookingV WHERE (endDate >= CURDATE());";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(stringQuery)) {
      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        booking =
                new Booking(new Reservation(result.getInt("reservationID"),
                        result.getInt("customerID"),
                        result.getInt("roomID"),
                        Instant.ofEpochMilli(result.getDate("startDate").getTime()),
                        Instant.ofEpochMilli(result.getDate("endDate").getTime()),
                        result.getBoolean("paid")),

                        new Room(result.getInt("roomID"),
                                result.getInt("number"),
                                result.getString("size"),
                                result.getInt("beds"),
                                result.getInt("floor"),
                                result.getString("note")),

                        new Customer(result.getInt("customerID"),
                                result.getString("publicID"),
                                result.getString("customerName"),
                                result.getString("address"),
                                result.getString("paymentMethod")));

        allBookings.add(booking);
      }
      preparedStmnt.close();
      result.close();

    } catch (SQLException e) {
      throw new CouldNotFetchAllActiveBookingException(
              "There was an error while fetching the list of bookings available in the database. ", e);
    }
    return allBookings;
  }

  /**
   * Fetches all the current bookings sorted by room available in the database.
   *
   * @param room    The bookings to be fetched.
   * @return It returns a list containing all the Booking objects in the database.
   * @throws  CouldNotFetchAllActiveBookingByRoomException thrown for errors while fetching a
   *                                                       list of current bookings sorted by room.
   */
  @Override
  public List<Booking> getActiveBookingsByRoom(Room room) throws CouldNotFetchAllActiveBookingByRoomException {
    Connection conn = DataBaseManager.getConnection();
    List<Booking> allBookings = new ArrayList<Booking>();
    Booking booking;

    String stringQuery = "SELECT * FROM BookingV WHERE (number=(?) AND endDate >= CURDATE());";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(stringQuery)) {
      preparedStmnt.setInt(1, room.getNumber());
      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        booking =
                new Booking(new Reservation(result.getInt("reservationID"),
                        result.getInt("customerID"),
                        result.getInt("roomID"),
                        Instant.ofEpochMilli(result.getDate("startDate").getTime()),
                        Instant.ofEpochMilli(result.getDate("endDate").getTime()),
                        result.getBoolean("paid")),

                        new Room(result.getInt("roomID"),
                                result.getInt("number"),
                                result.getString("size"),
                                result.getInt("beds"),
                                result.getInt("floor"),
                                result.getString("note")),

                        new Customer(result.getInt("customerID"),
                                result.getString("publicID"),
                                result.getString("customerName"),
                                result.getString("address"),
                                result.getString("paymentMethod")));

        allBookings.add(booking);
      }
      preparedStmnt.close();
      result.close();

    } catch (SQLException e) {
      throw new CouldNotFetchAllActiveBookingByRoomException(
              "There was an error while fetching the list of bookings of a room available in the database. ", e);
    }
    return allBookings;
  }
}

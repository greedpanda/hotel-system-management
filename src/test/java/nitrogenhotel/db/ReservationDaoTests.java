package nitrogenhotel.db;

import java.time.Instant;
import java.util.List;

import nitrogenhotel.db.dataaccessobjects.ReservationDao;
import nitrogenhotel.db.entries.Reservation;
import nitrogenhotel.db.exceptions.reservation.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationDaoTests extends dbTest {

  @BeforeAll
  @DisplayName("We create and add a Reservation before all otherwise we get problems with the auto" +
          "incrementing primary key in the DB ")
  public static void add_ReservationAsParameter_AddsReservationToDB()
          throws CouldNotAddReservationException, InvalidReservationInputException {

    ReservationDao reservationDao = new ReservationDao();
    Reservation reservation = new Reservation(1, 1, Instant.ofEpochMilli(3535),
            Instant.ofEpochMilli(4585615), false);

    reservationDao.add(reservation);
  }

  @AfterAll
  public static void delete_ReservationAsParameter_DeletesReservationFromDB()
          throws CouldNotDeleteReservationException, InvalidReservationInputException {

    ReservationDao reservationDao = new ReservationDao();
    reservationDao.delete(new Reservation(4));
  }


  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllReservationException {
      ReservationDao reservationDao = new ReservationDao();
      List<Reservation> allReservations = reservationDao.getAll();

      assertFalse(allReservations.isEmpty());
    }

    @Test
    public void getAll_ShouldReturnAListContainingReservationsFromDB() throws CouldNotFetchAllReservationException {
      ReservationDao reservationDao = new ReservationDao();
      List<Reservation> allReservations = reservationDao.getAll();

      assertTrue(
              allReservations.stream()
                      .anyMatch(
                              reservation ->
                                      reservation.getCustomerID() == 1
                                              && reservation.getRoomID() == 1
                                              //&& reservation.getStartDate().equals(Instant.ofEpochMilli(1619733600000L))
                                              // && reservation.getEndDate().equals(Instant.ofEpochMilli(1619992800000L))
                                              && reservation.getPaid()));
      assertTrue(
              allReservations.stream()
                      .anyMatch(
                              reservation ->
                                      reservation.getCustomerID() == 2
                                              && reservation.getRoomID() == 2
                                              //&& reservation.getStartDate().equals(Instant.ofEpochMilli(1619992800000L))
                                              //&& reservation.getEndDate().equals(Instant.ofEpochMilli(1620597600000L))
                                              && !reservation.getPaid()));
    }
  }

  @Nested
  public class getMethodTests {

    @Test
    public void get_ReservationAsParameter_ShouldReturnSpecificReservationFromDB()
            throws InvalidReservationInputException, CouldNotFetchReservationException {

      ReservationDao reservationDao = new ReservationDao();
      Reservation reservation = reservationDao.get(new Reservation(1));

      assertAll(() -> assertEquals(1, reservation.getCustomerID()),
                () -> assertTrue(reservation.getPaid()),
                () -> assertEquals(1, reservation.getRoomID()));
    }

    @Test
    public void get_NullAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();
      Reservation reservation = null;

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.get(reservation));
    }

    @Test
    public void get_ReservationWithReservationIdZeroAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();
      Reservation reservation = new Reservation(0);

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.get(reservation));

    }

    @Test
    public void get_ReservationWithNegativeIntegerReservationIdAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();
      Reservation reservation = new Reservation(-1);

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.get(reservation));
    }

    @Test
    public void get_NoneExistingReservationInDbAsParameter_ShouldThrowCouldNotFetchReservationException() {
      ReservationDao reservationDao = new ReservationDao();
      Reservation reservation = new Reservation(6060);

      assertThrows(CouldNotFetchReservationException.class, () -> reservationDao.get(reservation));

    }
  }

  @Nested
  public class addMethodTests {

  @Test
  @DisplayName("Tests If the 'before all' test method added the reservation successfully")
  public void add_ReservationAsParameter_ShouldReturnSpecifiedReservationFromDbAfterAddingIt()
          throws InvalidReservationInputException,
          CouldNotFetchReservationException {

    ReservationDao reservationDao = new ReservationDao();
    Reservation reservation = new Reservation(4);

    Reservation reservation1 = reservationDao.get(reservation);

    assertAll(() -> assertEquals(1, reservation1.getCustomerID()),
              () -> assertFalse(reservation1.getPaid()),
              () -> assertEquals(1, reservation1.getRoomID()));

  }

    @Test
    public void add_NullAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();
      Reservation reservation = null;

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.add(reservation));
    }

    @Test
    public void add_ReservationWithCustomerIdZeroAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();

      Reservation reservation = new Reservation(0, 1, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false);

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.add(reservation));
    }

    @Test
    public void add_ReservationWithNegativeIntegerCustomerIdAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();

      Reservation reservation = new Reservation(-1, 1, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false);

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.add(reservation));
    }

    @Test
    public void add_ReservationWithNoneExistingCustomerIdInDbParameter_ShouldThrowCouldNotAddReservationException() {
      ReservationDao reservationDao = new ReservationDao();

      Reservation reservation = new Reservation(9999, 1, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false);

      assertThrows(CouldNotAddReservationException.class, () -> reservationDao.add(reservation));
    }

    @Test
    public void add_ReservationWithRoomIdZeroAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();

      Reservation reservation = new Reservation(1, 0, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false);

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.add(reservation));
    }

    @Test
    public void add_ReservationWithNegativeIntegerRoomIdAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();

      Reservation reservation = new Reservation(1, -1, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false);

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.add(reservation));
    }

    @Test
    public void add_ReservationWithNoneExistingRoomIdInDbAsParameter_ShouldThrowCouldNotAddReservationException() {
      ReservationDao reservationDao = new ReservationDao();

      Reservation reservation = new Reservation(1, 9999, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false);

      assertThrows(CouldNotAddReservationException.class, () -> reservationDao.add(reservation));
    }

    @Test
    public void add_ReservationWithNullStartDateAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();

      Reservation reservation = new Reservation(1, 0, null,
              Instant.ofEpochMilli(161999280000L), false);

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.add(reservation));
    }

    @Test
    public void add_ReservationWithNullEndDateAsParameter_ShouldThrowInvalidReservationInputException() {
      ReservationDao reservationDao = new ReservationDao();

      Reservation reservation = new Reservation(1, 0, Instant.ofEpochMilli(161973360000L),
              null, false);

      assertThrows(InvalidReservationInputException.class, () -> reservationDao.add(reservation));
    }
  }
}

// The update and delete methods are tested in BookingDaoTests since normally they will be used from
// through bookingDao

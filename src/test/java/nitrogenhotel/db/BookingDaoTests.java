package nitrogenhotel.db;

import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import nitrogenhotel.db.dataaccessobjects.BookingDao;
import nitrogenhotel.db.entries.*;
import nitrogenhotel.db.exceptions.booking.*;
import org.junit.jupiter.api.*;

public class BookingDaoTests extends dbTest{

  @BeforeAll
  @DisplayName("We create and add a Booking 'before all' otherwise we get problems with the auto" +
          "incrementing primary key in the DB ")
  public static void add_BookingAsParameter_AddsBookingToDB()
          throws InvalidBookingInputException, CouldNotAddBookingException {

    BookingDao bookingDao = new BookingDao();
    Booking booking = new Booking(new Reservation(1, 1, Instant.ofEpochMilli(161973360000L),
            Instant.ofEpochMilli(161999280000L), false));

    bookingDao.add(booking);
  }

  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllBookingException {
      BookingDao bookingDao = new BookingDao();
      List<Booking> allBookings = bookingDao.getAll();

      assertFalse(allBookings.isEmpty());
    }

    @Test
    public void getAll_ShouldReturnListContainingTheBookingsInTheDB() throws CouldNotFetchAllBookingException {
      BookingDao bookingDao = new BookingDao();
      List<Booking> allBookings = bookingDao.getAll();

      assertTrue(
              allBookings.stream()
                      .anyMatch(
                              booking ->
                                      booking.getReservation().getCustomerID() == 1
                                              && booking.getReservation().getRoomID() == 1
                                              //&& booking.getReservation().getStartDate().equals(
                                              // Instant.ofEpochMilli(1619733600000L))
                                              //&& booking.getReservation().getEndDate().equals(
                                              // Instant.ofEpochMilli(1619992800000L))
                                              && booking.getReservation().getPaid() == true

                                              && booking.getRoom().getFloor() == 1
                                              && booking.getRoom().getNumber() == 100
                                              && booking.getRoom().getNote().equals("nice designed room")
                                              && booking.getRoom().getSize().equals("DOUBLE")
                                              && booking.getRoom().getNbBeds() == 2

                                              && booking.getCustomer().getCustomerID() == 1
                                              && booking.getCustomer().getPublicID().equals("ARG 123456")
                                              && booking.getCustomer().getCustomerName().equals("Homer SIMPson")
                                              && booking.getCustomer().getAddress().equals("742 Evergreen Terrace")
                                              && booking.getCustomer().getPaymentMethod().equals("Visa")));
      assertTrue(
              allBookings.stream()
                      .anyMatch(
                              booking ->
                                      booking.getReservation().getCustomerID() == 2
                                              && booking.getReservation().getRoomID() == 2
                                              //&& booking.getReservation().getStartDate().equals(
                                              // Instant.ofEpochMilli(1619733600000L))
                                              //&& booking.getReservation().getEndDate().equals(
                                              // Instant.ofEpochMilli(1619992800000L))
                                              && booking.getReservation().getPaid() == false

                                              && booking.getRoom().getFloor() == 1
                                              && booking.getRoom().getNumber() == 101
                                              && booking.getRoom().getNote() == null
                                              && booking.getRoom().getSize().equals("SINGLE")
                                              && booking.getRoom().getNbBeds() == 1

                                              && booking.getCustomer().getCustomerID() == 2
                                              && booking.getCustomer().getPublicID().equals("19440606-1998")
                                              && booking.getCustomer().getCustomerName().equals("Revolver Ocelot")
                                              && booking.getCustomer().getAddress().equals("601 Outer Haven, Bering Sea")
                                              && booking.getCustomer().getPaymentMethod().equals("Master Card")));
    }
  }

  @Nested
  public class getMethodTests {

    @Test
    public void get_BookingAsParameter_ShouldReturnSpecifiedBookingFromDB()
            throws InvalidBookingInputException, CouldNotFetchBookingException {

      BookingDao bookingDao = new BookingDao();
      Booking booking = bookingDao.get(new Booking(1));

      assertAll(() -> assertEquals(1, booking.getReservation().getReservationID()),
                () -> assertEquals(1, booking.getReservation().getRoomID()),
                () -> assertTrue(booking.getReservation().getPaid()),
              //() -> assertEquals(booking.getReservation().getStartDate(), Instant.ofEpochMilli(1619733600000L)),
              //() -> assertEquals(booking.getReservation().getEndDate(), Instant.ofEpochMilli(1619992800000L)),

                () -> assertEquals(1, booking.getRoom().getFloor()),
                () -> assertEquals(100, booking.getRoom().getNumber()),
                () -> assertEquals("nice designed room", booking.getRoom().getNote()),
                () -> assertEquals("DOUBLE", booking.getRoom().getSize()),
                () -> assertEquals(2, booking.getRoom().getNbBeds()),

                () -> assertEquals(1, booking.getCustomer().getCustomerID()),
                () -> assertEquals("ARG 123456", booking.getCustomer().getPublicID()),
                () -> assertEquals("Homer SIMPson", booking.getCustomer().getCustomerName()),
                () -> assertEquals("742 Evergreen Terrace", booking.getCustomer().getAddress()),
                () -> assertEquals("Visa", booking.getCustomer().getPaymentMethod()));
    }

    @Test
    public void get_NullAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = null;

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.get(booking));
    }

    @Test
    public void get_BookingWithReservationIdZeroAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(0);

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.get(booking));
    }

    @Test
    public void get_BookingWithNegativeIntegerReservationIdAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(-1);

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.get(booking));
    }

    @Test
    public void get_NoneExistingReservationIdInDbAsParameter_ShouldThrowCouldNotFetchBookingException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(69696);

      assertThrows(CouldNotFetchBookingException.class, () -> bookingDao.get(booking));
    }
  }

  @Nested
  public class deleteMethodTests {

    @Test
    public void delete_BookingAsParameter_ShouldThrowCouldNotFetchBookingExceptionWhenFetchingDeletedBooking()
            throws CouldNotDeleteBookingException, InvalidBookingInputException {

      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(4);

      bookingDao.delete(booking);

      assertThrows(CouldNotFetchBookingException.class, () -> bookingDao.get(new Booking(4)));
    }

    @Test
    public void delete_NullAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = null;

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.delete(booking));
    }

    @Test
    public void delete_BookingWithReservationIdZeroAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(0);

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.delete(booking));
    }

    @Test
    public void delete_BookingWithNegativeIntegerReservationIdAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(-1);

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.delete(booking));
    }

    @Test
    public void delete_NoneExistingReservationIdInDbAsParameter_ShouldThrowCouldNotFetchBookingException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(999);

      assertDoesNotThrow(() -> bookingDao.delete(booking));
    }
  }

  @Nested
  public class addMethodTests {

    @Test
    @DisplayName("Tests If the 'before all' test method added the booking successfully")
    public void add_BookingAsParameter_ShouldReturnSpecifiedBookingFromDbAfterAddingIt()
            throws InvalidBookingInputException, CouldNotFetchBookingException{

      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(4);

      Booking booking1 = bookingDao.get(booking);

      assertAll(() -> assertEquals(1, booking1.getReservation().getCustomerID()),
                () -> assertEquals(1, booking1.getReservation().getRoomID()),
                () -> assertFalse(booking1.getReservation().getPaid()));
    }

    @Test
    public void add_NullAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = null;

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.add(booking));
    }

    @Test
    public void add_BookingWithCustomerIdZeroAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();

      Booking booking = new Booking(new Reservation(0, 1, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.add(booking));
    }

    @Test
    public void add_BookingWithNegativeIntegerCustomerIdAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();

      Booking booking = new Booking(new Reservation(-1, 1, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.add(booking));
    }

    @Test
    public void add_BookingWithNoneExistingCustomerIdInDbParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();

      Booking booking = new Booking(new Reservation(9999, 1, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false));

      assertThrows(CouldNotAddBookingException.class, () -> bookingDao.add(booking));
    }

    @Test
    public void add_BookingWithRoomIdZeroAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();

      Booking booking = new Booking(new Reservation(1, 0, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.add(booking));
    }

    @Test
    public void add_BookingWithNegativeIntegerRoomIdAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();

      Booking booking = new Booking(new Reservation(1, -1, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.add(booking));
    }

    @Test
    public void add_BookingWithNoneExistingRoomIdInDbAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();

      Booking booking = new Booking(new Reservation(1, 9999, Instant.ofEpochMilli(161973360000L),
              Instant.ofEpochMilli(161999280000L), false));

      assertThrows(CouldNotAddBookingException.class, () -> bookingDao.add(booking));
    }

    @Test
    public void add_BookingWithNullStartDateAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();

      Booking booking = new Booking(new Reservation(1, 0, null,
              Instant.ofEpochMilli(161999280000L), false));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.add(booking));
    }

    @Test
    public void add_BookingWithNullEndDateAsParameter_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();

      Booking booking = new Booking(new Reservation(1, 0, Instant.ofEpochMilli(161973360000L),
              null, false));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.add(booking));
    }
  }

  @Nested
  public class updateRoomIDMethodTests {

    @Test
    public void updateRoomID_NullAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = null;

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateRoomID(booking, 3));
    }

    @Test
    public void updateRoomID_BookingWithReservationIdZeroAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(0));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateRoomID(booking, 3));
    }

    @Test
    public void updateRoomID_BookingWithNegativeIntegerReservationIdAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(-1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateRoomID(booking, 3));
    }

    @Test
    public void updateRoomID_NoneExistingBookingInDbAs1ParameterPositiveIntegerAs2_ShouldNotThrowException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(9999));

      assertDoesNotThrow(() -> bookingDao.updateRoomID(booking, 3));
    }

    @Test
    public void updateRoomID_BookingAs1ParameterZeroAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateRoomID(booking, 0));
    }

    @Test
    public void updateRoomID_BookingAs1ParameterNegativeIntegerAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateCustomerID(booking, -1));
    }
  }

  @Nested
  public class updateCustomerIDMethodTests {

    @Test
    public void updateCustomerID_NullAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = null;

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateCustomerID(booking, 3));
    }

    @Test
    public void updateCustomerID_BookingWithReservationIdZeroAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(0));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateRoomID(booking, 3));
    }

    @Test
    public void updateCustomerID_BookingWithNegativeIntegerReservationIdAs1ParameterPositiveIntegerAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(-1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateRoomID(booking, 3));
    }

    @Test
    public void updateCustomerID_NoneExistingBookingInDbAs1ParameterPositiveIntegerAs2_ShouldNotThrowException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(9999));

      assertDoesNotThrow(() -> bookingDao.updateCustomerID(booking, 3));
    }

    @Test
    public void updateCustomerID_BookingAs1ParameterZeroAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateCustomerID(booking, 0));
    }

    @Test
    public void updateCustomerID_BookingAs1ParameterNegativeIntegerAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateCustomerID(booking, -1));
    }
  }

  @Nested
  public class updatePaidMethodTests {

    @Test
    public void updatePaid_NullAs1ParameterBooleanAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = null;

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updatePaid(booking, true));
    }

    @Test
    public void updatePaid_BookingWithReservationIdZeroAs1ParameterBooleanAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(0));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updatePaid(booking, true));
    }

    @Test
    public void updatePaid_BookingWithNegativeIntegerReservationIdAs1ParameterBooleanAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(-1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updatePaid(booking, true));
    }

    @Test
    public void updatePaid_NoneExistingBookingInDbAs1ParameterBooleanAs2_ShouldNotThrowException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(9999));

      assertDoesNotThrow(() -> bookingDao.updatePaid(booking, true));
    }
  }

  @Nested
  public class updateStartDateMethodTests {

    @Test
    public void updateStartDate_NullAs1ParameterInstantAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = null;

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateStartDate(
              booking, Instant.ofEpochMilli(1)));
    }

    @Test
    public void updateStartDate_BookingWithReservationIdZeroAs1ParameterInstantAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(0));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateStartDate(
              booking, Instant.ofEpochMilli(1)));
    }

    @Test
    public void updateStartDate_BookingWithNegativeIntegerReservationIdAs1ParameterInstantAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(-1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateStartDate(
              booking, Instant.ofEpochMilli(1)));
    }

    @Test
    public void updateStartDate_NoneExistingBookingInDbAs1ParameterInstantAs2_ShouldNotThrowException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(9999));

      assertDoesNotThrow(() -> bookingDao.updateStartDate(booking, Instant.ofEpochMilli(1)));
    }

    @Test
    public void updateStartDate_BookingAs1NullAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateStartDate(booking, null));
    }
  }

  @Nested
  public class updateEndDateMethodTests {

    @Test
    public void updateEndDate_NullAs1ParameterInstantAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = null;

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateEndDate(
              booking, Instant.ofEpochMilli(1)));
    }

    @Test
    public void updateEndDate_BookingWithReservationIdZeroAs1ParameterInstantAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(0));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateEndDate(
              booking, Instant.ofEpochMilli(1)));
    }

    @Test
    public void updateEndDate_BookingWithNegativeIntegerReservationIdAs1ParameterInstantAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(-1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateEndDate(
              booking, Instant.ofEpochMilli(1)));
    }

    @Test
    public void updateEndDate_NoneExistingBookingInDbAs1ParameterInstantAs2_ShouldNotThrowException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(9999));

      assertDoesNotThrow(() -> bookingDao.updateEndDate(booking, Instant.ofEpochMilli(1)));
    }

    @Test
    public void updateEndDate_BookingAs1NullAs2_ShouldThrowInvalidBookingInputException() {
      BookingDao bookingDao = new BookingDao();
      Booking booking = new Booking(new Reservation(1));

      assertThrows(InvalidBookingInputException.class, () -> bookingDao.updateEndDate(booking, null));
    }
  }

  @Nested
  public class getActiveBookingMethodTests {

    @Test
    public void getActiveBooking_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllActiveBookingException {
      BookingDao bookingDao = new BookingDao();
      List<Booking> allBookings = bookingDao.getActiveBookings();

      assertFalse(allBookings.isEmpty());
    }

    @Test
    public void getActiveBooking_ShouldReturnListContainingTheActiveBookingsInTheDB()
            throws CouldNotFetchAllActiveBookingException {

      BookingDao bookingDao = new BookingDao();
      List<Booking> allBookings = bookingDao.getActiveBookings();

      assertFalse(
              allBookings.stream()
                      .anyMatch(
                              booking ->
                                      booking.getReservation().getCustomerID() == 1
                                              && booking.getReservation().getRoomID() == 1
                                              //&& booking.getReservation().getStartDate().equals(
                                              // Instant.ofEpochMilli(1619733600000L))
                                              //&& booking.getReservation().getEndDate().equals(
                                              // Instant.ofEpochMilli(1619992800000L))
                                              && booking.getReservation().getPaid()

                                              && booking.getRoom().getFloor() == 1
                                              && booking.getRoom().getNumber() == 100
                                              && booking.getRoom().getNote().equals("nice designed room")
                                              && booking.getRoom().getSize().equals("DOUBLE")
                                              && booking.getRoom().getNbBeds() == 2

                                              && booking.getCustomer().getCustomerID() == 1
                                              && booking.getCustomer().getPublicID().equals("ARG 123456")
                                              && booking.getCustomer().getCustomerName().equals("Homer SIMPson")
                                              && booking.getCustomer().getAddress().equals("742 Evergreen Terrace")
                                              && booking.getCustomer().getPaymentMethod().equals("Visa")));
      assertTrue(
              allBookings.stream()
                      .anyMatch(
                              booking ->
                                      booking.getReservation().getCustomerID() == 2
                                              && booking.getReservation().getRoomID() == 2
                                              //&& booking.getReservation().getStartDate().equals(
                                              // Instant.ofEpochMilli(1619733600000L))
                                              //&& booking.getReservation().getEndDate().equals(
                                              // Instant.ofEpochMilli(1619992800000L))
                                              && booking.getReservation().getPaid()

                                              && booking.getRoom().getFloor() == 1
                                              && booking.getRoom().getNumber() == 101
                                              && booking.getRoom().getNote() == null
                                              && booking.getRoom().getSize().equals("SINGLE")
                                              && booking.getRoom().getNbBeds() == 1

                                              && booking.getCustomer().getCustomerID() == 2
                                              && booking.getCustomer().getPublicID().equals("19440606-1998")
                                              && booking.getCustomer().getCustomerName().equals("Revolver Ocelot")
                                              && booking.getCustomer().getAddress().equals("601 Outer Haven, Bering Sea")
                                              && booking.getCustomer().getPaymentMethod().equals("Master Card")));

    }
  }

  @Nested
  public class getActiveBookingByRoomMethodTests {

    @Test
    public void getActiveBookingByRoom_ReturnedListShouldNotBeEmpty()
            throws CouldNotFetchAllActiveBookingByRoomException {

      BookingDao bookingDao = new BookingDao();
      List<Booking> allBookings = bookingDao.getActiveBookingsByRoom(new Room(101));

      assertFalse(allBookings.isEmpty());
    }

    @Test
    public void getActiveBookingByRoom_ShouldReturnListContainingTheActiveBookingsByRoomInTheDB()
            throws CouldNotFetchAllActiveBookingByRoomException {
      BookingDao bookingDao = new BookingDao();
      List<Booking> allBookings = bookingDao.getActiveBookingsByRoom(new Room(101));

      assertFalse(
              allBookings.stream()
                      .anyMatch(
                              booking ->
                                      booking.getReservation().getCustomerID() == 1
                                              && booking.getReservation().getRoomID() == 1
                                              //&& booking.getReservation().getStartDate().equals(
                                              // Instant.ofEpochMilli(1619733600000L))
                                              //&& booking.getReservation().getEndDate().equals(
                                              // Instant.ofEpochMilli(1619992800000L))
                                              && booking.getReservation().getPaid()

                                              && booking.getRoom().getFloor() == 1
                                              && booking.getRoom().getNumber() == 100
                                              && booking.getRoom().getNote().equals("nice designed room")
                                              && booking.getRoom().getSize().equals("DOUBLE")
                                              && booking.getRoom().getNbBeds() == 2

                                              && booking.getCustomer().getCustomerID() == 1
                                              && booking.getCustomer().getPublicID().equals("ARG 123456")
                                              && booking.getCustomer().getCustomerName().equals("Homer SIMPson")
                                              && booking.getCustomer().getAddress().equals("742 Evergreen Terrace")
                                              && booking.getCustomer().getPaymentMethod().equals("Visa")));
      assertTrue(
              allBookings.stream()
                      .anyMatch(
                              booking ->
                                      booking.getReservation().getCustomerID() == 2
                                              && booking.getReservation().getRoomID() == 2
                                              //&& booking.getReservation().getStartDate().equals(
                                              // Instant.ofEpochMilli(1619733600000L))
                                              //&& booking.getReservation().getEndDate().equals(
                                              // Instant.ofEpochMilli(1619992800000L))
                                              && booking.getReservation().getPaid()

                                              && booking.getRoom().getFloor() == 1
                                              && booking.getRoom().getNumber() == 101
                                              && booking.getRoom().getNote() == null
                                              && booking.getRoom().getSize().equals("SINGLE")
                                              && booking.getRoom().getNbBeds() == 1

                                              && booking.getCustomer().getCustomerID() == 2
                                              && booking.getCustomer().getPublicID().equals("19440606-1998")
                                              && booking.getCustomer().getCustomerName().equals("Revolver Ocelot")
                                              && booking.getCustomer().getAddress().equals("601 Outer Haven, Bering Sea")
                                              && booking.getCustomer().getPaymentMethod().equals("Master Card")));

    }
  }
}

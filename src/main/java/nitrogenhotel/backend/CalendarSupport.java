package nitrogenhotel.backend;

import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import nitrogenhotel.db.dataaccessobjects.BookingDao;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllActiveBookingByRoomException;
import org.tinylog.Logger;

/** Abstract class for vetoing dates in the calendar. */
public abstract class CalendarSupport implements DateVetoPolicy {

  protected BookingDao bookingDao;
  protected List<Booking> bookings;
  protected HashSet<LocalDate> badDates;
  protected LocalDate startDate;
  protected LocalDate endDate;
  protected LocalDate badDate;

  /** Supports the calendar builder. */
  public CalendarSupport() {
    this.bookingDao = new BookingDao();
    this.badDates = new HashSet<>();
  }

  /** Call to update isDateAllowed with the info for the selected room. */
  public void refreshBookings(Room room) {
    try {
      bookings = bookingDao.getActiveBookingsByRoom(room);
    } catch (CouldNotFetchAllActiveBookingByRoomException e) {
      Logger.warn(e);
      return;
    }
    parseBookings();
  }

  /**
   * Call to update isDateAllowed with the info for the selected room.
   * Same as refreshBookings but does not veto the supplied booking's dates.
   * Used to reschedule a booking without the booking itself blocking any dates.
   */
  public void refreshEligibleDates(Room room, Booking booking) {
    try {
      bookings = bookingDao.getActiveBookingsByRoom(room);
    } catch (CouldNotFetchAllActiveBookingByRoomException e) {
      Logger.warn(e);
      return;
    }
    Booking remove = null;
    for (Booking b : bookings) {
      if (b.getReservation().getReservationID() == booking.getReservation().getReservationID()) {
        remove = b;
      }
    }
    bookings.remove(remove);
    parseBookings();
  }

  protected abstract void parseBookings();

  @Override
  public boolean isDateAllowed(LocalDate date) {
    if (LocalDate.now().isAfter(date)) {
      return false;
    }
    return !badDates.contains(date);
  }
}

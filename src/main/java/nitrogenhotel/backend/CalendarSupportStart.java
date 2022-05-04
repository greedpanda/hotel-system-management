package nitrogenhotel.backend;

import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.utils.DateConverter;

/** Used to mark dates at which you cannot START a booking. */
public class CalendarSupportStart extends CalendarSupport {

  public CalendarSupportStart() {
    super();
  }

  @Override
  protected void parseBookings() {
    badDates.clear();
    for (Booking b : bookings) {
      startDate = DateConverter.convertToDate(b.getReservation().getStartDate());
      endDate = DateConverter.convertToDate(b.getReservation().getEndDate());
      if (!startDate.equals(endDate)) {
        badDate = startDate.plusDays(1);
        while (!badDate.equals(endDate)) {
          badDates.add(badDate);
          badDate = badDate.plusDays(1);
        }
      }
      badDates.add(startDate);
    }
  }
}

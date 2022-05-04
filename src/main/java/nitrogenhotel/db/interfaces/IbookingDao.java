package nitrogenhotel.db.interfaces;

import java.time.Instant;
import java.util.List;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Room;

/**
 * Defines update methods for bookingDao.
 */
public interface IbookingDao {

  void updateRoomID(Booking obj, int roomID) throws Exception;

  void updateCustomerID(Booking obj, int customerID) throws Exception;

  void updatePaid(Booking obj, boolean paid) throws Exception;

  void updateStartDate(Booking obj, Instant startDate) throws Exception;

  void updateEndDate(Booking obj, Instant endDate) throws Exception;

  List<Booking> getActiveBookings() throws Exception;

  List<Booking> getActiveBookingsByRoom(Room room) throws Exception;

}

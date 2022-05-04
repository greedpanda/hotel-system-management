package nitrogenhotel.db.interfaces;

import java.time.Instant;
import nitrogenhotel.db.entries.Reservation;

/**
 * Defines update methods for reservationDao.
 */
public interface IreservationDao {

  void updateRoomID(Reservation res, int roomID) throws Exception;

  void updateCustomerID(Reservation res, int customerID) throws Exception;

  void updatePaid(Reservation res, boolean paid) throws Exception;

  void updateStartDate(Reservation res, Instant startdate) throws Exception;

  void updateEndDate(Reservation res, Instant endDate) throws Exception;

}

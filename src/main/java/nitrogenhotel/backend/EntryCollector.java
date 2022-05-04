package nitrogenhotel.backend;

import java.util.Comparator;
import java.util.List;
import nitrogenhotel.db.dataaccessobjects.BookingDao;
import nitrogenhotel.db.dataaccessobjects.CustomerDao;
import nitrogenhotel.db.dataaccessobjects.FloorDao;
import nitrogenhotel.db.dataaccessobjects.RoomDao;
import nitrogenhotel.db.dataaccessobjects.RoomTypeDao;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllActiveBookingException;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllBookingException;
import nitrogenhotel.db.exceptions.customer.CouldNotFetchAllCustomersException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllFloorException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllRoomTypesException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllRoomsException;

/**
 * Utility class for collecting Entry lists from the database.
 * Will be used for fetching lists of Rooms, Bookings, Privileges, Users.
 */
public class EntryCollector {

  public static List<Room> allRooms() throws CouldNotFetchAllRoomsException {
    return new RoomDao().getAll();
  }

  /** Fetches and sorts all the room types. */
  public static List<RoomType> allRoomTypes() throws CouldNotFetchAllRoomTypesException {
    List<RoomType> roomTypes = new RoomTypeDao().getAll();
    roomTypes.sort(Comparator.comparing(RoomType::indexOf));
    return roomTypes;
  }

  public static List<Floor> allFloors() throws CouldNotFetchAllFloorException {  // OCD: floor is not plural...
    return new FloorDao().getAll();
  }

  public static List<Customer> allCustomers() throws CouldNotFetchAllCustomersException {
    return new CustomerDao().getAll();
  }

  public static List<Booking> allBookings() throws CouldNotFetchAllBookingException {
    return new BookingDao().getAll();
  }

  public static List<Booking> activeBookings() throws CouldNotFetchAllActiveBookingException {
    return new BookingDao().getActiveBookings();
  }
}

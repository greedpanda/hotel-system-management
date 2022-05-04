package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import nitrogenhotel.db.dataaccessobjects.BookingDao;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.entries.Reservation;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.booking.CouldNotAddBookingException;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllActiveBookingByRoomException;
import nitrogenhotel.db.exceptions.booking.InvalidBookingInputException;
import nitrogenhotel.ui.panels.BookingAddPanelUI;
import nitrogenhotel.utils.DateConverter;
import org.threeten.extra.Interval;
import org.tinylog.Logger;

/** Backend class for adding booking logic. */
public class BookingAddPanel extends CommandPanel {
  public BookingAddPanelUI ui;
  private MainFrame mf;

  public BookingAddPanel(List<Customer> customers, List<Room> rooms,
                         List<RoomType> roomTypeList, List<Booking> activeBookings) {
    super(new BookingAddPanelUI(customers, rooms, roomTypeList, activeBookings));
    ui = (BookingAddPanelUI) super.ui;
  }

  public BookingAddPanel(MainFrame mf, List<Customer> customers, List<Room> rooms,
                         List<RoomType> roomTypeList, List<Booking> activeBookings) {
    this(customers, rooms, roomTypeList, activeBookings);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {
      case "add booking ok" -> {
        if (run()) {
          mf.clearCurrentPanel();
          ui.success("Booking added.");
        }
      }
      case "cancel" -> mf.clearCurrentPanel();

      default -> Logger.warn("Received unknown actionCommand '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    int custID;
    int roomNumber;
    int roomID;
    Instant startDateInstant;
    Instant endDateInstant;
    LocalDate startDate;
    LocalDate endDate;
    List<Booking> allBookings;
    boolean isPaid = ui.isPaid();

    if (ui.getRoomType() == null) {
      Logger.warn("User selected no room type for new booking.");
      ui.warn("Please select a room type.");
      return false;
    }

    if (ui.getStartDate() == null) {
      Logger.warn("User selected no start date for new booking.");
      ui.warn("Please select a start date.");
      return false;
    }

    if (ui.getEndDate() == null) {
      Logger.warn("User selected no end date for new booking.");
      ui.warn("Please select an end date.");
      return false;
    }

    if (ui.getRoomID() == null) {
      Logger.warn("User selected parameters have no room available.");
      ui.warn("No room is available for the selected room type, start date and end date.\n"
              + "Please try another combination.");
      return false;
    }

    if (ui.getRoomNumber() == null) {
      Logger.warn("User selected no room number for new booking.");
      ui.warn("Please select a room number.");
      return false;
    }

    if (ui.getCustomerID() == null) {
      Logger.warn("User selected no customer for new booking.");
      ui.warn("Please select a customer.");
      return false;
    }

    try {
      startDateInstant = ui.getStartDate();
      endDateInstant = ui.getEndDate();
    } catch (NumberFormatException e) {
      Logger.warn("Booking parsing from GUI failed with exception {}", e);
      ui.warn("Dates do not have valid format DD-MM-YYYY.");
      return false;
    }

    try {
      roomNumber = Integer.parseInt(ui.getRoomNumber());
    } catch (NumberFormatException e) {
      Logger.warn("Booking parsing from GUI failed with exception {}", e);
      ui.warn("Invalid Room Number.");
      return false;
    }

    try {
      roomID = Integer.parseInt(ui.getRoomID());
    } catch (NumberFormatException e) {
      Logger.warn("Booking parsing from GUI failed with exception {}", e);
      ui.warn("Invalid Room ID.");
      return false;
    }

    try {
      custID = Integer.parseInt(ui.getCustomerID());
    } catch (NumberFormatException e) {
      Logger.warn("Booking parsing from GUI failed with exception {}", e);
      ui.warn("Invalid for Customer ID.");
      return false;
    }

    startDate = DateConverter.convertToDate(startDateInstant);
    endDate = DateConverter.convertToDate(endDateInstant);

    if (startDate.isAfter(endDate) || startDate.equals(endDate)) {
      Logger.warn("User tried to book a room for 0 or negative days.");
      ui.warn("Invalid booking. End date must be after Start date.");
      return false;
    }

    if (startDate.isBefore(LocalDate.now())) {
      Logger.warn("User tried to book a room in the past.");
      ui.warn("Cannot book room before current day: %s.".formatted(LocalDate.now()));
      return false;
    }

    try {
      allBookings = new BookingDao().getActiveBookingsByRoom(new Room(roomNumber));
    } catch (CouldNotFetchAllActiveBookingByRoomException e) {
      Logger.warn(e);
      ui.warn("Database error: could not fetch active bookings from database.");
      return false;
    }

    for (Booking b : allBookings) {
      Interval existingBooking = Interval.of(b.getReservation().getStartDate(), b.getReservation().getEndDate());
      Interval newBooking = Interval.of(startDateInstant, endDateInstant);
      if (newBooking.overlaps(existingBooking)) {
        Logger.warn("User attempted to overlap bookings for room '{}'", roomNumber);
        ui.warn("Attempted to overlap bookings for room %s".formatted(roomNumber));
        return false;
      }
    }

    try {
      new BookingDao().add(new Booking(new Reservation(custID, roomID, startDateInstant, endDateInstant, isPaid)));
    } catch (CouldNotAddBookingException e) {
      Logger.warn(e);
      ui.warn("Database error: could not add booking to the database.");
      return false;
    } catch (InvalidBookingInputException e) {
      Logger.warn(e);
      ui.warn("Database error: new booking has invalid data.");
      return false;
    }

    return true;
  }
}

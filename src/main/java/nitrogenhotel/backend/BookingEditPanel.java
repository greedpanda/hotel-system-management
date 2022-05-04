package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nitrogenhotel.db.dataaccessobjects.BookingDao;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllBookingException;
import nitrogenhotel.db.exceptions.booking.InvalidBookingInputException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateCustomerForReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateDateOfReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdatePaidForReservationException;
import nitrogenhotel.db.exceptions.reservation.CouldNotUpdateRoomForReservationException;
import nitrogenhotel.db.exceptions.reservation.InvalidReservationInputException;
import nitrogenhotel.ui.panels.BookingEditPanelUI;
import org.threeten.extra.Interval;
import org.tinylog.Logger;

/** Backend logic for editing bookings. */
public class BookingEditPanel extends CommandPanel {
  public BookingEditPanelUI ui;
  private MainFrame mf;

  public BookingEditPanel(List<Booking> bookings, List<Customer> customers, List<Room> rooms,
                          List<RoomType> roomTypes) {
    super(new BookingEditPanelUI(bookings, customers, rooms, roomTypes));
    ui = (BookingEditPanelUI) super.ui;
  }

  public BookingEditPanel(MainFrame mf, List<Booking> bookings, List<Customer> customers, List<Room> rooms,
                          List<RoomType> roomTypes) {
    this(bookings, customers, rooms, roomTypes);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {
      case "edit booking ok" -> {
        mf.clearCurrentPanel();
        if (run()) {
          ui.success("Booking(s) edited.");
        }
      }

      case "edit booking apply" -> {
        if (run()) {
          mf.refreshCurrentPanel();
        }
      }

      case "cancel" -> mf.clearCurrentPanel();

      default -> Logger.warn("Received unknown action Command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    Map<Integer, Booking> newBookings = ui.getEditedBookingData();
    if (newBookings == null) {
      Logger.warn("BookingEditPanelUI returned null for newBookings");
      ui.warn("Could not update bookings.");
      return false;
    }

    if (newBookings.size() == 0) {
      Logger.info("No booking data edited skipping refresh");
      return false;
    }

    Logger.debug("Edited bookings returned from GUI\n%s".formatted(newBookings));

    BookingDao bookingDao = new BookingDao();
    List<Booking> oldBookings;
    try {
      oldBookings = bookingDao.getAll();
    } catch (CouldNotFetchAllBookingException e) {
      Logger.warn(e);
      return false;
    }

    // TODO: Confirm these are proper comparisons
    for (Map.Entry<Integer, Booking> entry : newBookings.entrySet()) {
      Booking newB = entry.getValue();
      List<Booking> filteredOldBs = oldBookings.stream()
              .filter(b ->
                  b.getReservation().getReservationID() == newB.getReservation().getReservationID())
              .collect(Collectors.toList());
      int reservationID = newB.getReservation().getReservationID();

      for (Booking b : oldBookings) {
        if (b.getReservation().getReservationID() != newB.getReservation().getReservationID()
            && b.getRoom().getNumber() == newB.getRoom().getNumber()) {
          Interval existingBooking = Interval.of(b.getReservation().getStartDate(), b.getReservation().getEndDate());
          Interval newBooking = Interval.of(newB.getReservation().getStartDate(), newB.getReservation().getEndDate());
          if (newBooking.overlaps(existingBooking)) {
            Logger.warn("User attempted to overlap bookings for room '{}'", newB.getRoom().getNumber());
            ui.warn("Attempted to overlap bookings for room %s".formatted(newB.getRoom().getNumber()));
            return false;
          }
        }
      }


      if (filteredOldBs.size() == 0) {
        Logger.info("Booking with reservation ID {} not in old bookings... skipping", reservationID);
        continue;
      }

      if (filteredOldBs.size() > 1) {
        Logger.warn("Non unique reservation found stopping edit");
        ui.warn("Overlapping bookings found.");
        return false;
      }

      Booking oldB = filteredOldBs.get(0);
      if (!newB.equalCustomerID(oldB)) {
        Logger.info("Updating CustomerID for reservationID {}", reservationID);

        try {
          bookingDao.updateCustomerID(oldB, newB.getCustomer().getCustomerID());
        } catch (InvalidBookingInputException | InvalidReservationInputException e) {
          Logger.warn(e);
          ui.warn("Database error: Booking with reservation id %s has invalid data."
              .formatted(newB.getReservation().getReservationID())
          );
          return false;
        } catch (CouldNotUpdateCustomerForReservationException e) {
          Logger.warn(e);
          ui.warn("Database error: Customer for reservation id %s could not be updated."
                  .formatted(newB.getReservation().getReservationID()));
          return false;
        }
      }

      if (!newB.equalRoomID(oldB)) {
        Logger.info("Updating RoomID for reservationID {}", reservationID);

        try {
          bookingDao.updateRoomID(oldB, newB.getReservation().getRoomID());
        } catch (CouldNotUpdateRoomForReservationException e) {
          Logger.warn(e);
          ui.warn("Database error: could not update room for reservation id %s."
                  .formatted(newB.getReservation().getReservationID()));
          return false;
        } catch (InvalidReservationInputException | InvalidBookingInputException e) {
          Logger.warn(e);
          ui.warn("Database error: booking with reservation id %s has invalid data."
              .formatted(newB.getReservation().getReservationID()));
          return false;
        }
      }

      if (!newB.equalStartDate(oldB)) {
        Logger.info("Updating StartDate for reservationID {}", reservationID);

        try {
          bookingDao.updateStartDate(oldB, newB.getReservation().getStartDate());
        } catch (CouldNotUpdateDateOfReservationException e) {
          Logger.warn(e);
          ui.warn("Database error: start date for reservation id %s could not be updated."
                  .formatted(newB.getReservation().getReservationID()));
          return false;
        } catch (InvalidBookingInputException | InvalidReservationInputException e) {
          Logger.warn(e);
          ui.warn("Database error: booking with reservation id %s has invalid data."
              .formatted(newB.getReservation().getReservationID()));
          return false;
        }
      }

      if (!newB.equalEndDate(oldB)) {
        Logger.info("Updating EndDate for reservationID {}", reservationID);

        try {
          bookingDao.updateEndDate(oldB, newB.getReservation().getEndDate());
        } catch (CouldNotUpdateDateOfReservationException e) {
          Logger.warn(e);
          ui.warn("Database error: end date for reservation id %s could not be updated."
                  .formatted(newB.getReservation().getReservationID()));
          return false;
        } catch (InvalidBookingInputException | InvalidReservationInputException e) {
          Logger.warn(e);
          ui.warn("Database error: booking with reservation id %s has invalid data."
              .formatted(newB.getReservation().getReservationID()));
          return false;
        }
      }

      if (!newB.equalPaid(oldB)) {
        Logger.info("Updating Paid for reservationID {}", reservationID);

        try {
          bookingDao.updatePaid(oldB, newB.getReservation().getPaid());
        } catch (CouldNotUpdatePaidForReservationException e) {
          Logger.warn(e);
          ui.warn("Database error: paid/unpaid for reservation id %s could not be updated."
                  .formatted(newB.getReservation().getReservationID()));
          return false;
        } catch (InvalidReservationInputException | InvalidBookingInputException e) {
          Logger.warn(e);
          ui.warn("Database error: booking with reservation id %s has invalid data."
              .formatted(newB.getReservation().getReservationID()));
          return false;
        }
      }
    }

    return true;
  }
}

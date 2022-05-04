package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.util.List;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.ui.panels.BookingDetailsPanelUI;
import org.tinylog.Logger;

/**
 * Panel responsible for booking details.
 */
public class BookingDetailsPanel extends CommandPanel {
  public BookingDetailsPanelUI ui;
  private MainFrame mf;

  public BookingDetailsPanel(List<Booking> bookings, List<Room> rooms) {
    super(new BookingDetailsPanelUI(bookings, rooms));
    ui = (BookingDetailsPanelUI) super.ui;
  }

  public BookingDetailsPanel(MainFrame mf, List<Booking> bookings, List<Room> rooms) {
    this(bookings, rooms);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    if (actionCommand.equals("cancel")) {
      mf.clearCurrentPanel();
    } else {
      Logger.warn("Received unknown action Command '{}'", actionCommand);
    }
  }

  /** Fetch all bookings and pass them to the GUI. */
  @Override
  public boolean run() {
    return true;
  }
}

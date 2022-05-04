package nitrogenhotel.ui.actioncommands;

/**
 * Action command for adding a booking.
 */
public enum BookingAddActionCommand {
  BOOKROOM("add booking"),
  OK("add booking ok"),
  CANCEL("cancel");

  public final String str;

  BookingAddActionCommand(String str) {
    this.str = str;
  }
}

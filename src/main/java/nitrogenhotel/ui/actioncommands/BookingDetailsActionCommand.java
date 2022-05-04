package nitrogenhotel.ui.actioncommands;

/** Bookings overview enumeration. */
public enum BookingDetailsActionCommand {
  BOOKINGDETAILS("booking details"), 
  CLOSE("cancel");

  public final String str;

  BookingDetailsActionCommand(String str) {
    this.str = str;
  }

}

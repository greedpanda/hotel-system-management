package nitrogenhotel.ui.actioncommands;

/** Edit booking enumeration. */
public enum BookingEditActionCommand {
  EDITBOOKING("edit booking"),
  APPLY("edit booking apply"), 
  OK("edit booking ok"), 
  CANCEL("cancel");
  
  public final String str;

  BookingEditActionCommand(String str) {
    this.str = str;
  }
}

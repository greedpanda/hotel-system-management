package nitrogenhotel.ui.actioncommands;

/** Delete room enumeration. */
public enum RoomDeleteActionCommand {
  DELETEROOM("delete room"), 
  DELETE("delete room delete"), 
  CLOSE("cancel");

  public final String str;

  RoomDeleteActionCommand(String str) {
    this.str = str;
  }
}

package nitrogenhotel.ui.actioncommands;

/** Add room size enumeration. */
public enum RoomAddSizeActionCommand {
  ADDROOMSIZE("add room"),
  OK("add room ok"),
  CANCEL("cancel");

  public final String str;

  RoomAddSizeActionCommand(String str) {
    this.str = str;
  }
}

package nitrogenhotel.ui.actioncommands;

/** Add room enumeration. */
public enum RoomAddActionCommand {
  ADDROOM("add room"), 
  OK("add room ok"), 
  CANCEL("cancel");

  public final String str;

  RoomAddActionCommand(String str) {
    this.str = str;
  }

}

package nitrogenhotel.ui.actioncommands;

/** Edit room enumeration. */
public enum RoomEditActionCommand {
  EDITROOM("edit room"),
  APPLY("edit room apply"), 
  OK("edit room ok"), 
  CANCEL("cancel");

  public final String str;

  RoomEditActionCommand(String str) {
    this.str = str;
  }
}

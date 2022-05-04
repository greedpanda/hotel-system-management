package nitrogenhotel.ui.actioncommands;

/** Room details enums. */
public enum RoomDetailsActionCommand {
  ROOMDETAILS("room details"), 
  CLOSE("cancel");

  public final String str;

  RoomDetailsActionCommand(String str) {
    this.str = str;
  }

}

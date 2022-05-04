package nitrogenhotel.ui.actioncommands;

/** Edit password enums. */
public enum EditPasswordActionCommand {
  EDITPASSWORD("edit password"),
  OK("edit password ok"),
  CANCEL("cancel");

  public final String str;

  EditPasswordActionCommand(String str) {
    this.str = str;
  }
}

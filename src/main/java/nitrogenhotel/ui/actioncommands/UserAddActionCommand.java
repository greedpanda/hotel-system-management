package nitrogenhotel.ui.actioncommands;

/** Add user enumeration. */
public enum UserAddActionCommand {
  ADDUSER("add user"),
  OK("add user ok"),
  CANCEL("cancel");

  public final String str;

  UserAddActionCommand(String str) { 
    this.str = str; 
  }
}

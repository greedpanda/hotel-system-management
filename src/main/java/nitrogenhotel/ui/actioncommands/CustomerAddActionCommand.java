package nitrogenhotel.ui.actioncommands;

/** Add customer enumeration. */
public enum CustomerAddActionCommand {
  ADDCUSTOMER("add customer"),
  OK("add customer ok"),
  CANCEL("cancel");

  public final String str;

  CustomerAddActionCommand(String str) {
    this.str = str;
  }
}

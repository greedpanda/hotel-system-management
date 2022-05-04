package nitrogenhotel.ui.actioncommands;

/** Customer details enums. */
public enum CustomerDetailsActionCommand {
  CUSTOMERDETAILS("customer details"), 
  CLOSE("cancel");

  public final String str;

  CustomerDetailsActionCommand(String str) {
    this.str = str;
  }
}

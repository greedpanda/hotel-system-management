package nitrogenhotel.ui.actioncommands;

/**
 * Edit customer enumeration.
 */
public enum CustomerEditActionCommand {
  EDITCUSTOMER("edit customer"), 
  APPLY("edit customer apply"), 
  OK("edit customer ok"), 
  CANCEL("cancel");

  public final String str;

  CustomerEditActionCommand(String str) {
    this.str = str;
  }
}

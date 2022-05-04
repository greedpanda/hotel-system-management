package nitrogenhotel.ui.utilsgui;

/** ErrorNotifications strings. */
public enum ErrorNotifications {
  FAILED("Invalid input"),
  ERROR("Internal Server Error"),
  DEFAULT("An unspecified error occurred");

  public final String str;

  ErrorNotifications(String str) {
    this.str = str;
  }
}

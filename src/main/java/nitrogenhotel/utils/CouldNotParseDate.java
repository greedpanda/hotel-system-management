package nitrogenhotel.utils;

/**
 * An exception to be thrown when date could not be parsed.
 * */
public class CouldNotParseDate extends Exception {

  public CouldNotParseDate(String msg, Throwable e) {
    super(msg, e);
  }
}

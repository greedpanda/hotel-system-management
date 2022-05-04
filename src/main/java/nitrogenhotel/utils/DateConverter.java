package nitrogenhotel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Date utility class.
 */
public class DateConverter {

  /**
   * Method for returning date in YYYY-MM-DD.
   *
   * @param time instant time object.
   * @return LocalDate in the form of YYYY-MM-DD.
   */
  public static LocalDate convertToDate(Instant time) {
    return time.atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * Method for returning an instant from a string.
   *
   * @param date String in the form of year-month-date.
   * @return Gives an instant object.
   * @throws CouldNotParseDate Failed to parse date and exception.
   */
  public static Instant castToInstant(Object date) throws CouldNotParseDate {
    try {
      Date rawDate = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
      return rawDate.toInstant();
    } catch (ParseException e) {
      throw new CouldNotParseDate(date + "  could not be parsed into an instant", e);
    }
  }
}

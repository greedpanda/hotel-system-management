package nitrogenhotel.backend;

import java.util.regex.Pattern;

/** Input patterns enumeration to facilitate the insertion limitations. */
public enum InputPatterns {
  USERNAME(Pattern.compile("\\w{3,}"), "Username must be at least 3 characters long."),
  PASSWORD(Pattern.compile("(?=\\w*\\d)(?=\\w*[a-zA-Z])\\w{8,}"),
          "Password must be at least 8 characters long.\n"
                  + "Password must contain at least 1 letter and 1 number."),
  SCREEN_NAME(Pattern.compile("\\w{3,}"), "Screen name must be at least 3 character long."),
  CUST_NAME(Pattern.compile("[a-zA-Z]{2,} [a-zA-Z]{2,}"),
          "Customer name must contain both first and last name separated by space.\n"
                  + "Names must be at least two characters long."),
  CUST_PUBLIC_ID(Pattern.compile("[a-zA-Z0-9- ]{6,}"), "Public ID must be at least 6 characters long."),
  CUST_ADDRESS(Pattern.compile("[a-zA-Z0-9,. ]{3,}"), "Address must be at least 3 characters long.");

  public final Pattern pattern;
  public final String userWarnMsg;
  InputPatterns(Pattern p, String s) {
    pattern = p;
    userWarnMsg = s;
  }

  public boolean test(String s) {
    return this.pattern.asMatchPredicate().test(s);
  }
}

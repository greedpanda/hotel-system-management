package nitrogenhotel.utils;

/**
 * App Version controller.
 */
public class VersionController {

  private static final String majorVersion = "5";
  private static final String minorVersion = "1";
  private static final String patchVersion = "0";
  private static final String devLevel = "RELEASE";

  private VersionController() {

  }

  public static String getVersion() {
    return majorVersion + "." + minorVersion + "." + patchVersion + "-" + devLevel;
  }

  public static String getMajorVersion() {
    return majorVersion;
  }

  public static String getMinorVersion() {
    return minorVersion;
  }

  public static String getPatchVersion() {
    return patchVersion;
  }

  public static String getDevLevel() {
    return devLevel;
  }

}

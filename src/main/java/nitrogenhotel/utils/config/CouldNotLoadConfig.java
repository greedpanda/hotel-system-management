package nitrogenhotel.utils.config;

/**
 * An exception to be thrown when config can not be loaded.
 * */
public class CouldNotLoadConfig extends Exception {

  public CouldNotLoadConfig(String msg, Throwable e) {
    super(msg, e);
  }
}

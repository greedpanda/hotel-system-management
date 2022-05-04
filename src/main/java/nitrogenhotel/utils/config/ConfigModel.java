package nitrogenhotel.utils.config;

/** Model configuration. */
public class ConfigModel {
  private final String host;
  private final String port;
  private final String username;
  private final String password;
  private final boolean development;

  /** Model configuration constructor. */
  public ConfigModel(String host, String port, String username, String password, boolean development) {
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.development = development;
  }

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public boolean getDevelopment() {
    return development;
  }

}

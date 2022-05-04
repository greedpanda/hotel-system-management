package nitrogenhotel.utils.config;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BooleanSupplier;
import org.tinylog.Logger;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

/** Loader for configuration. */
public class ConfigLoader {
  private static ConfigLoader singleConfig;
  private ConfigModel config;

  private ConfigLoader() {
    this.load(Paths.get("config.toml"));
  }

  /** Getter for configuration loader. */
  public static ConfigModel getInstance() {
    if (singleConfig == null) {
      singleConfig = new ConfigLoader();
    }

    return singleConfig.config;
  }

  private void load(Path location) {
    try {
      TomlParseResult result = Toml.parse(location);
      result.errors().forEach(error -> Logger.error("Failed to resolve value ", error.toString()));
      this.config = populate(result);
    } catch (IOException e) {
      Logger.error("Failed to load configuration, using default.");
      this.config = new ConfigModel("127.0.0.1", "3306", "root", "root", true);
    }
  }

  private ConfigModel populate(TomlParseResult result) {
    // Values to try for
    String host = result.getString("host");

    if (host == null) {
      host = "127.0.0.1";
    }

    String port = result.getString("port");
    if (port == null) {
      port = "3306";
    }

    String user = result.getString("user");
    if (user == null) {
      user = "root";
    }

    String password = result.getString("password");
    if (password == null) {
      password = "root";
    }

    boolean development = result.getBoolean("development", () -> true);

    return new ConfigModel(host, port, user, password, development);
  }

}

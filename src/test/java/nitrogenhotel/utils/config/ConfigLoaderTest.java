package nitrogenhotel.utils.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConfigLoaderTest {

  @Test
  @DisplayName("Testing Config save state default settings.")
  public void TestSaveState() {
    ConfigModel config = ConfigLoader.getInstance();
    assertEquals(config.getHost(), "127.0.0.1");
    assertEquals(config.getUsername(), "root");
    assertEquals(config.getPassword(), "root");
    assertEquals(config.getPort(), "3306");
    assertTrue(config.getDevelopment());
  }

}
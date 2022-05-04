package nitrogenhotel.db;

import nitrogenhotel.db.exceptions.databasemanager.CloseConnectionException;
import nitrogenhotel.db.exceptions.databasemanager.ConnectionException;
import nitrogenhotel.db.exceptions.databasemanager.CouldNotSetupDataBaseException;
import nitrogenhotel.db.exceptions.databasemanager.DataBaseDriverException;
import nitrogenhotel.db.exceptions.databasemanager.SchemaNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class dbTest {

  @BeforeAll
  public static void startConnection()
      throws ConnectionException, CloseConnectionException, DataBaseDriverException,
      SchemaNotFoundException, CouldNotSetupDataBaseException {
    boolean ciRunning = Boolean.parseBoolean(System.getProperty("ciRunning"));
    if (ciRunning) {
      DataBaseManager.startConnection(
          "jdbc:mysql://mysql?useTimezone=true&serverTimezone=UTC",
          "NitrogenHotel",
          "root",
          "root");
    } else {
      DataBaseManager.startConnection();
    }
  }

  @AfterAll
  public static void closeConnection() throws CloseConnectionException {
    DataBaseManager.closeConnection();
  }
}

package nitrogenhotel;

import nitrogenhotel.backend.MainFrame;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.exceptions.databasemanager.CloseConnectionException;
import nitrogenhotel.db.exceptions.databasemanager.ConnectionException;
import nitrogenhotel.db.exceptions.databasemanager.CouldNotSetupDataBaseException;
import nitrogenhotel.db.exceptions.databasemanager.DataBaseDriverException;
import nitrogenhotel.db.exceptions.databasemanager.SchemaNotFoundException;
import nitrogenhotel.ui.panels.WelcomeScreen;
import nitrogenhotel.utils.CouldNotParseDate;
import org.tinylog.Logger;

/**
 * Main class responsible for starting application.
 */
public class Main {

  /**
   * Main method responsible for starting application.
   *
   * @param args CLI arguments passed to application.
   */
  public static void main(String[] args) {

    try {
      DataBaseManager.startConnection();
    } catch (SchemaNotFoundException
        | DataBaseDriverException
        | ConnectionException
        | CloseConnectionException
        | CouldNotSetupDataBaseException e) {
      Logger.error(e);
      return;
    }

    new WelcomeScreen();
    new MainFrame();  // Block until character exits

  }
}

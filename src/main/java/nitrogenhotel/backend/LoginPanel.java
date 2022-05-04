package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import nitrogenhotel.db.dataaccessobjects.UserDao;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.user.CouldNotFetchUserException;
import nitrogenhotel.db.exceptions.user.InvalidUserInputException;
import nitrogenhotel.ui.actioncommands.LoginActionCommand;
import nitrogenhotel.ui.panels.LoginPanelUI;
import nitrogenhotel.utils.ActiveUser;
import org.tinylog.Logger;

/**
 * Validates input on ActionEvent of the login button being pressed
 * and calls database to ensure input is valid.
 */
public class LoginPanel extends CommandPanel {
  public LoginPanelUI ui;
  private MainFrame mf;

  public LoginPanel() {
    super(new LoginPanelUI());
    ui = (LoginPanelUI) super.ui;
  }

  public LoginPanel(MainFrame mf) {
    this();
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if (actionEvent.getActionCommand().equals(LoginActionCommand.LOGIN.str)) {
      if (run()) {
        ui.tfUsername.setText("");
        ui.pfPassword.setText("");
        mf.loginSetup();
      }
    }
  }

  @Override
  public boolean run() {
    String username = ui.tfUsername.getText();
    String password = String.valueOf(ui.pfPassword.getPassword());

    if ((!username.equals("rec") && !username.equals("admin"))  // first login
        && (!InputPatterns.USERNAME.test(username) || !InputPatterns.PASSWORD.test(password))) {
      Logger.warn("Invalid username or password.");
      ui.warn("Invalid username or password.");

      ui.tfUsername.setText("");
      ui.pfPassword.setText("");
      return false;
    }

    User user;
    try {
      user = new UserDao().get(new User(username));

    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
      ui.tfUsername.setText("");
      ui.pfPassword.setText("");
      return false;
    } catch (CouldNotFetchUserException e) {
      Logger.warn(e);
      ui.warn(String.format("Database error: could not fetch user %s.", username));
      ui.tfUsername.setText("");
      ui.pfPassword.setText("");
      return false;
    }

    if (!user.getPassword().equals(password)) {
      Logger.warn("Failed login. Incorrect password for '{}'. Received '{}'.",
          username, user.getPassword(), password);
      ui.warn("Invalid username or password.");
      ui.tfUsername.setText("");
      ui.pfPassword.setText("");
      return false;
    }

    // To find out if user is an admin, use AdminDao.getAll() to get a list
    // of admin user names and check if the user name is among them.

    ActiveUser.set(user);
    Logger.info("Successful login. '{}': User logged in.", username);
    return true;
  }
}

package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import nitrogenhotel.db.dataaccessobjects.AdminDao;
import nitrogenhotel.db.dataaccessobjects.ReceptionDao;
import nitrogenhotel.db.dataaccessobjects.UserDao;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.admin.CouldNotPromoteUserException;
import nitrogenhotel.db.exceptions.admin.InvalidAdminInputException;
import nitrogenhotel.db.exceptions.reception.CouldNotPromoteToReceptionException;
import nitrogenhotel.db.exceptions.reception.InvalidReceptionStaffInputException;
import nitrogenhotel.db.exceptions.user.CouldNotAddUserException;
import nitrogenhotel.db.exceptions.user.CouldNotFetchUserException;
import nitrogenhotel.db.exceptions.user.InvalidUserInputException;
import nitrogenhotel.ui.panels.UserAddPanelUI;
import org.tinylog.Logger;


/** Backend Panel to handle the Room Add logic.*/
public class UserAddPanel extends CommandPanel {
  public UserAddPanelUI ui;
  private MainFrame mf;

  public UserAddPanel() {
    super(new UserAddPanelUI());
    ui = (UserAddPanelUI) super.ui;
  }

  public UserAddPanel(MainFrame mf) {
    this();
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {

      case "add user ok" -> {
        boolean ret = run();
        if (ret) {
          mf.clearCurrentPanel();
          ui.success("User added.");
        }
      }

      case "cancel" -> {
        clearUI();
        mf.clearCurrentPanel();
      }

      default -> Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    String username = ui.getUsername();
    String screenName = ui.getScreenName();
    String role = ui.getSelectedRole();
    String password = ui.getPassword();
    String repeatPassword = ui.getRepeatPassword();
    User newUser;

    if (!InputPatterns.USERNAME.test(username)) {
      Logger.warn("Invalid screen name.");
      ui.warn(InputPatterns.USERNAME.userWarnMsg);
      clearUI();
      return false;
    } else if (!InputPatterns.PASSWORD.test(password)) {
      Logger.warn("Invalid password.");
      ui.warn(InputPatterns.PASSWORD.userWarnMsg);
      clearUI();
      return false;
    } else if (!password.matches(repeatPassword)) {
      Logger.info("Invalid repeated password.");
      ui.warn("Invalid repeated password.");
      clearUI();
      return false;
    }

    newUser = new User(username, screenName, password);

    UserDao userDao = new UserDao();
    try {
      Logger.debug("Fetching user username", newUser.getUserName());
      userDao.get(newUser);
      Logger.debug("User exists");
      ui.warn(String.format("User '%s' already exists.", newUser.getUserName()));
      clearUI();
      return false;
    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
      clearUI();
      return false;
    } catch (CouldNotFetchUserException e) {
      // Move on
    }

    try {
      userDao.add(newUser);
    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
      clearUI();
      return false;
    } catch (CouldNotAddUserException e) {
      Logger.warn(e);
      ui.warn(String.format("Database error: could not add user %s.", newUser.getUserName()));
      clearUI();
      return false;
    }

    ReceptionDao receptionDao = new ReceptionDao();
    AdminDao adminDao = new AdminDao();
    try {
      if (role.equals("Reception Staff")) {
        receptionDao.promoteUserToRecp(newUser);
      }

      if (role.equals("Administrator")) {
        adminDao.promoteExistingUser(newUser);
      }
    } catch (CouldNotPromoteToReceptionException e) {
      Logger.warn(e);
      ui.warn("Database error: could not promote user to reception.");
      clearUI();
      return false;
    } catch (InvalidReceptionStaffInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid input.");
      clearUI();
      return false;
    } catch (CouldNotPromoteUserException e) {
      Logger.warn(e);
      ui.warn("Database error: could not promote user.");
      clearUI();
      return false;
    } catch (InvalidAdminInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid input.");
      clearUI();
      return false;
    }
    clearUI();
    return true;
  }

  private void clearUI() {
    ui.usernameInput.setText("");
    ui.screenNameInput.setText("");
    ui.passwordInput.setText("");
    ui.repeatPasswordInput.setText("");
  }
}
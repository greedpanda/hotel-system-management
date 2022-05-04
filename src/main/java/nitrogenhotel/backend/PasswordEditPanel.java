package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import nitrogenhotel.db.dataaccessobjects.UserDao;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.user.CouldNotFetchUserException;
import nitrogenhotel.db.exceptions.user.CouldNotUpdatePasswordException;
import nitrogenhotel.db.exceptions.user.InvalidUserInputException;
import nitrogenhotel.ui.panels.PasswordEditPanelUI;
import nitrogenhotel.utils.ActiveUser;
import org.tinylog.Logger;

/** Backend password panel responsible for editing password. */
public class PasswordEditPanel extends CommandPanel {
  public PasswordEditPanelUI ui;
  private MainFrame mf;

  public PasswordEditPanel() {
    super(new PasswordEditPanelUI());
    ui = (PasswordEditPanelUI) super.ui;
  }

  public PasswordEditPanel(MainFrame mf) {
    this();
    this.mf = mf;
  }

  private void logic() {
    if (run()) {
      mf.clearCurrentPanel();
      ui.success("Password Changed.");
    }
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {
      case "edit password ok" -> logic();
      case "cancel" -> mf.clearCurrentPanel();
      default -> Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    String currentPassword = ui.getCurrentPassword();
    String newPassword = ui.getNewPasword();
    String confirmNewPassword = ui.getConfirmNewPasword();

    User user = ActiveUser.get();
    UserDao userDao = new UserDao();

    if (!InputPatterns.PASSWORD.test(newPassword)) {
      Logger.warn("Invalid password.");
      ui.warn(InputPatterns.PASSWORD.userWarnMsg);
      clearUI();
      return false;
    } else if (!currentPassword.equals(user.getPassword())) {
      Logger.warn("Password does not match the current user password");
      ui.warn("Invalid current password.");
      clearUI();
      return false;
    } else if (!newPassword.equals(confirmNewPassword)) {
      Logger.warn("New password does not match confirm password");
      ui.warn("Invalid confirmed password");
      clearUI();
      return false;
    }

    try {
      user = userDao.get(new User(user.getUserName()));

    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
      clearUI();
      return false;
    } catch (CouldNotFetchUserException e) {
      Logger.warn(e);
      ui.warn(String.format("Database error: could not fetch user %s.", user.getUserName()));
      clearUI();
      return false;
    }

    try {
      userDao.updatePassword(user, newPassword);
    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
      clearUI();
      return false;
    } catch (CouldNotUpdatePasswordException e) {
      Logger.warn(e);
      ui.warn(String.format("Database error: could not update password to %s.", newPassword));
      clearUI();
      return false;
    }

    try {
      user = userDao.get(new User(user.getUserName()));
    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
      clearUI();
      return false;
    } catch (CouldNotFetchUserException e) {
      Logger.warn(e);
      ui.warn(String.format("Database error: could not fetch user %s.", user.getUserName()));
      clearUI();
      return false;
    }

    if (!user.getPassword().equals(newPassword)) {
      Logger.warn("User '{}' was supposed to have their password be updated to '{}' but it became '{}'.",
              user.getUserName(), newPassword, user.getPassword());
      ui.warn(String.format("Error: password should be %s but it has been changed to %s.",
              newPassword, user.getPassword()));
      clearUI();
      return false;
    }

    Logger.info("Successfully changed password to '{}'.", user.getPassword());
    ActiveUser.get().setPassword(user.getPassword());
    clearUI();
    return true;
  }

  private void clearUI() {
    ui.enterPasswordInput.setText("");
    ui.enterNewPasswordInput.setText("");
    ui.confirmNewPasswordInput.setText("");
  }
}
package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import nitrogenhotel.db.dataaccessobjects.UserDao;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.user.CouldNotFetchUserException;
import nitrogenhotel.db.exceptions.user.CouldNotUpdateScreenNameException;
import nitrogenhotel.db.exceptions.user.InvalidUserInputException;
import nitrogenhotel.ui.panels.ScreenNameEditPanelUI;
import nitrogenhotel.utils.ActiveUser;
import org.tinylog.Logger;

/** Backend screen panel responsible for editing screen name. */
public class ScreenNameEditPanel extends CommandPanel {
  private MainFrame mf;
  public ScreenNameEditPanelUI ui;

  public ScreenNameEditPanel() {
    super(new ScreenNameEditPanelUI());
    ui = (ScreenNameEditPanelUI) super.ui;
  }

  public ScreenNameEditPanel(MainFrame mf) {
    this();
    this.mf = mf;
  }

  private void logic() {
    if (run()) {
      mf.clearCurrentPanel();
      mf.updateScreenNameLabel();
      ui.success("Screen name changed.");
    }
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {
      case "edit screen name ok" -> logic();
      case "cancel" -> mf.clearCurrentPanel();
      default -> Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    String scrname = ui.getScreenName();

    if (!InputPatterns.SCREEN_NAME.test(scrname)) {
      Logger.warn("Invalid screen name.");
      ui.warn(InputPatterns.SCREEN_NAME.userWarnMsg);
      return false;
    }

    User user = ActiveUser.get();
    UserDao userDao = new UserDao();
    try {
      user = userDao.get(new User(user.getUserName()));

    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
    } catch (CouldNotFetchUserException e) {
      Logger.warn(e);
      ui.warn(String.format("Database error: could not fetch user %s.", user.getUserName()));
      return false;
    }

    try {
      userDao.updateScreenName(user, scrname);

    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
      return false;
    } catch (CouldNotUpdateScreenNameException e) {
      Logger.warn(e);
      ui.warn(String.format("Database error: could not update screen name to %s.", scrname));
      return false;
    }

    try {
      user = userDao.get(new User(user.getUserName()));

    } catch (InvalidUserInputException e) {
      Logger.warn(e);
      ui.warn("Database error: invalid user input.");
      return false;
    } catch (CouldNotFetchUserException e) {
      Logger.warn(e);
      ui.warn(String.format("Database error: could not fetch user %s.", user.getUserName()));
      return false;
    }

    if (!user.getScreenName().equals(scrname)) {
      Logger.warn("User '{}' was supposed to have their screen name be updated to '{}' but it became '{}'.",
              user.getUserName(), scrname, user.getScreenName());
      ui.warn(String.format("Error: screen name should be %s but it has been changed to %s.",
              scrname, user.getScreenName()));
      return false;
    }

    Logger.info("Screen name successfully changed to '{}'.", user.getScreenName());
    ActiveUser.get().setScreenName(user.getScreenName());
    clearUI();
    return true;
  }

  private void clearUI() {
    ui.screenNameInput.setText("");
  }
}

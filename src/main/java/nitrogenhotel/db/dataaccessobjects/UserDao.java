package nitrogenhotel.db.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.user.CouldNotAddUserException;
import nitrogenhotel.db.exceptions.user.CouldNotDeleteUserException;
import nitrogenhotel.db.exceptions.user.CouldNotFetchAllUsersException;
import nitrogenhotel.db.exceptions.user.CouldNotFetchUserException;
import nitrogenhotel.db.exceptions.user.CouldNotUpdatePasswordException;
import nitrogenhotel.db.exceptions.user.CouldNotUpdateScreenNameException;
import nitrogenhotel.db.exceptions.user.InvalidUserInputException;
import nitrogenhotel.db.interfaces.BasicDao;
import nitrogenhotel.db.interfaces.IuserDao;

/** This class is used to access data related to user or users in the database. */
public class UserDao implements BasicDao<User>, IuserDao {

  /**
   * Fetches all the users available in the database.
   *
   * @return It returns a list containing all the User objects in the database.
   * @throws CouldNotFetchAllUsersException for errors while fetching a list of users.
   */
  @Override
  public List<User> getAll() throws CouldNotFetchAllUsersException {
    Connection conn = DataBaseManager.getConnection();
    List<User> allUsers = new ArrayList<User>();
    User user;

    String queryString = "SELECT * FROM Users;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      ResultSet result = preparedStmnt.executeQuery();

      while (result.next()) {
        user =
            new User(
                result.getString("userName"),
                result.getString("screenName"),
                result.getString("password"));
        allUsers.add(user);
      }
      result.close();
      return allUsers;
    } catch (SQLException e) {
      throw new CouldNotFetchAllUsersException(
          "There was an error while fetching the list of users available in the database.", e);
    }
  }

  /**
   * Returns a user from the database.
   *
   * @param obj the name of the User object to be retrieved from the database.
   * @return It returns an User object.
   * @throws InvalidUserInputException for user input error.
   * @throws CouldNotFetchUserException for errors while fetching a specific user.
   */
  @Override
  public User get(User obj) throws InvalidUserInputException, CouldNotFetchUserException {

    if (obj == null || obj.getUserName() == null) {
      throw new InvalidUserInputException(
          "The user must not be null and a username must be provided");
    }

    Connection conn = DataBaseManager.getConnection();
    User user;

    String queryString = "SELECT * FROM Users WHERE userName=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, obj.getUserName());
      ResultSet result = preparedStmnt.executeQuery();
      result.next();
      user =
          new User(
              result.getString("userName"),
              result.getString("screenName"),
              result.getString("password"));

      result.close();
      return user;
    } catch (SQLException e) {
      throw new CouldNotFetchUserException(
          "There was an error while fetching the user:" + " " + obj.getUserName(), e);
    }
  }

  /**
   * Deletes a User from the database.
   *
   * @param obj the name of the User object to be deleted from the database.
   * @throws InvalidUserInputException for user input error.
   * @throws CouldNotDeleteUserException for errors during user deletion.
   */
  @Override
  public void delete(User obj) throws InvalidUserInputException, CouldNotDeleteUserException {

    if (obj == null || obj.getUserName() == null) {
      throw new InvalidUserInputException(
          "The user must not be null and a username must be provided");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM Users WHERE userName=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, obj.getUserName());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotDeleteUserException(
          "There was an error while deleting the user:" + " " + obj.getUserName(), e);
    }
  }

  /**
   * Adds a user to the database.
   *
   * @param obj the name of the User object to be added in the database.
   * @throws InvalidUserInputException for user input error.
   * @throws CouldNotAddUserException for errors during user addition.
   */
  @Override
  public void add(User obj) throws InvalidUserInputException, CouldNotAddUserException {

    if (obj == null
        || obj.getUserName() == null
        || obj.getScreenName() == null
        || obj.getPassword() == null) {
      throw new InvalidUserInputException(
          "The user must not be null and a Username, ScreenName and Password must be provided");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "INSERT INTO Users(userName, screenName, password) VALUES(?,?,?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, obj.getUserName());
      preparedStmnt.setString(2, obj.getScreenName());
      preparedStmnt.setString(3, obj.getPassword());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotAddUserException(
          "There was an error while adding the user:" + " " + obj.getUserName(), e);
    }
  }

  /**
   * Updates user's screenName in the database.
   *
   * @param obj the name of the User object to be retrieved in the database.
   * @param screenName name of the updated screenName.
   * @return false if the user does not exist in the database.
   * @throws InvalidUserInputException for user input error.
   * @throws CouldNotUpdateScreenNameException for errors during updating ScreenName.
   */
  @Override
  public boolean updateScreenName(User obj, String screenName)
      throws InvalidUserInputException, CouldNotUpdateScreenNameException {

    if (obj == null || obj.getUserName() == null || screenName == null) {
      throw new InvalidUserInputException(
          "The user must not be null and a UserName as well a new screenName must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Users SET screenName = (?) WHERE userName =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, screenName);
      preparedStmnt.setString(2, obj.getUserName());
      if (obj.getUserName() != null) {
        preparedStmnt.executeUpdate();
        return true;
      }
      return false;
    } catch (SQLException e) {
      throw new CouldNotUpdateScreenNameException(
          "There was an error while updating screenName for the user:" + " " + obj.getUserName(), e);
    }
  }

  /**
   * Updates user's password in the database.
   *
   * @param obj the name of the User object to be retrieved in the database.
   * @param password name of the updated password.
   * @return false if the user does not exist in the database.
   * @throws InvalidUserInputException for user input error.
   * @throws CouldNotUpdatePasswordException for errors during updating password.
   */
  @Override
  public boolean updatePassword(User obj, String password)
      throws InvalidUserInputException, CouldNotUpdatePasswordException {

    if (obj == null || obj.getUserName() == null || password == null) {
      throw new InvalidUserInputException(
          "The user must not be null and a UserName as well as a password must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Users SET password = (?) WHERE userName=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, password);
      preparedStmnt.setString(2, obj.getUserName());
      if (obj.getUserName() != null) {
        preparedStmnt.executeUpdate();
        return true;
      }
      return false;
    } catch (SQLException e) {
      throw new CouldNotUpdatePasswordException(
          "There was an error while updating password of the user:" + " " + obj.getUserName(), e);
    }
  }
}

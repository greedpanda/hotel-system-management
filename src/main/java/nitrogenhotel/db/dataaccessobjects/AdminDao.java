package nitrogenhotel.db.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.entries.Admin;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.admin.CouldNotAddAdminException;
import nitrogenhotel.db.exceptions.admin.CouldNotDeleteAdminException;
import nitrogenhotel.db.exceptions.admin.CouldNotDemoteAdminException;
import nitrogenhotel.db.exceptions.admin.CouldNotFetchAdminException;
import nitrogenhotel.db.exceptions.admin.CouldNotFetchAllAdminsException;
import nitrogenhotel.db.exceptions.admin.CouldNotPromoteUserException;
import nitrogenhotel.db.exceptions.admin.InvalidAdminInputException;
import nitrogenhotel.db.exceptions.user.CouldNotAddUserException;
import nitrogenhotel.db.exceptions.user.CouldNotDeleteUserException;
import nitrogenhotel.db.exceptions.user.CouldNotFetchUserException;
import nitrogenhotel.db.exceptions.user.InvalidUserInputException;
import nitrogenhotel.db.interfaces.BasicDao;
import nitrogenhotel.db.interfaces.IadminDao;

/** This class is used to access data related to admin or admins in the database. */
public class AdminDao implements BasicDao<Admin>, IadminDao {

  /**
   * Fetches all the admins available in the database.
   *
   * @return It returns a list containing all the Admin objects in the database.
   * @throws CouldNotFetchAllAdminsException thrown for errors while fetching a list of admins.
   */
  @Override
  public List<Admin> getAll() throws CouldNotFetchAllAdminsException {
    Connection conn = DataBaseManager.getConnection();
    List<Admin> allAdmins = new ArrayList<Admin>();
    Admin admin;

    String queryString = "SELECT * FROM AdminV;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        admin =
                new Admin(
                        result.getString("userName"),
                        result.getString("screenName"),
                        result.getString("password"));
        allAdmins.add(admin);
      }
      result.close();
      return allAdmins;
    } catch (SQLException e) {
      throw new CouldNotFetchAllAdminsException(
              "There was an error while fetching the list of admins available in the database.", e);
    }
  }

  /**
   * Returns an admin from the database.
   *
   * @param obj name of an admin to be retrieved from the database.
   * @return It returns Admin object.
   * @throws InvalidAdminInputException  thrown when an invalid admin or a wrong username is given.
   * @throws CouldNotFetchAdminException thrown when an error occurs while fetching an admin.
   */
  @Override
  public Admin get(Admin obj)
          throws InvalidAdminInputException, CouldNotFetchAdminException {

    if (obj == null || obj.getUserName() == null) {
      throw new InvalidAdminInputException(
              "The admin must not be null and a username of an admin must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();
    Admin admin;

    String queryString = "SELECT * FROM AdminV WHERE userName=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, obj.getUserName());
      ResultSet result = preparedStmnt.executeQuery();
      result.next();
      admin =
              new Admin(
                      result.getString("userName"),
                      result.getString("screenName"),
                      result.getString("password"));

      result.close();
      return admin;
    } catch (SQLException e) {
      throw new CouldNotFetchAdminException(
              "There was an error while fetching the admin " + " " + obj.getUserName(), e);
    }
  }

  /**
   * Deletes an admin completely from the database. The user won't exist anymore in the database.
   * There's another method to demote admins to users.
   *
   * @param obj name of the administrator to be deleted from the database.
   * @throws InvalidAdminInputException   thrown when an invalid admin or a wrong username is given.
   * @throws CouldNotDeleteAdminException thrown when an error occurs while deleting the admin.
   */
  @Override
  public void delete(Admin obj)
          throws InvalidAdminInputException, CouldNotDeleteAdminException {

    if (obj == null || obj.getUserName() == null) {
      throw new InvalidAdminInputException("The admin and a username must not be null.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM Administrator WHERE username=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, obj.getUserName());
      preparedStmnt.executeUpdate();

      UserDao userDao = new UserDao();
      userDao.delete(obj);

    } catch (SQLException | InvalidUserInputException | CouldNotDeleteUserException e) {
      throw new CouldNotDeleteAdminException(
              "There was an error while removing the admin" + " " + obj.getUserName(), e);
    }
  }

  /**
   * Adds a new user and promotes it to Administrator.
   *
   * @param obj name of an administrator to be added.
   * @throws InvalidAdminInputException thrown when an invalid admin is given.
   * @throws CouldNotAddAdminException  thrown when an error occurs while adding an admin.
   */
  @Override
  public void add(Admin obj)
          throws InvalidAdminInputException, CouldNotAddAdminException {

    if (obj == null
            || obj.getUserName() == null
            || obj.getScreenName() == null
            || obj.getPassword() == null) {
      throw new InvalidAdminInputException(
              "You need to provide all the attributes for a normal user for the admin.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "INSERT INTO Administrator(userName) VALUES(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      // First add the new user
      UserDao userDao = new UserDao();
      userDao.add(obj);

      // Second promote user to admin.
      preparedStmnt.setString(1, obj.getUserName());
      preparedStmnt.executeUpdate();

    } catch (SQLException | InvalidUserInputException | CouldNotAddUserException e) {
      throw new CouldNotAddAdminException(
              "There was an error while adding the Admin" + " " + obj.getUserName(), e);
    }
  }

  /**
   * Promotes an existing User to an Administrator.
   *
   * @param obj name of an Administrator.
   * @throws InvalidAdminInputException   thrown when an invalid admin is given or certain attributes
   *                                      are not provided.
   * @throws CouldNotPromoteUserException thrown when an error occurs while promoting an existing
   *                                      user to an admin.
   */
  @Override
  public void promoteExistingUser(User obj)
          throws InvalidAdminInputException, CouldNotPromoteUserException {

    if (obj == null || obj.getUserName() == null) {
      throw new InvalidAdminInputException(
              "The admin must not be null, and a userName must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "INSERT INTO Administrator(userName) VALUES(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      UserDao userDao = new UserDao();
      userDao.get(obj);

      preparedStmnt.setString(1, obj.getUserName());
      preparedStmnt.executeUpdate();

    } catch (SQLException | InvalidUserInputException | CouldNotFetchUserException e) {
      throw new CouldNotPromoteUserException(
              "There was an error while promoting a User to an Admin" + " " + obj.getUserName(), e);
    }
  }

  /**
   * Demotes an existing admin to a user by deleting it only from the administrator table.
   *
   * @param obj name of an Administrator.
   * @throws InvalidAdminInputException   thrown when an invalid admin is given or certain attributes
   *                                      are not provided.
   * @throws CouldNotDemoteAdminException thrown when an error occurs while demoting an existing
   *                                      admin to a user.
   */
  @Override
  public void demoteAdminToUser(User obj)
          throws InvalidAdminInputException, CouldNotDemoteAdminException {

    if (obj == null || obj.getUserName() == null) {
      throw new InvalidAdminInputException(
              "The admin must not be null, and a userName must be provided.");
    }
    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM Administrator WHERE username=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, obj.getUserName());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotDemoteAdminException(
              "There was an error while demoting an Admin to a User" + " " + obj.getUserName(), e);
    }
  }
}

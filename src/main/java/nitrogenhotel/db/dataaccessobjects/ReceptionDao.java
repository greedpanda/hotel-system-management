package nitrogenhotel.db.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.entries.ReceptionStaff;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.reception.CouldNotAddReceptionStaffException;
import nitrogenhotel.db.exceptions.reception.CouldNotDeleteReceptionStaffException;
import nitrogenhotel.db.exceptions.reception.CouldNotDemoteReceptionStaffException;
import nitrogenhotel.db.exceptions.reception.CouldNotFetchAllReceptionStaffException;
import nitrogenhotel.db.exceptions.reception.CouldNotFetchReceptionStaffException;
import nitrogenhotel.db.exceptions.reception.CouldNotPromoteToReceptionException;
import nitrogenhotel.db.exceptions.reception.InvalidReceptionStaffInputException;
import nitrogenhotel.db.exceptions.user.CouldNotAddUserException;
import nitrogenhotel.db.exceptions.user.CouldNotDeleteUserException;
import nitrogenhotel.db.exceptions.user.InvalidUserInputException;
import nitrogenhotel.db.interfaces.BasicDao;
import nitrogenhotel.db.interfaces.IreceptionDao;

/**
 * Represents the reception staff table in the database.
 */
public class ReceptionDao implements BasicDao<ReceptionStaff>, IreceptionDao {

  /**
   * Fetches all the reception staff from the database.
   *
   * @return a list of all the reception staff.
   * @throws CouldNotFetchAllReceptionStaffException thrown for errors while fetching a list of
   *                                                 reception staff.
   */
  @Override
  public List<ReceptionStaff> getAll() throws CouldNotFetchAllReceptionStaffException {
    Connection conn = DataBaseManager.getConnection();
    List<ReceptionStaff> allReceptionStaff = new ArrayList<ReceptionStaff>();
    ReceptionStaff receptionStaff;

    String queryString = "SELECT * FROM ReceptionV;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        receptionStaff =
            new ReceptionStaff(
                result.getString("userName"),
                result.getString("screenName"),
                result.getString("password"));
        allReceptionStaff.add(receptionStaff);
      }
      result.close();
      return allReceptionStaff;
    } catch (SQLException e) {
      throw new CouldNotFetchAllReceptionStaffException(
          "There was an error while fetching the list of reception staff available in the database.",
          e);
    }
  }

  /**
   * Fetches the specified reception staff.
   *
   * @param obj the reception staff to get.
   * @return a ReceptionStaff object with the specified person's information.
   * @throws InvalidReceptionStaffInputException  thrown when an invalid reception staff or a wrong
   *                                              username is given.
   * @throws CouldNotFetchReceptionStaffException thrown when an error occurs while fetching an
   *                                              reception staff.
   */
  @Override
  public ReceptionStaff get(ReceptionStaff obj)
      throws InvalidReceptionStaffInputException, CouldNotFetchReceptionStaffException {

    if (obj == null || obj.getUserName() == null) {
      throw new InvalidReceptionStaffInputException(
          "The reception staff must not be null and a username of an reception staff must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();
    ReceptionStaff receptionStaff;

    String queryString = "SELECT * FROM ReceptionV WHERE userName=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, obj.getUserName());
      ResultSet result = preparedStmnt.executeQuery();
      result.next();
      receptionStaff =
          new ReceptionStaff(
              result.getString("userName"),
              result.getString("screenName"),
              result.getString("password"));

      result.close();
      return receptionStaff;
    } catch (SQLException e) {
      throw new CouldNotFetchReceptionStaffException(
          "There was an error while fetching the reception staff " + obj.getUserName(), e);
    }
  }

  /**
   * Deletes a reception staff completely from the database.
   *
   * @param obj The reception staff to be deleted.
   * @throws InvalidReceptionStaffInputException thrown when an invalid reception staff
   *                                             or a wrong username is given.
   * @throws CouldNotDeleteReceptionStaffException thrown when an error occurs while deleting an
   *                                               reception staff.
   */
  @Override
    public void delete(ReceptionStaff obj)
          throws InvalidReceptionStaffInputException, CouldNotDeleteReceptionStaffException {

    if (obj == null || obj.getUserName() == null) {
      throw new InvalidReceptionStaffInputException("The reception staff and a username must not be null.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM ReceptionStaff WHERE username=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, obj.getUserName());
      preparedStmnt.executeUpdate();

      UserDao userDao = new UserDao();
      userDao.delete(obj);

    } catch (SQLException | InvalidUserInputException | CouldNotDeleteUserException e) {
      throw new CouldNotDeleteReceptionStaffException(
              "There was an error while deleting reception the reception staff" + " " + obj.getUserName(), e);
    }
  }

  /**
   * Adds a new reception staff to the database.
   *
   * @param obj The new reception staff to add.
   * @throws InvalidReceptionStaffInputException thrown when an invalid reception staff
   *                                             is given.
   * @throws CouldNotAddReceptionStaffException thrown when an error occurs while adding an
   *                                            reception staff.
   */
  @Override
  public void add(ReceptionStaff obj)
      throws InvalidReceptionStaffInputException, CouldNotAddReceptionStaffException {

    if (obj == null
            || obj.getUserName() == null
            || obj.getScreenName() == null
            || obj.getPassword() == null) {
      throw new InvalidReceptionStaffInputException(
              "You need to provide all the attributes for a normal user for the reception staff.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "INSERT INTO ReceptionStaff(userName) VALUES(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      // First add the new user
      UserDao userDao = new UserDao();
      userDao.add(obj);

      // Second promote user to reception staff.
      preparedStmnt.setString(1, obj.getUserName());
      preparedStmnt.executeUpdate();

    } catch (SQLException | InvalidUserInputException | CouldNotAddUserException e) {
      throw new CouldNotAddReceptionStaffException(
              "There was an error while adding the reception staff " + obj.getUserName(), e);
    }
  }

  /**
   * Promotes an existing user to be a reception staff.
   *
   * @param us The existing user to be promoted.
   * @throws InvalidReceptionStaffInputException thrown when an invalid reception staff
   *                                             or a wrong username is given.
   * @throws CouldNotPromoteToReceptionException thrown when an error occurs while promoting a
   *                                             user.
   */
  @Override
  public void promoteUserToRecp(User us)
      throws InvalidReceptionStaffInputException, CouldNotPromoteToReceptionException {

    if (us == null || us.getUserName() == null) {
      throw new InvalidReceptionStaffInputException(
          "The reception staff must not be null and a username of an reception staff must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "INSERT INTO ReceptionStaff (userName) VALUES (?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, us.getUserName());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotPromoteToReceptionException(
          "There was an error while promoting the reception staff " + us.getUserName(), e);
    }
  }

  /**
   * Demotes an existing reception staff to be a normal user.
   *
   * @param us Reception staff to be demoted.
   * @throws InvalidReceptionStaffInputException thrown when an invalid reception staff
   *                                             or a wrong username is given.
   * @throws CouldNotDemoteReceptionStaffException thrown when an error occurs while demoting a
   *                                               user.
   */
  @Override
  public void demoteUserFromRecp(User us)
      throws InvalidReceptionStaffInputException, CouldNotDemoteReceptionStaffException {

    if (us == null || us.getUserName() == null) {
      throw new InvalidReceptionStaffInputException(
          "The reception staff must not be null and a username of an reception staff must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM ReceptionStaff WHERE userName=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, us.getUserName());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotDemoteReceptionStaffException(
          "There was an error while demoting the reception staff " + us.getUserName(), e);
    }
  }
}

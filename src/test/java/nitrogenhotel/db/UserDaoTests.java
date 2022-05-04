package nitrogenhotel.db;

import java.util.List;
import nitrogenhotel.db.dataaccessobjects.UserDao;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.user.CouldNotAddUserException;
import nitrogenhotel.db.exceptions.user.CouldNotDeleteUserException;
import nitrogenhotel.db.exceptions.user.CouldNotFetchAllUsersException;
import nitrogenhotel.db.exceptions.user.CouldNotFetchUserException;
import nitrogenhotel.db.exceptions.user.CouldNotUpdatePasswordException;
import nitrogenhotel.db.exceptions.user.CouldNotUpdateScreenNameException;
import nitrogenhotel.db.exceptions.user.InvalidUserInputException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTests extends dbTest {

  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllUsersException {
      UserDao userDao = new UserDao();
      List<User> allUsers = userDao.getAll();

      assertFalse(allUsers.isEmpty());
    }

    @Test
    public void getAll_ShouldReturnListContainingTheUsersInDB() throws CouldNotFetchAllUsersException {
      UserDao userDao = new UserDao();
      List<User> allUsers = userDao.getAll();

      assertTrue(
              allUsers.stream()
                      .anyMatch(
                              user ->
                                      user.getUserName().equals("ha223cz")
                                              && user.getScreenName().equals("sam")
                                              && user.getPassword().equals("123")));
      assertTrue(
              allUsers.stream()
                      .anyMatch(
                              user ->
                                      user.getUserName().equals("mg223uk")
                                              && user.getScreenName().equals("Mario")
                                              && user.getPassword().equals("456")));
      assertTrue(
              allUsers.stream()
                      .anyMatch(
                              user ->
                                      user.getUserName().equals("oz222aw")
                                              && user.getScreenName().equals("Olga")
                                              && user.getPassword().equals("789")));
    }
  }

  @Nested
  public class getMethodTests {

    @Test
    public void get_UserAsParameter_ShouldReturnSpecifiedUserFromDB()
            throws CouldNotFetchUserException, InvalidUserInputException {

      UserDao userDao = new UserDao();
      User user = new User("mg223uk");
      User user1 = userDao.get(user);

      assertAll(() -> assertEquals(user1.getUserName(), "mg223uk"),
                () -> assertEquals(user1.getScreenName(), "Mario"),
                () -> assertEquals(user1.getPassword(), "456"));
    }

    @Test
    public void get_NullAsParameter_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = null;

      assertThrows(InvalidUserInputException.class, () -> userDao.get(user));
    }

    @Test
    public void get_UserWithNullUserNameAsParameter_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User(null);

      assertThrows(InvalidUserInputException.class, () -> userDao.get(user));
    }

    @Test
    public void get_NoneExistingUserInDbAsParameter_ShouldThrowCouldNotFetchUserException() {
      UserDao userDao = new UserDao();
      User user = new User("gg445uk");

      assertThrows(CouldNotFetchUserException.class, () -> userDao.get(user));
    }
  }

  @Nested
  public class deleteMethodTests {

    @Test
    public void delete_UserAsParameter_ShouldThrowCouldNotFetchUserExceptionWhenFetchingDeletedUser()
            throws InvalidUserInputException, CouldNotDeleteUserException,
            CouldNotAddUserException {

      UserDao userDao = new UserDao();
      User user = new User("mg223uk", "Mario", "456");
      userDao.delete(user);

      assertThrows(CouldNotFetchUserException.class, () -> userDao.get(new User("mg223uk")));

      userDao.add(user);
    }

    @Test
    public void delete_UserWithNullUserNameAsParameter_ShouldThrowInvalidUserInputException() {
      UserDao ud = new UserDao();
      User dummy = new User(null, "Dummy", "101");

      assertThrows(InvalidUserInputException.class, () -> ud.delete(dummy));
    }

    @Test
    public void delete_NoneExistingUserInDbAsParameter_ShouldNotThrowException() {
      UserDao userDao = new UserDao();
      User user = new User("gg445uk", "Laxis", "123");

      assertDoesNotThrow(() -> userDao.delete(user));
    }
  }

  @Nested
  public class addMethodTests {

    @Test
    public void add_UserAsParameter_ShouldReturnSpecifiedUserFromDbAfterAddingIt()
            throws InvalidUserInputException, CouldNotAddUserException, CouldNotFetchUserException,
            CouldNotDeleteUserException {

      UserDao userDao = new UserDao();
      User user = new User("du223my", "Dummy", "101");
      userDao.add(user);
      User user1 = userDao.get(user);

      assertAll(() -> assertEquals(user1.getUserName(), "du223my"),
                () -> assertEquals(user1.getScreenName(), "Dummy"),
                () -> assertEquals(user1.getPassword(), "101"));

      userDao.delete(user);
    }

    @Test
    public void add_NullAsParameter_shouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = null;

      assertThrows(InvalidUserInputException.class, () -> userDao.add(user));
    }

    @Test
    public void add_UserWithNullUserNameAsParameter_shouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User(null, "Dummy", "101");

      assertThrows(InvalidUserInputException.class, () -> userDao.add(user));
    }

    @Test
    public void add_UserWithNullScreenNameAsParameter_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User("du223my", null, "101");

      assertThrows(InvalidUserInputException.class, () -> userDao.add(user));
    }

    @Test
    public void add_UserWithNullPasswordAsParameter_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User("du223my", "Dummy", null);

      assertThrows(InvalidUserInputException.class, () -> userDao.add(user));
    }

    @Test
    public void add_UserWithOnlyUserNameAsParameter_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User("du223my");

      assertThrows(InvalidUserInputException.class, () -> userDao.add(user));
    }

    @Test
    public void add_ExistingUserInDbAsParameter_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User("mg223uk", "Dummy", "101");

      assertThrows(CouldNotAddUserException.class, () -> userDao.add(user));
    }
  }

  @Nested
  public class updateScreenNameMethodTests {

    @Test
    public void updateScreenName_UserAs1parameterStringAs2_ShouldUpdateUserScreenNameInDB()
            throws InvalidUserInputException, CouldNotUpdateScreenNameException, CouldNotFetchUserException {

      boolean val;
      UserDao userDao = new UserDao();
      User user = new User("oz222aw");
      val = userDao.updateScreenName(user, "Derry");

      assertTrue(val);
      assertEquals(userDao.get(user).getScreenName(), "Derry");

      userDao.updateScreenName(user, "Olga");
    }

    @Test
    public void updateScreenName_NoneExistingUserInDbAs1parameterStringAs2_ShouldNotThrowException() {
      UserDao userDao = new UserDao();
      User user = new User("gg445uk");

      assertDoesNotThrow(() -> userDao.updateScreenName(user, "Garry"));
    }

    @Test
    public void updateScreenName_NullAs1parameterStringAs2_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User(null);

      assertThrows(InvalidUserInputException.class, () -> userDao.updateScreenName(user, "Garry"));
    }

    @Test
    public void updateScreenName_UserAs1parameterNullAs2_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User("dummy223");

      assertThrows(InvalidUserInputException.class, () -> userDao.updateScreenName(user, null));
    }
  }

  @Nested
  public class updatePasswordMethodTests {

    @Test
    public void updatePassword_UserAs1parameterStringAs2_ShouldUpdateUserPasswordInDB()
            throws InvalidUserInputException, CouldNotUpdatePasswordException, CouldNotFetchUserException {

      boolean val1;
      UserDao userDao = new UserDao();
      User user = new User("oz222aw");
      val1 = userDao.updatePassword(user, "455");

      assertTrue(val1);
      assertEquals(userDao.get(user).getPassword(), "455");

      userDao.updatePassword(user, "789");
    }

    @Test
    public void updatePassword_NoneExistingUserInDbAs1parameterStringAs2_ShouldNotThrowException() {
      UserDao userDao = new UserDao();
      User user = new User("gg445uk");

      assertDoesNotThrow(() -> userDao.updatePassword(user, "123"));
    }

    @Test
    public void updatePassword_NullAs1parameterStringAs2_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User(null);

      assertThrows(InvalidUserInputException.class, () -> userDao.updatePassword(user, "123"));
    }

    @Test
    public void updatePassword_UserAs1parameterNullAs2_ShouldThrowInvalidUserInputException() {
      UserDao userDao = new UserDao();
      User user = new User("gg445uk");

      assertThrows(InvalidUserInputException.class, () -> userDao.updatePassword(user, null));
    }
  }
}

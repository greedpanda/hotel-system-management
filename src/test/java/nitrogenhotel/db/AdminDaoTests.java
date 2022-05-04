package nitrogenhotel.db;

import java.util.List;
import nitrogenhotel.db.dataaccessobjects.AdminDao;
import nitrogenhotel.db.dataaccessobjects.UserDao;
import nitrogenhotel.db.entries.Admin;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.admin.CouldNotAddAdminException;
import nitrogenhotel.db.exceptions.admin.CouldNotDeleteAdminException;
import nitrogenhotel.db.exceptions.admin.CouldNotDemoteAdminException;
import nitrogenhotel.db.exceptions.admin.CouldNotFetchAdminException;
import nitrogenhotel.db.exceptions.admin.CouldNotFetchAllAdminsException;
import nitrogenhotel.db.exceptions.admin.CouldNotPromoteUserException;
import nitrogenhotel.db.exceptions.admin.InvalidAdminInputException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AdminDaoTests extends dbTest {

  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllAdminsException {
      AdminDao adminDao = new AdminDao();
      List<Admin> allAdmins = adminDao.getAll();

      assertFalse(allAdmins.isEmpty());
    }

    @Test
    public void getAll_ShouldReturnListContainingTheAdminsInDB() throws CouldNotFetchAllAdminsException {
      AdminDao adminDao = new AdminDao();
      List<Admin> allAdmins = adminDao.getAll();

      assertTrue(
              allAdmins.stream()
                      .anyMatch(
                              admin ->
                                      admin.getUserName().equals("ha223cz")
                                              && admin.getScreenName().equals("sam")
                                              && admin.getPassword().equals("123")));
      assertTrue(
              allAdmins.stream()
                      .anyMatch(
                              admin ->
                                      admin.getUserName().equals("oz222aw")
                                              && admin.getScreenName().equals("Olga")
                                              && admin.getPassword().equals("789")));
    }
  }

  @Nested
  public class getMethodTests {

    @Test
    public void get_AdminAsParameter_ShouldReturnSpecifiedAdminFromDB()
            throws InvalidAdminInputException, CouldNotFetchAdminException {

      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin("oz222aw");
      Admin admin1 = adminDao.get(admin);

      assertAll(() -> assertEquals(admin1.getUserName(), "oz222aw"),
                () ->  assertEquals(admin1.getScreenName(), "Olga"),
                () -> assertEquals(admin1.getPassword(), "789"));
    }

    @Test
    public void get_NullAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      Admin admin = null;

      assertThrows(InvalidAdminInputException.class, () -> adminDao.get(admin));
    }

    @Test
    public void get_AdminWithNullUserNameAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin(null);

      assertThrows(InvalidAdminInputException.class, () -> adminDao.get(admin));
    }

    @Test
    public void get_NoneExistingAdminInDbAsParameter_ShouldThrowCouldNotFetchAdminException() {
      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin("du223my");

      assertThrows(CouldNotFetchAdminException.class, () -> adminDao.get(admin));
    }
  }

  @Nested
  public class deleteMethodTests {

    @Test
    public void delete_AdminAsParameter_ShouldThrowCouldNotFetchAdminExceptionWhenFetchingDeletedAdmin()
            throws InvalidAdminInputException, CouldNotDeleteAdminException, CouldNotAddAdminException {

      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin("ha223cz", "sam", "123");
      adminDao.delete(admin);

      assertThrows(CouldNotFetchAdminException.class, () -> adminDao.get(new Admin("ha223cz")));

      adminDao.add(admin);
    }

    @Test
    public void delete_NullAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      Admin admin = null;

      assertThrows(InvalidAdminInputException.class, () -> adminDao.delete(admin));
    }

    @Test
    public void delete_AdminWithNullUserNameAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin(null);

      assertThrows(InvalidAdminInputException.class, () -> adminDao.delete(admin));
    }

    @Test
    public void delete_NoneExistingAdminInDbAsParameter_ShouldNotThrowException() {

      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin("Test", "test", "test");

      assertDoesNotThrow(() -> adminDao.delete(admin));
    }
  }

  @Nested
  public class addMethodTests {

    @Test
    public void add_AdminAsParameter_ShouldReturnSpecifiedAdminFromDbAfterAddingIt()
            throws InvalidAdminInputException, CouldNotAddAdminException, CouldNotFetchAdminException,
            CouldNotDeleteAdminException {

      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin("xy66xz", "admin", "admin");
      adminDao.add(admin);
      Admin test = adminDao.get(new Admin("xy66xz"));

      assertAll(() -> assertEquals(test.getUserName(), "xy66xz"),
                () -> assertEquals(test.getScreenName(), "admin"),
                () -> assertEquals(test.getPassword(), "admin"));

      adminDao.delete(admin);
    }

    @Test
    public void add_NullAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();

      assertThrows(InvalidAdminInputException.class, () -> adminDao.add(null));
    }

    @Test
    public void add_AdminWithNullUserNameAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin(null);

      assertThrows(InvalidAdminInputException.class, () -> adminDao.add(admin));
    }

    @Test
    public void add_ExistingAdminInDbAsParameter_ShouldThrowCouldNotAddAdminException() {
      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin("oz222aw", "Olga", "123");

      assertThrows(CouldNotAddAdminException.class, () -> adminDao.add(admin));
    }
  }

  @Nested
  public class promoteExistingUserMethodTests {

    @Test
    public void promoteExistingUser_UserAsParameter_ShouldPromoteSpecifiedUserToAdmin()
            throws CouldNotPromoteUserException, InvalidAdminInputException, CouldNotFetchAdminException,
            CouldNotDemoteAdminException {

      AdminDao adminDao = new AdminDao();
      User user = new User("al225nn", "Alija", "222");
      adminDao.promoteExistingUser(user);
      Admin admin = adminDao.get(new Admin("al225nn"));

      assertAll(() -> assertEquals(admin.getUserName(), "al225nn"),
                () -> assertEquals(admin.getScreenName(), "Alija"),
                () -> assertEquals(admin.getPassword(), "222"));

      adminDao.demoteAdminToUser(user);
    }

    @Test
    public void promoteExistingUser_NullAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      User user = null;

      assertThrows(InvalidAdminInputException.class, () -> adminDao.promoteExistingUser(user));
    }

    @Test
    public void promoteExistingUser_UserWithNullUserNameAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      User user = new User(null);

      assertThrows(InvalidAdminInputException.class, () -> adminDao.promoteExistingUser(user));
    }

    @Test
    public void promoteExistingUser_UserAlreadyAdminAsParameter_ShouldThrowCouldNotPromoteUserException() {
      AdminDao adminDao = new AdminDao();
      User user = new User("oz222aw", "Olga", "123");

      assertThrows(CouldNotPromoteUserException.class, () -> adminDao.promoteExistingUser(user));
    }
  }

  @Nested
  public class demoteAdminToUserMethodTests {

    @Test
    public void demoteAdminToUser_UserAsParameter_ShouldDemoteSpecifiedAdminToUser()
            throws InvalidAdminInputException, CouldNotDemoteAdminException, CouldNotPromoteUserException {
      AdminDao adminDao = new AdminDao();
      Admin admin = new Admin("pm222py", "Paolo", "098");
      UserDao userDao = new UserDao();
      adminDao.demoteAdminToUser(admin);

      assertThrows(CouldNotFetchAdminException.class, () -> adminDao.get(admin));
      assertDoesNotThrow(() -> userDao.get(admin));

      adminDao.promoteExistingUser(admin);
    }

    @Test
    public void demoteAdminToUser_NullAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      User user = null;

      assertThrows(InvalidAdminInputException.class, () -> adminDao.demoteAdminToUser(user));
    }

    @Test
    public void demoteAdminToUser_UserWithNullUserNameAsParameter_ShouldThrowInvalidAdminInputException() {
      AdminDao adminDao = new AdminDao();
      User user = new User(null);

      assertThrows(InvalidAdminInputException.class, () -> adminDao.demoteAdminToUser(user));
    }

    @Test
    public void demoteAdminToUser_NoneExistingUserAsParameter_ShouldNotThrowException() {
      AdminDao adminDao = new AdminDao();
      User user = new User("666");

      assertDoesNotThrow(() -> adminDao.demoteAdminToUser(user));
    }

    @Test
    public void demoteAdminToUser_NoneAdminUserAsParameter_ShouldNotThrowException() {
      AdminDao adminDao = new AdminDao();
      User user = new User("mg223uk");

      assertDoesNotThrow(() -> adminDao.demoteAdminToUser(user));
    }
  }
}

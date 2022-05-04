package nitrogenhotel.db;

import java.util.List;
import nitrogenhotel.db.dataaccessobjects.ReceptionDao;
import nitrogenhotel.db.entries.ReceptionStaff;
import nitrogenhotel.db.entries.User;
import nitrogenhotel.db.exceptions.reception.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReceptionDaoTests extends dbTest {

  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllReceptionStaffException {
      ReceptionDao receptionDao = new ReceptionDao();
      List<ReceptionStaff> allReceptionStaff = receptionDao.getAll();

      assertFalse(allReceptionStaff.isEmpty());
    }

    @Test
    public void getAll_ShouldReturnListContainingTheReceptionStaffInDB()
            throws CouldNotFetchAllReceptionStaffException {

      ReceptionDao receptionDao = new ReceptionDao();
      List<ReceptionStaff> allReceptionStaff = receptionDao.getAll();

      assertTrue(
              allReceptionStaff.stream()
                      .anyMatch(
                              reception ->
                                      reception.getUserName().equals("gk222hi")
                                              && reception.getScreenName().equals("Georgios")
                                              && reception.getPassword().equals("101")));
      assertTrue(
              allReceptionStaff.stream()
                      .anyMatch(
                              reception ->
                                      reception.getUserName().equals("ru222gu")
                                              && reception.getScreenName().equals("Richard")
                                              && reception.getPassword().equals("010")));
    }
  }

  @Nested
  public class getMethodTests {

    @Test
    public void get_ReceptionStaffAsParameter_ShouldReturnSpecifiedReceptionStaffFromDB()
            throws CouldNotFetchReceptionStaffException, InvalidReceptionStaffInputException {

      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff("gk222hi");
      ReceptionStaff receptionStaff1 = receptionDao.get(receptionStaff);

      assertAll(() -> assertEquals(receptionStaff1.getUserName(), "gk222hi"),
                () -> assertEquals(receptionStaff1.getScreenName(), "Georgios"),
                () -> assertEquals(receptionStaff1.getPassword(), "101"));
    }

    @Test
    public void get_NullAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = null;

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.get(receptionStaff));
    }

    @Test
    public void get_ReceptionStaffWithNullUserNameAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff(null);

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.get(receptionStaff));
    }

    @Test
    public void get_NoneExistingReceptionStaffInDbAsParameter_ShouldThrowCouldNotFetchReceptionStaffException() {
      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff("du223my");

      assertThrows(CouldNotFetchReceptionStaffException.class, () -> receptionDao.get(receptionStaff));
    }
  }

  @Nested
  public class deleteMethodTests {

    @Test
    public void delete_ReceptionStaffAsParameter_ShouldThrowCouldNotFetchReceptionStaffExceptionWhenFetchingDeletedRF()
            throws CouldNotDeleteReceptionStaffException, InvalidReceptionStaffInputException,
            CouldNotAddReceptionStaffException {

      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff("gk222hi", "Georgios", "101");
      receptionDao.delete(receptionStaff);

      assertThrows(CouldNotFetchReceptionStaffException.class,
              () -> receptionDao.get(new ReceptionStaff("gk222hi")));

      receptionDao.add(receptionStaff);
    }

    @Test
    public void delete_NullAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = null;

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.delete(receptionStaff));
    }

    @Test
    public void delete_ReceptionStaffWithNullUserNameAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff(null);

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.delete(receptionStaff));
    }

    @Test
    public void delete_NoneExistingReceptionStaffInDbAsParameter_ShouldNotThrowException()
            throws CouldNotDeleteReceptionStaffException, InvalidReceptionStaffInputException {

      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff("Test", "test", "test");
      receptionDao.delete(receptionStaff);

      assertDoesNotThrow(() -> receptionDao.delete(receptionStaff));
    }
  }

  @Nested
  public class addMethodTests {

    @Test
    public void add_ReceptionStaffAsParameter_ShouldReturnSpecifiedReceptionStaffFromDbAfterAddingIt()
            throws CouldNotAddReceptionStaffException, InvalidReceptionStaffInputException,
            CouldNotFetchReceptionStaffException, CouldNotDeleteReceptionStaffException {

      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff("xy66xz", "Bobbi", "321");
      receptionDao.add(receptionStaff);
      ReceptionStaff test = receptionDao.get(new ReceptionStaff("xy66xz"));

      assertAll(() -> assertEquals(test.getUserName(), "xy66xz"),
                () -> assertEquals(test.getScreenName(), "Bobbi"),
                () -> assertEquals(test.getPassword(), "321"));

      receptionDao.delete(receptionStaff);
    }

    @Test
    public void add_NullAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = null;

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.add(receptionStaff));
    }

    @Test
    public void add_ReceptionStaffWithNullUserNameAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff(null);

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.add(receptionStaff));
    }

    @Test
    public void add_ExistingReceptionStaffInDbAsParameter_ShouldThrowCouldNotAddReceptionStaffException() {
      ReceptionDao receptionDao = new ReceptionDao();
      ReceptionStaff receptionStaff = new ReceptionStaff("gk222hi", "Georgios", "101");

      assertThrows(CouldNotAddReceptionStaffException.class, () -> receptionDao.add(receptionStaff));
    }
  }

  @Nested
  public class promoteUserToRecpMethodTests {

    @Test
    public void promoteUserToRecp_UserAsParameter_ShouldPromoteSpecifiedUserToReceptionStaff()
            throws CouldNotPromoteToReceptionException, InvalidReceptionStaffInputException,
            CouldNotDemoteReceptionStaffException, CouldNotFetchReceptionStaffException {

      ReceptionDao receptionDao = new ReceptionDao();
      receptionDao.promoteUserToRecp(new User("ha223cz"));

      assertEquals("ha223cz", receptionDao.get(new ReceptionStaff("ha223cz")).getUserName());

      receptionDao.demoteUserFromRecp(new User("ha223cz"));
    }

    @Test
    public void promoteUserToRecp_NullAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      User user = null;

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.promoteUserToRecp(user));
    }

    @Test
    public void promoteUserToRecp_UserWithNullUserNameAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      User user = new User(null);

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.promoteUserToRecp(user));
    }

    @Test
    public void promoteUserToRecp_NoneExistingUserInDbAsParameter_ShouldThrowCouldNotPromoteToReceptionException() {
      ReceptionDao receptionDao = new ReceptionDao();
      User user = new User("666");

      assertThrows(CouldNotPromoteToReceptionException.class, () -> receptionDao.promoteUserToRecp(user));
    }
  }

  @Nested
  public class demoteUserFromRecpMethodTests {

    @Test
    public void demoteUserFromRecp_UserAsParameter_ShouldDemoteSpecifiedReceptionStaffToUser()
            throws CouldNotDemoteReceptionStaffException, InvalidReceptionStaffInputException,
            CouldNotPromoteToReceptionException {

      ReceptionDao receptionDao = new ReceptionDao();
      receptionDao.demoteUserFromRecp(new User("gk222hi"));
      ReceptionStaff receptionStaff = new ReceptionStaff("gk222hi");

      assertThrows(CouldNotFetchReceptionStaffException.class, () -> receptionDao.get(receptionStaff));

      receptionDao.promoteUserToRecp(new User("gk222hi"));
    }

    @Test
    public void demoteUserFromRecp_NullAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      User user = null;

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.demoteUserFromRecp(user));
    }

    @Test
    public void demoteUserFromRecp_UserWithNullUserNameAsParameter_ShouldThrowInvalidReceptionStaffInputException() {
      ReceptionDao receptionDao = new ReceptionDao();
      User user = new User(null);

      assertThrows(InvalidReceptionStaffInputException.class, () -> receptionDao.demoteUserFromRecp(user));
    }

    @Test
    public void demoteUserFromRecp_NoneExistingUserInDbAsParameter_ShouldNotThrowException()  {
      ReceptionDao receptionDao = new ReceptionDao();
      User user = new User("666");

      assertDoesNotThrow(() -> receptionDao.demoteUserFromRecp(user));
    }

    @Test
    public void demoteUserFromRecp_NoneReceptionStaffUserAsParameter_ShouldNotThrowException()  {
      ReceptionDao receptionDao = new ReceptionDao();
      User user = new User("mg223uk");

      assertDoesNotThrow(() -> receptionDao.demoteUserFromRecp(user));
    }
  }
}

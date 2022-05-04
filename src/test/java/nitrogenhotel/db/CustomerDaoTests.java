package nitrogenhotel.db;

import java.util.List;
import nitrogenhotel.db.dataaccessobjects.CustomerDao;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.exceptions.customer.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDaoTests extends dbTest {

  @BeforeAll
  @DisplayName("We create and add a Customer 'before all' otherwise we get problems with the auto" +
          "incrementing primary key in the DB ")
  public static void add_CustomerAsParameter_AddsCustomerToDB()
          throws CouldNotAddCustomerException, InvalidCustomerInputException {

    CustomerDao customerDao = new CustomerDao();
    Customer customer = new Customer("18540106-1234", "Sherlock Holmes",
            "221 Baker Street", "Cash");

    customerDao.add(customer);
  }

  @Nested
  public class getAllMethodTests {

    @Test
    public void getAll_ReturnedListShouldNotBeEmpty() throws CouldNotFetchAllCustomersException {
      CustomerDao customerDao = new CustomerDao();
      List<Customer> allCustomers = customerDao.getAll();

      assertFalse(allCustomers.isEmpty());
    }

    @Test
    public void getAll_ShouldReturnListContainingTheCustomersInDB() throws CouldNotFetchAllCustomersException {
      CustomerDao customerDao = new CustomerDao();
      List<Customer> allCustomers = customerDao.getAll();

      assertTrue(
              allCustomers.stream()
                      .anyMatch(
                              customer ->
                                      customer.getCustomerID() == 1
                                              && customer.getPublicID().equals("ARG 123456")
                                              && customer.getCustomerName().equals("Homer SIMPson")
                                              && customer.getAddress().equals("742 Evergreen Terrace")
                                              && customer.getPaymentMethod().equals("Visa")));
      assertTrue(
              allCustomers.stream()
                      .anyMatch(
                              customer ->
                                      customer.getCustomerID() == 2
                                              && customer.getPublicID().equals("19440606-1998")
                                              && customer.getCustomerName().equals("Revolver Ocelot")
                                              && customer.getAddress().equals("601 Outer Haven, Bering Sea")
                                              && customer.getPaymentMethod().equals("Master Card")));
    }
  }

  @Nested
  public class getMethodTests {

    @Test
    public void get_CustomerAsParameter_ShouldReturnSpecifiedCustomerFromDB()
            throws InvalidCustomerInputException, CouldNotFetchCustomerException {

      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);
      Customer customer1 = customerDao.get(customer);

      assertAll(() -> assertEquals(customer1.getPublicID(), "ARG 123456"),
                () -> assertEquals(customer1.getCustomerName(), "Homer SIMPson"),
                () -> assertEquals(customer1.getAddress(), "742 Evergreen Terrace"),
                () -> assertEquals(customer1.getPaymentMethod(), "Visa"));
    }

    @Test
    public void get_NullAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = null;

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.get(customer));
    }

    @Test
    public void get_CustomerWithCustomerIdZeroAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(0);

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.get(customer));
    }

    @Test
    public void get_CustomerWithNegativeIntegerCustomerIdAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(-1);

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.get(customer));
    }

    @Test
    public void get_NoneExistingCustomerInDbAsParameter_ShouldThrowCouldNotFetchCustomerException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1000);

      assertThrows(CouldNotFetchCustomerException.class, () -> customerDao.get(customer));
    }
  }

  @Nested
  public class deleteMethodTests {

    @Test
    public void delete_CustomerAsParameter_ShouldThrowCouldNotFetchUserExceptionWhenFetchingDeletedCustomer()
            throws InvalidCustomerInputException, CouldNotDeleteCustomerException {

      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(3);

      customerDao.delete(customer);

      assertThrows(CouldNotFetchCustomerException.class, () -> customerDao.get(new Customer(3)));
    }

    @Test
    public void delete_NullAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = null;

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.delete(customer));
    }

    @Test
    public void delete_CustomerWithCustomerIdZeroAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(0);

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.delete(customer));
    }

    @Test
    public void delete_CustomerWithNegativeIntegerCustomerIdAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(-1);

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.delete(customer));
    }

    @Test
    public void delete_NoneExistingCustomerInDbAsParameter_ShouldNotThrowException()
            throws InvalidCustomerInputException, CouldNotDeleteCustomerException {

      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(9999);
      customerDao.delete(customer);

      assertDoesNotThrow(() -> customerDao.delete(customer));
    }
  }

  @Nested
  public class addMethodTests {

    @Test
    @DisplayName("Tests If the 'before all' test method added the customer successfully")
    public void add_CustomerAsParameter_ShouldReturnSpecifiedCustomerAfterAddingIt()
            throws CouldNotFetchCustomerException, InvalidCustomerInputException {

      CustomerDao customerDao = new CustomerDao();

      Customer customer = customerDao.get(new Customer(3));

      assertAll(() -> assertEquals(customer.getPublicID(), "18540106-1234"),
                () -> assertEquals(customer.getCustomerName(), "Sherlock Holmes"),
                () -> assertEquals(customer.getAddress(), "221 Baker Street"),
                () -> assertEquals(customer.getPaymentMethod(), "Cash"));
    }

    @Test
    public void add_NullAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = null;

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.add(customer));
    }

    @Test
    public void add_CustomerWithNullPublicIdAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(null, "Sherlock Holmes",
              "221 Baker Street", "Cash");

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.add(customer));
    }

    @Test
    public void add_CustomerWithNullCustomerNameAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer("18540106-1234", null,
              "221 Baker Street", "Cash");

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.add(customer));
    }

    @Test
    public void add_CustomerWithNullAdressAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer("18540106-1234", "Sherlock Holmes",
              null, "Cash");

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.add(customer));
    }

    @Test
    public void add_CustomerWithNullPaymentMethodAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer("18540106-1234", "Sherlock Holmes",
              "221 Baker Street", null);

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.add(customer));
    }

    @Test
    public void add_ExistingCustomerInDb_ShouldThrowCouldNotAddCustomerException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer("ARG 123456", "Homer SIMPson",
              "742 Evergreen Terrace", "Visa");

      assertThrows(CouldNotAddCustomerException.class, () -> customerDao.add(customer));
    }

    @Test
    public void add_CustomerWithOnlyCustomerIdAsParameter_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(3);

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.add(customer));
    }

  }

  @Nested
  public class updateAddressMethodTests {

    @Test
    public void updateAddress_CustomerAs1ParameterStringAs2_ShouldUpdateCustomerAddressInDB()
            throws InvalidCustomerInputException, CouldNotUpdateAddressException,CouldNotFetchCustomerException {

      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);

      customerDao.updateAddress(customer, "Bakersfield");

      assertEquals("Bakersfield", customerDao.get(new Customer(1)).getAddress());

      customerDao.updateAddress(new Customer(1), "742 Evergreen Terrace");
    }

    @Test
    public void updateAddress_NullAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = null;

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updateAddress(customer, "Bakersfield"));
    }

    @Test
    public void updateAddress_CustomerAs1ParameterNullAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);


      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updateAddress(customer, null));
    }

    @Test
    public void updateAddress_CustomerWithCustomerIdZeroAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(0);

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updateAddress(customer, "Bakersfield"));
    }

    @Test
    public void updateAddress_CustomerWithNegativeIntegerCustomerIdAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(-1);

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updateAddress(customer, "Bakersfield"));
    }

    @Test
    public void updateAddress_NoneExistingCustomerInDbAs1ParameterStringAs2_ShouldNotThrowException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(696969);

      assertDoesNotThrow(() -> customerDao.updateAddress(customer, "Bakersfield"));
    }
  }

  @Nested
  public class updateNameMethodTests {

    @Test
    public void updateName_CustomerAs1ParameterStringAs2_ShouldUpdateCustomerNameInDB()
            throws  InvalidCustomerInputException, CouldNotFetchCustomerException,
            CouldNotUpdateCustomerNameException {

      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);

      customerDao.updateName(customer, "Sam");

      assertEquals("Sam", customerDao.get(new Customer(1)).getCustomerName());

      customerDao.updateName(new Customer(1), "Homer SIMPson");
    }

    @Test
    public void updateName_NullAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = null;

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.updateName(customer, "Bobi"));
    }

    @Test
    public void updateName_CustomerAs1ParameterNullAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);


      assertThrows(InvalidCustomerInputException.class, () -> customerDao.updateName(customer, null));
    }

    @Test
    public void updateName_CustomerWithCustomerIdZeroAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(0);

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updateName(customer, "Bobi"));
    }

    @Test
    public void updateName_CustomerWithNegativeIntegerCustomerIdAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(-1);

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updateName(customer, "Bobi"));
    }

    @Test
    public void updateName_NoneExistingCustomerInDbAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(696969);

      assertDoesNotThrow(() -> customerDao.updateName(customer, "Bobi"));
    }
  }

  @Nested
  public class updatePaymentMethodTests {

    @Test
    public void updatePayment_CustomerAs1ParameterStringAs2_ShouldUpdateCustomerPaymentMethodInDB()
            throws InvalidCustomerInputException, CouldNotFetchCustomerException,
            CouldNotUpdatePaymentMethodException {

      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);

      customerDao.updatePaymentMethod(customer, "Paypal");

      assertEquals("Paypal", customerDao.get(new Customer(1)).getPaymentMethod());

      customerDao.updatePaymentMethod(new Customer(1), "Visa");
    }

    @Test
    public void updatePayment_NullAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = null;

      assertThrows(InvalidCustomerInputException.class, () -> customerDao.updatePaymentMethod(customer, "Visa"));
    }

    @Test
    public void updatePayment_CustomerAs1ParameterNullAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);


      assertThrows(InvalidCustomerInputException.class, () -> customerDao.updatePaymentMethod(customer, null));
    }

    @Test
    public void updatePayment_CustomerWithCustomerIdZeroAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(0);

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updatePaymentMethod(customer, "Visa"));
    }

    @Test
    public void updatePayment_CustomerWithNegativeIntegerCustomerIdAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(-1);

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updatePaymentMethod(customer, "Visa"));
    }

    @Test
    public void updatePayment_NoneExistingCustomerInDbAs1ParameterStringAs2_ShouldNotThrowException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(696969);

      assertDoesNotThrow(() -> customerDao.updatePaymentMethod(customer, "Visa"));
    }
  }

  @Nested
  public class updatePublicIDMethodTests {

    @Test
    public void updatePublicID_CustomerAs1ParameterStringAs2_ShouldUpdateCustomerPublicIdInDB()
            throws InvalidCustomerInputException, CouldNotUpdatePublicIDException,
            CouldNotFetchCustomerException {

      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);

      customerDao.updatePublicID(customer, "ABA 123456");

      assertEquals("ABA 123456", customerDao.get(new Customer(1)).getPublicID());

      customerDao.updatePublicID(new Customer(1), "ARG 123456");
    }

    @Test
    public void updatePublicID_NullAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = null;

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updatePublicID(customer, "ABA 123456"));
    }

    @Test
    public void updatePublicID_CustomerAs1ParameterNullAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(1);


      assertThrows(InvalidCustomerInputException.class, () -> customerDao.updatePublicID(customer, null));
    }

    @Test
    public void updatePublicID_CustomerWithCustomerIdZeroAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(0);

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updatePublicID(customer, "ABA 123456"));
    }

    @Test
    public void updatePublicID_CustomerWithNegativeIntegerCustomerIdAs1ParameterStringAs2_ShouldThrowInvalidCustomerInputException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(-1);

      assertThrows(InvalidCustomerInputException.class,
              () -> customerDao.updatePublicID(customer, "ABA 123456"));
    }

    @Test
    public void updatePublicID_NoneExistingCustomerInDbAs1ParameterStringAs2_ShouldNotThrowException() {
      CustomerDao customerDao = new CustomerDao();
      Customer customer = new Customer(696969);

      assertDoesNotThrow(() -> customerDao.updatePublicID(customer, "ABA 123456"));
    }
  }
}


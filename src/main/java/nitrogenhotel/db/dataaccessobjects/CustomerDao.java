package nitrogenhotel.db.dataaccessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nitrogenhotel.db.DataBaseManager;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.exceptions.customer.CouldNotAddCustomerException;
import nitrogenhotel.db.exceptions.customer.CouldNotDeleteCustomerException;
import nitrogenhotel.db.exceptions.customer.CouldNotFetchAllCustomersException;
import nitrogenhotel.db.exceptions.customer.CouldNotFetchCustomerException;
import nitrogenhotel.db.exceptions.customer.CouldNotUpdateAddressException;
import nitrogenhotel.db.exceptions.customer.CouldNotUpdateCustomerNameException;
import nitrogenhotel.db.exceptions.customer.CouldNotUpdatePaymentMethodException;
import nitrogenhotel.db.exceptions.customer.CouldNotUpdatePublicIDException;
import nitrogenhotel.db.exceptions.customer.InvalidCustomerInputException;
import nitrogenhotel.db.interfaces.BasicDao;
import nitrogenhotel.db.interfaces.IcustomerDao;

/**
 * A DAO class used to interact with customers information.
 */
public class CustomerDao implements BasicDao<Customer>, IcustomerDao {

  /**
   * Fetches all the customers available in the database.
   *
   * @return It returns a list containing all the Customer objects in the database.
   * @throws CouldNotFetchAllCustomersException thrown for errors while fetching a list of
   *                                            customers.
   */
  @Override
  public List<Customer> getAll() throws CouldNotFetchAllCustomersException {
    Connection conn = DataBaseManager.getConnection();
    List<Customer> allCustomers = new ArrayList<Customer>();
    Customer customer;

    String queryString = "SELECT * FROM Customer;";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      ResultSet result = preparedStmnt.executeQuery();
      while (result.next()) {
        customer =
            new Customer(
                result.getInt("customerID"),
                result.getString("publicID"),
                result.getString("customerName"),
                result.getString("address"),
                result.getString("paymentMethod"));
        allCustomers.add(customer);
      }
      result.close();
      return allCustomers;
    } catch (SQLException e) {
      throw new CouldNotFetchAllCustomersException(
          "There was an error while fetching the list of Customers available in the database.", e);
    }
  }

  /**
   * Returns an customer from the database.
   *
   * @param obj name of an customer to be retrieved from the database.
   * @return It returns Customer object.
   * @throws InvalidCustomerInputException  thrown when an invalid customer or a wrong customerID is
   *                                        given.
   * @throws CouldNotFetchCustomerException thrown when an error occurs while fetching an customer.
   */
  @Override
  public Customer get(Customer obj)
      throws InvalidCustomerInputException, CouldNotFetchCustomerException {

    if (obj == null || obj.getCustomerID() <= 0) {
      throw new InvalidCustomerInputException(
          "The customer must not be null and a customerID of the customer must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();
    Customer customer;

    String queryString = "SELECT * FROM Customer WHERE customerID=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setInt(1, obj.getCustomerID());
      ResultSet result = preparedStmnt.executeQuery();
      result.next();
      customer =
          new Customer(
              result.getInt("customerID"),
              result.getString("publicID"),
              result.getString("customerName"),
              result.getString("address"),
              result.getString("paymentMethod"));

      result.close();
      return customer;
    } catch (SQLException e) {
      throw new CouldNotFetchCustomerException(
          "There was an error while fetching the customer " + obj.getCustomerID() + " " + obj
              .getCustomerName(), e);
    }
  }

  /**
   * Deletes a customer from the database.
   *
   * @param obj The customer to be deleted.
   * @throws InvalidCustomerInputException   Thrown when given bad input.
   * @throws CouldNotDeleteCustomerException Thrown when an error occurs while deleting the
   *                                         customer.
   */
  @Override
  public void delete(Customer obj)
      throws InvalidCustomerInputException, CouldNotDeleteCustomerException {
    if (obj == null || obj.getCustomerID() <= 0) {
      throw new InvalidCustomerInputException(
          "The customer must not be null and a customerID must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "DELETE FROM Customer WHERE customerID=(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setInt(1, obj.getCustomerID());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotDeleteCustomerException(
          "There was an error while deleting the customer" + " " + obj.getCustomerName() + " "
              + "with ID" + " " + obj.getCustomerID(), e);
    }
  }

  /**
   * Adds a new customer to the database.
   *
   * @param obj The new customer
   * @throws InvalidCustomerInputException Thrown when invalid input is given.
   * @throws CouldNotAddCustomerException  Thrown when an error occurs while adding the customer.
   */
  @Override
  public void add(Customer obj)
      throws InvalidCustomerInputException, CouldNotAddCustomerException {
    if (obj == null
        || obj.getPublicID() == null
        || obj.getCustomerName() == null
        || obj.getAddress() == null
        || obj.getPaymentMethod() == null) {
      throw new InvalidCustomerInputException(
          "The customer must not be null and all attributes must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "INSERT INTO Customer (publicID, customerName, address, paymentMethod) VALUES(?,?,?,?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {

      preparedStmnt.setString(1, obj.getPublicID());
      preparedStmnt.setString(2, obj.getCustomerName());
      preparedStmnt.setString(3, obj.getAddress());
      preparedStmnt.setString(4, obj.getPaymentMethod());
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      throw new CouldNotAddCustomerException(
          "There was an error while adding the customer" + " " + obj.getCustomerName(), e);
    }
  }

  /**
   * Updates the address of the customer.
   *
   * @param cus     The customer.
   * @param address The new address.
   * @throws CouldNotUpdateAddressException Thrown when an error occurs while updating.
   * @throws InvalidCustomerInputException  Thrown when given bad input.
   */
  @Override
  public void updateAddress(Customer cus, String address)
      throws CouldNotUpdateAddressException, InvalidCustomerInputException {

    if (cus == null || cus.getCustomerID() <= 0 || address == null) {
      throw new InvalidCustomerInputException(
          "The customer must not be null, a customerID and an address must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Customer SET address = (?) WHERE customerID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, address);
      preparedStmnt.setInt(2, cus.getCustomerID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdateAddressException(
          "There was an error while updating the name of the customer: " + cus.getCustomerID(),
          e);
    }

  }

  /**
   * Updates the name of the customer.
   *
   * @param cus  The customer.
   * @param name The new name.
   * @throws CouldNotUpdateCustomerNameException Thrown if an error occurs while updating.
   * @throws InvalidCustomerInputException       Thrown if given bad input.
   */
  @Override
  public void updateName(Customer cus, String name)
      throws CouldNotUpdateCustomerNameException, InvalidCustomerInputException {

    if (cus == null || cus.getCustomerID() <= 0 || name == null) {
      throw new InvalidCustomerInputException(
          "The customer must not be null, a customerID and a name must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Customer SET customerName = (?) WHERE customerID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, name);
      preparedStmnt.setInt(2, cus.getCustomerID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdateCustomerNameException(
          "There was an error while updating the name of the customer: " + cus.getCustomerID(),
          e);
    }
  }

  /**
   * Updates the payment method of the customer.
   *
   * @param cus The customer.
   * @param pay The new payment method.
   * @throws InvalidCustomerInputException        Thrown when bad input is given.
   * @throws CouldNotUpdatePaymentMethodException Thrown when an error occurs while updating.
   */
  @Override
  public void updatePaymentMethod(Customer cus, String pay)
      throws InvalidCustomerInputException, CouldNotUpdatePaymentMethodException {
    if (cus == null || cus.getCustomerID() <= 0 || pay == null) {
      throw new InvalidCustomerInputException(
          "The customer must not be null, a customerID and a payment method must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Customer SET paymentMethod = (?) WHERE customerID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, pay);
      preparedStmnt.setInt(2, cus.getCustomerID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdatePaymentMethodException(
          "There was an error while updating the payment method of the customer: " + cus
              .getCustomerID(),
          e);
    }
  }

  /**
   * Updates the publicID of the customer.
   *
   * @param cus  The customer.
   * @param publicID The new publicID.
   * @throws CouldNotUpdatePublicIDException Thrown if an error occurs while updating.
   * @throws InvalidCustomerInputException   Thrown if given bad input.
   */
  @Override
  public void updatePublicID(Customer cus, String publicID)
          throws InvalidCustomerInputException, CouldNotUpdatePublicIDException {
    if (cus == null || cus.getCustomerID() <= 0 || publicID == null) {
      throw new InvalidCustomerInputException(
              "The customer must not be null, a customerID with a publicID must be provided.");
    }

    Connection conn = DataBaseManager.getConnection();

    String queryString = "UPDATE Customer SET publicID = (?) WHERE customerID =(?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
      preparedStmnt.setString(1, publicID);
      preparedStmnt.setInt(2, cus.getCustomerID());
      preparedStmnt.executeUpdate();
    } catch (SQLException e) {
      throw new CouldNotUpdatePublicIDException(
              "There was an error while updating the publicID of the customer: " + cus
                      .getCustomerID(), e);
    }
  }
}

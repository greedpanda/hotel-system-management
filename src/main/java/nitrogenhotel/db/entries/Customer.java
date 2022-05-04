package nitrogenhotel.db.entries;

/**
 * A class that represents the customer table.
 */
public class Customer {

  private int customerID;
  private String publicID;
  private String customerName;
  private String address;
  private String paymentMethod;

  /**
   * Constructor used to fetch a customer.
   *
   * @param customerID int.
   */
  public Customer(int customerID) {
    this.customerID = customerID;
  }

  /**
   * Constructor used to fetch a customer.
   *
   * @param customerID int
   * @param publicID String
   * @param customerName String
   * @param address String
   * @param paymentMethod String
   */
  public Customer(int customerID, String publicID, String customerName, String address, String paymentMethod) {
    this.customerID = customerID;
    this.publicID = publicID;
    this.customerName = customerName;
    this.address = address;
    this.paymentMethod = paymentMethod;
  }

  /**
   * Constructor used to add a customer.
   *
   * @param publicID String
   * @param customerName String
   * @param address String
   * @param paymentMethod String
   *
    */
  public Customer(String publicID, String customerName, String address, String paymentMethod) {
    this.publicID = publicID;
    this.customerName = customerName;
    this.address = address;
    this.paymentMethod = paymentMethod;
  }

  public int getCustomerID() {
    return customerID;
  }


  public String getPublicID() {
    return publicID;
  }

  public void setPublicID(String publicID) {
    this.publicID = publicID;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
}

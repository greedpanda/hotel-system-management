package nitrogenhotel.db.interfaces;

import nitrogenhotel.db.entries.Customer;

/**
 * An interface that defines updating the customer.
 */
public interface IcustomerDao {

  void updateAddress(Customer cus, String address) throws Exception;

  void updateName(Customer cus, String name) throws Exception;

  void updatePaymentMethod(Customer cus, String pay) throws Exception;

  void updatePublicID(Customer cus, String publicID) throws Exception;

}

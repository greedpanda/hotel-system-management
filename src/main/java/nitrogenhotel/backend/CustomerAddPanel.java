package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import nitrogenhotel.db.dataaccessobjects.CustomerDao;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.exceptions.customer.CouldNotAddCustomerException;
import nitrogenhotel.db.exceptions.customer.CouldNotFetchAllCustomersException;
import nitrogenhotel.db.exceptions.customer.InvalidCustomerInputException;
import nitrogenhotel.ui.panels.CustomerAddPanelUI;
import org.tinylog.Logger;

/** Backend class for adding customer logic. */
public class CustomerAddPanel extends CommandPanel {
  public CustomerAddPanelUI ui;
  private MainFrame mf;

  public CustomerAddPanel() {
    super(new CustomerAddPanelUI());
    ui = (CustomerAddPanelUI) super.ui;
  }

  public CustomerAddPanel(MainFrame mf) {
    this();
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {
      case "add customer ok" -> {
        if (run()) {
          mf.clearCurrentPanel();
          ui.success("Customer added.");
        }
      }

      case "cancel" -> mf.clearCurrentPanel();

      default -> Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    String publicID = ui.getPublicID();
    String customerName = ui.getCustomerName();
    String address = ui.getAddress();

    if (publicID.isEmpty() || customerName.isEmpty() || address.isEmpty()) {
      Logger.warn("Fields cannot be empty.");
      ui.warn("All fields must be filled.");
      clearUI();
      return false;
    }

    if (!InputPatterns.CUST_NAME.test(customerName)) {
      Logger.warn("Customer name invalid");
      ui.warn(InputPatterns.CUST_NAME.userWarnMsg);
      return false;
    }

    if (!InputPatterns.CUST_PUBLIC_ID.test(publicID)) {
      Logger.warn("Public ID invalid");
      ui.warn(InputPatterns.CUST_PUBLIC_ID.userWarnMsg);
      return false;
    }

    if (!InputPatterns.CUST_ADDRESS.test(address)) {
      Logger.warn("Address invalid");
      ui.warn(InputPatterns.CUST_ADDRESS.userWarnMsg);
      return false;
    }

    Customer newCustomer = new Customer(
            publicID,
            customerName,
            address,
            ui.getPaymentMethod()
    );

    CustomerDao customerDao = new CustomerDao();
    boolean newCustomerAlreadyExists = false;

    try {
      newCustomerAlreadyExists = customerDao.getAll().stream()
          .anyMatch(cust -> cust.getPublicID().equals(newCustomer.getPublicID()));
    } catch (CouldNotFetchAllCustomersException e) {
      Logger.warn(e);
      ui.warn("Could not add customer.");
      clearUI();
      return false;
    }

    if (newCustomerAlreadyExists) {
      Logger.warn("Customer already in the database.");
      ui.warn("Customer already in the database.");
      clearUI();
      return false;
    }

    try {
      customerDao.add(newCustomer);
    } catch (CouldNotAddCustomerException e) {
      Logger.warn(e);
      ui.warn("Could not add customer to the database.");
      clearUI();
      return false;
    } catch (InvalidCustomerInputException e) {
      Logger.warn(e);
      ui.warn("New customer has invalid data.");
      clearUI();
      return false;
    }

    clearUI();
    return true;
  }

  private void clearUI() {
    ui.customerNameInput.setText("");
    ui.publicIDInput.setText("");
    ui.addressInput.setText("");
  }
}

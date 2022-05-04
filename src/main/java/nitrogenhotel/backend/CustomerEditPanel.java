package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import nitrogenhotel.db.dataaccessobjects.CustomerDao;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.exceptions.customer.CouldNotFetchCustomerException;
import nitrogenhotel.db.exceptions.customer.CouldNotUpdateAddressException;
import nitrogenhotel.db.exceptions.customer.CouldNotUpdateCustomerNameException;
import nitrogenhotel.db.exceptions.customer.CouldNotUpdatePaymentMethodException;
import nitrogenhotel.db.exceptions.customer.CouldNotUpdatePublicIDException;
import nitrogenhotel.db.exceptions.customer.InvalidCustomerInputException;
import nitrogenhotel.ui.panels.CustomerEditPanelUI;
import org.tinylog.Logger;

/** Backend Panel to handle the edit customer panel logic. */
public class CustomerEditPanel extends CommandPanel {
  public CustomerEditPanelUI ui;
  private MainFrame mf;

  public CustomerEditPanel(List<Customer> customers) {
    super(new CustomerEditPanelUI(customers));
    ui = (CustomerEditPanelUI) super.ui;
  }

  public CustomerEditPanel(MainFrame mf, List<Customer> customers) {
    this(customers);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {
      case "edit customer ok" -> {
        if (run()) {
          mf.clearCurrentPanel();
          ui.success("Customer edited.");
        }
      }

      case "edit customer apply" -> {
        if (run()) {
          mf.refreshCurrentPanel();
        }
      }

      case "cancel" -> mf.clearCurrentPanel();

      default -> Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    Map<Integer, Customer> customerList = ui.getEditedCustomerData();
    if (customerList == null) {
      Logger.warn("CustomerEditPanelUI returned null for customerList");
      ui.warn("Could not update Customers.");
      return false;
    }

    if (customerList.size() == 0) {
      Logger.info("No customers edited, skipping refresh");
      return false;
    }

    Logger.debug("Edited customers returned from GUI\n%s".formatted(customerList));

    Customer[] customers = customerList.values().toArray(new Customer[0]);
    CustomerDao customerDao = new CustomerDao();

    for (Customer c : customers) {
      try {
        Customer originalCustomer = customerDao.get(c);
        if (!originalCustomer.getCustomerName().equals(c.getCustomerName())) {
          customerDao.updateName(originalCustomer, c.getCustomerName());
        }
        if (!originalCustomer.getAddress().equals(c.getAddress())) {
          customerDao.updateAddress(originalCustomer, c.getAddress());
        }
        if (!originalCustomer.getPaymentMethod().equals(c.getPaymentMethod())) {
          customerDao.updatePaymentMethod(originalCustomer, c.getPaymentMethod());
        }
        if (!originalCustomer.getPublicID().equals(c.getPublicID())) {
          customerDao.updatePublicID(originalCustomer, c.getPublicID());
        }
      } catch (InvalidCustomerInputException e) {
        Logger.warn(e);
        ui.warn("Edited customer has invalid data.");
        return false;
      } catch (CouldNotFetchCustomerException
          | CouldNotUpdatePublicIDException
          | CouldNotUpdatePaymentMethodException
          | CouldNotUpdateAddressException
          | CouldNotUpdateCustomerNameException e) {
        Logger.warn(e);
        ui.warn("Database error: could not update customer.");
        return false;
      }
    }

    return true;
  }
}

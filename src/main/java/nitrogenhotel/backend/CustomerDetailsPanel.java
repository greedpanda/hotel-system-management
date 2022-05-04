package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.util.List;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.ui.panels.CustomerDetailsPanelUI;
import org.tinylog.Logger;

/** Backend Panel to handle the show customer detail panel logic. */
public class CustomerDetailsPanel extends CommandPanel {
  public CustomerDetailsPanelUI ui;
  private MainFrame mf;

  public CustomerDetailsPanel(List<Customer> customers) {
    super(new CustomerDetailsPanelUI(customers));
    ui = (CustomerDetailsPanelUI) super.ui;
  }

  public CustomerDetailsPanel(MainFrame mf, List<Customer> customers) {
    this(customers);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    if (actionCommand.equals("cancel")) {
      mf.clearCurrentPanel();
    } else {
      Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    return true;
  }
}

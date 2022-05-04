package nitrogenhotel.ui.panels;

import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.ui.actioncommands.CustomerDetailsActionCommand;
import nitrogenhotel.ui.utilsgui.CustomerTable;

/** CustomerDetailsPanelUI displays table of customers in NON editable table. */
public class CustomerDetailsPanelUI extends CustomerTable implements PanelUI {
  public CustomerDetailsPanelUI(List<Customer> customerList) {
    super(customerList);
  }

  @Override
  public void setupUI(CommandPanel panel) {
    buildCustomerTable(panel, "Customer details", "");

    JPanel buttonPanel = new JPanel();

    JButton btnClose = new JButton("Close");
    btnClose.setActionCommand(CustomerDetailsActionCommand.CLOSE.str);
    btnClose.addActionListener(panel);

    buttonPanel.add(Box.createHorizontalStrut(40));
    buttonPanel.add(btnClose);
    buttonPanel.add(Box.createHorizontalStrut(40));
    
    panel.add(buttonPanel);
  }
}

package nitrogenhotel.ui.panels;

import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.ui.actioncommands.CustomerEditActionCommand;
import nitrogenhotel.ui.utilsgui.CustomerTable;

/** CustomerEditPanelUI displays table of customers in editable table. */
public class CustomerEditPanelUI extends CustomerTable implements PanelUI {
  public CustomerEditPanelUI(List<Customer> customerList) {
    super(customerList);
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (columnIndex == 0) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void setupUI(CommandPanel panel) {
    buildCustomerTable(panel, "Edit Customer", "click on table cells to edit/confirm by pressing Enter");

    JPanel buttonPanel = new JPanel();

    JButton applyButton = new JButton("Apply");
    applyButton.setActionCommand(CustomerEditActionCommand.APPLY.str);
    applyButton.addActionListener(panel);

    JButton okButton = new JButton("OK");
    okButton.setActionCommand(CustomerEditActionCommand.OK.str);
    okButton.addActionListener(panel);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand(CustomerEditActionCommand.CANCEL.str);
    cancelButton.addActionListener(panel);

    buttonPanel.add(Box.createHorizontalStrut(40));
    buttonPanel.add(applyButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(okButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(cancelButton);
    buttonPanel.add(Box.createHorizontalStrut(40));

    panel.add(buttonPanel);

  }
}

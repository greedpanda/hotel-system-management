package nitrogenhotel.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.ui.actioncommands.CustomerAddActionCommand;
import nitrogenhotel.ui.utilsgui.GridLayoutCustom;
import nitrogenhotel.ui.utilsgui.GuiBorderLabelUtil;
import nitrogenhotel.utils.PaymentMethod;

/** UI for add customer. */
public class CustomerAddPanelUI implements PanelUI {
  public JTextField addressInput;
  public JTextField customerNameInput;
  public JComboBox<String> paymentVariableInput;
  public JTextField publicIDInput;

  @Override
  public void setupUI(CommandPanel panel) {
   
    java.awt.GridBagConstraints gridBagConstraints;
    
    panel.setLayout(new GridLayoutCustom(3, 1));
    panel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder("Add Customer")));

    customerNameInput = new javax.swing.JTextField();
    paymentVariableInput = new javax.swing.JComboBox<>();
    addressInput = new javax.swing.JTextField();
    publicIDInput = new javax.swing.JTextField();

    JPanel fieldsPanel = new JPanel();
    fieldsPanel.setLayout(new java.awt.GridBagLayout());

    JLabel customerNameLabel = new javax.swing.JLabel();
    customerNameLabel.setText("Customer Name:");
    GuiBorderLabelUtil.boldText(customerNameLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(92, 83, 0, 0);
    fieldsPanel.add(customerNameLabel, gridBagConstraints);

    JLabel addressLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(addressLabel);
    addressLabel.setText("Address:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(16, 83, 0, 0);
    fieldsPanel.add(addressLabel, gridBagConstraints);

    JLabel paymentMethodLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(paymentMethodLabel);
    paymentMethodLabel.setText("Payment Method:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(18, 83, 0, 0);
    fieldsPanel.add(paymentMethodLabel, gridBagConstraints);

    JPanel buttonPanel = new JPanel();
    JButton okButton = new javax.swing.JButton();
    okButton.setText("OK");
    okButton.setActionCommand(CustomerAddActionCommand.OK.str);
    okButton.addActionListener(panel);
    buttonPanel.add(okButton);
    buttonPanel.add(Box.createHorizontalStrut(30));

    JButton cancelButton = new javax.swing.JButton();
    cancelButton.setText("Cancel");
    cancelButton.setActionCommand(CustomerAddActionCommand.CANCEL.str);
    cancelButton.addActionListener(panel);
    buttonPanel.add(cancelButton);

    customerNameInput.setBackground(new java.awt.Color(56, 56, 77));
    customerNameInput.setPreferredSize(new java.awt.Dimension(125, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 74;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(88, 22, 0, 0);
    fieldsPanel.add(customerNameInput, gridBagConstraints);

    paymentVariableInput.setBackground(new java.awt.Color(56, 56, 77));
    for (PaymentMethod paymentMethod : PaymentMethod.values()) {
      paymentVariableInput.addItem(paymentMethod.toString());
    }
    paymentVariableInput.setPreferredSize(new java.awt.Dimension(125, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.ipadx = 74;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(12, 22, 0, 0);
    fieldsPanel.add(paymentVariableInput, gridBagConstraints);

    addressInput.setBackground(new java.awt.Color(56, 56, 77));
    addressInput.setPreferredSize(new java.awt.Dimension(125, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.ipadx = 74;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(12, 22, 0, 0);
    fieldsPanel.add(addressInput, gridBagConstraints);

    JLabel publicIDLabel = new javax.swing.JLabel();
    publicIDLabel.setText("Public ID:");
    GuiBorderLabelUtil.boldText(publicIDLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(16, 83, 0, 0);
    fieldsPanel.add(publicIDLabel, gridBagConstraints);

    publicIDInput.setBackground(new java.awt.Color(56, 56, 77));
    publicIDInput.setPreferredSize(new java.awt.Dimension(125, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 74;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(12, 22, 0, 0);
    fieldsPanel.add(publicIDInput, gridBagConstraints);

    panel.add(fieldsPanel);
    panel.add(buttonPanel);
    panel.add(Box.createVerticalStrut(90));

  }

  public String getCustomerName() {
    return customerNameInput.getText();
  }

  public String getAddress() {
    return addressInput.getText();
  }

  public String getPaymentMethod() {
    return (String) paymentVariableInput.getSelectedItem();
  }

  public String getPublicID() {
    return publicIDInput.getText();
  }
}

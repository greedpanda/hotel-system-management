package nitrogenhotel.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.ui.actioncommands.UserAddActionCommand;
import nitrogenhotel.ui.utilsgui.GridLayoutCustom;
import nitrogenhotel.ui.utilsgui.GuiBorderLabelUtil;

/** UI for add user. */
public class UserAddPanelUI implements PanelUI {
  public JPasswordField passwordInput;
  public JPasswordField repeatPasswordInput;
  public JComboBox<String> roleInput;
  public JTextField screenNameInput;
  public JTextField usernameInput;

  @Override
  public void setupUI(CommandPanel panel) {
    JLabel usernameLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(usernameLabel);
    usernameInput = new javax.swing.JTextField();
    screenNameInput = new javax.swing.JTextField();
    passwordInput = new javax.swing.JPasswordField();
    repeatPasswordInput = new javax.swing.JPasswordField();
    roleInput = new javax.swing.JComboBox<>();

    JPanel fieldsPanel = new JPanel();
    fieldsPanel.setLayout(new java.awt.GridBagLayout());
    usernameLabel.setText("Username:");
    java.awt.GridBagConstraints gridBagConstraints;
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(40, 82, 0, 0);
    fieldsPanel.add(usernameLabel, gridBagConstraints);

    JLabel screenNameLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(screenNameLabel);
    screenNameLabel.setText("Screen Name:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(22, 82, 0, 0);
    fieldsPanel.add(screenNameLabel, gridBagConstraints);

    JLabel passwordLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(passwordLabel);
    passwordLabel.setText("Password:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(17, 82, 0, 0);
    fieldsPanel.add(passwordLabel, gridBagConstraints);

    JLabel repeatPasswordLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(repeatPasswordLabel);
    repeatPasswordLabel.setText("Repeat password:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.gridwidth = 5;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(16, 82, 0, 0);
    fieldsPanel.add(repeatPasswordLabel, gridBagConstraints);

    usernameInput.setBackground(new java.awt.Color(56, 56, 77));
    usernameInput.setPreferredSize(new java.awt.Dimension(170, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 12;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(36, 22, 0, 78);
    fieldsPanel.add(usernameInput, gridBagConstraints);

    screenNameInput.setBackground(new java.awt.Color(56, 56, 77));
    screenNameInput.setPreferredSize(new java.awt.Dimension(170, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 12;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(18, 22, 0, 78);
    fieldsPanel.add(screenNameInput, gridBagConstraints);

    passwordInput.setBackground(new java.awt.Color(56, 56, 77));
    passwordInput.setPreferredSize(new java.awt.Dimension(170, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 12;
    gridBagConstraints.ipady = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(13, 22, 0, 78);
    fieldsPanel.add(passwordInput, gridBagConstraints);

    repeatPasswordInput.setBackground(new java.awt.Color(56, 56, 77));
    repeatPasswordInput.setPreferredSize(new java.awt.Dimension(170, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 12;
    gridBagConstraints.ipady = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(12, 22, 0, 78);
    fieldsPanel.add(repeatPasswordInput, gridBagConstraints);

    JLabel roleLabel = new javax.swing.JLabel();
    GuiBorderLabelUtil.boldText(roleLabel);
    roleLabel.setText("Role:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(22, 82, 0, 0);
    fieldsPanel.add(roleLabel, gridBagConstraints);

    roleInput.setBackground(new java.awt.Color(56, 56, 77));
    roleInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Reception Staff", "Administrator" }));
    roleInput.setPreferredSize(new java.awt.Dimension(170, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 12;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(18, 22, 0, 78);
    fieldsPanel.add(roleInput, gridBagConstraints);

    JPanel buttonPanel = new JPanel();
    JButton okButton = new javax.swing.JButton();
    okButton.setText("OK");
    okButton.setActionCommand(UserAddActionCommand.OK.str);
    okButton.addActionListener(panel);
    buttonPanel.add(okButton);
    buttonPanel.add(Box.createHorizontalStrut(40));

    JButton cancelButton = new javax.swing.JButton();
    cancelButton.setText("Cancel");
    cancelButton.setActionCommand(UserAddActionCommand.CANCEL.str);
    cancelButton.addActionListener(panel);
    buttonPanel.add(cancelButton);

    panel.setLayout(new GridLayoutCustom(3, 1));
    panel.add(fieldsPanel);
    panel.add(buttonPanel);
    panel.add(Box.createVerticalStrut(40));
    panel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder("Add User")));
  }

  public String getUsername() {
    return usernameInput.getText();
  }

  public String getScreenName() {
    return screenNameInput.getText();
  }

  public String getPassword() {
    return new String(passwordInput.getPassword());
  }

  public String getRepeatPassword() {
    return new String(repeatPasswordInput.getPassword());
  }

  public String getSelectedRole() {
    return (String) roleInput.getSelectedItem();
  }
}

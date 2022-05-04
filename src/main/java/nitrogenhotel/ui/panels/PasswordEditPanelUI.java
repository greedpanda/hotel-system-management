package nitrogenhotel.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.ui.actioncommands.EditPasswordActionCommand;
import nitrogenhotel.ui.utilsgui.GridLayoutCustom;
import nitrogenhotel.ui.utilsgui.GuiBorderLabelUtil;

/** EditPasswordPanelUI that builds edit pssword fields. */
public class PasswordEditPanelUI implements PanelUI {
  public JButton cancelButton;
  public JPasswordField confirmNewPasswordInput;
  public JLabel confirmNewPasswordLabel;
  public JPasswordField enterNewPasswordInput;
  public JLabel enterNewPasswordLabel;
  public JPasswordField enterPasswordInput;
  public JLabel enterPasswordLabel;
  public JButton okButton;

  public PasswordEditPanelUI() {}

  @Override
  public void setupUI(CommandPanel panel) {
    java.awt.GridBagConstraints gridBagConstraints;

    enterPasswordLabel = new javax.swing.JLabel();
    enterNewPasswordLabel = new javax.swing.JLabel();
    confirmNewPasswordLabel = new javax.swing.JLabel();
    enterPasswordInput = new javax.swing.JPasswordField();
    enterNewPasswordInput = new javax.swing.JPasswordField();
    confirmNewPasswordInput = new javax.swing.JPasswordField();
    okButton = new javax.swing.JButton();
    cancelButton = new javax.swing.JButton();

    JPanel fieldsPanel = new JPanel();
    fieldsPanel.setLayout(new java.awt.GridBagLayout());

    enterPasswordLabel.setText("Enter Password :");
    GuiBorderLabelUtil.boldText(enterPasswordLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(58, 27, 0, 0);
    fieldsPanel.add(enterPasswordLabel, gridBagConstraints);

    enterNewPasswordLabel.setText("Enter New Password :");
    GuiBorderLabelUtil.boldText(enterNewPasswordLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(22, 27, 0, 0);
    fieldsPanel.add(enterNewPasswordLabel, gridBagConstraints);

    confirmNewPasswordLabel.setText("Confirm New Password :");
    GuiBorderLabelUtil.boldText(confirmNewPasswordLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(22, 27, 0, 0);
    fieldsPanel.add(confirmNewPasswordLabel, gridBagConstraints);

    enterPasswordInput.setBackground(new java.awt.Color(56, 56, 77));
    enterPasswordInput.setPreferredSize(new java.awt.Dimension(124, 28));

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 65;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(54, 25, 0, 41);
    fieldsPanel.add(enterPasswordInput, gridBagConstraints);

    enterNewPasswordInput.setBackground(new java.awt.Color(56, 56, 77));
    enterNewPasswordInput.setPreferredSize(new java.awt.Dimension(124, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 65;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(18, 25, 0, 41);
    fieldsPanel.add(enterNewPasswordInput, gridBagConstraints);

    confirmNewPasswordInput.setBackground(new java.awt.Color(56, 56, 77));
    confirmNewPasswordInput.setPreferredSize(new java.awt.Dimension(124, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 65;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(18, 25, 0, 41);
    fieldsPanel.add(confirmNewPasswordInput, gridBagConstraints);

    JPanel buttonPanel = new JPanel();
    okButton.setText("OK");
    okButton.setActionCommand(EditPasswordActionCommand.OK.str);
    okButton.addActionListener(panel);
    buttonPanel.add(okButton);
    buttonPanel.add(Box.createHorizontalStrut(40));

    cancelButton.setText("Cancel");
    cancelButton.setActionCommand(EditPasswordActionCommand.CANCEL.str);
    cancelButton.addActionListener(panel);
    buttonPanel.add(cancelButton);

    panel.setLayout(new GridLayoutCustom(3, 1));
    panel.add(fieldsPanel);
    panel.add(buttonPanel);
    panel.add(Box.createVerticalStrut(40));
    panel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder("Edit Password")));
  }

  /** Return current password. */
  public String getCurrentPassword() {
    return new String(enterPasswordInput.getPassword());
  }

  /** Return new password. */
  public String getNewPasword() {
    return new String(enterNewPasswordInput.getPassword());
  }

  /** Return confirm new password. */
  public String getConfirmNewPasword() {
    return new String(confirmNewPasswordInput.getPassword());
  }
}

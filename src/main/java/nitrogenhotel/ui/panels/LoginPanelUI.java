package nitrogenhotel.ui.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.ui.actioncommands.LoginActionCommand;
import nitrogenhotel.ui.utilsgui.GuiBorderLabelUtil;

/** LoginPanelUI builds login fields. */
public class LoginPanelUI implements PanelUI {
  public JTextField tfUsername;
  public JPasswordField pfPassword;

  /**
   * Setups the UI.
   */
  public void setupUI(CommandPanel panel) {
    panel.setBorder(new CompoundBorder(panel.getBorder(), new EmptyBorder(80, 0, 0, 0)));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // panel for storing labels and their respective fields
    JPanel panelField = new JPanel(new GridBagLayout());
    GridBagConstraints cs = new GridBagConstraints();

    // panel for storing Login and Cancel buttons
    JPanel panelButton = new JPanel();
    panelButton.setBorder(BorderFactory.createRaisedBevelBorder());

    cs.fill = GridBagConstraints.HORIZONTAL;

    JLabel label = new JLabel("Please provide your credentials in order to proceed");
    GuiBorderLabelUtil.boldText(label);
    cs.insets = new Insets(10, 5, 0, 5);
    cs.gridx = 0;
    cs.gridy = 0;
    cs.gridwidth = 2;
    panelField.add(label, cs);

    // username label
    JLabel lbUsername = new JLabel("Username");
    cs.insets = new Insets(10, 5, 0, 5);
    cs.gridx = 0;
    cs.gridy = 1;
    cs.gridwidth = 1;
    panelField.add(lbUsername, cs);

    // username field
    tfUsername = new JTextField(20);
    cs.insets = new Insets(10, 0, 0, 10);
    cs.gridx = 1;
    cs.gridy = 1;
    cs.gridwidth = 1;
    tfUsername.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    panelField.add(tfUsername, cs);

    // password label
    JLabel lbPassword = new JLabel("Password");
    cs.insets = new Insets(15, 5, 5, 5);
    cs.gridx = 0;
    cs.gridy = 2;
    cs.gridwidth = 1;
    panelField.add(lbPassword, cs);

    // password field
    pfPassword = new JPasswordField(20);
    cs.insets = new Insets(15, 0, 5, 10);
    cs.gridx = 1;
    cs.gridy = 2;
    cs.gridwidth = 1;
    pfPassword.setBorder(BorderFactory.createLoweredSoftBevelBorder());
    panelField.add(pfPassword, cs);
    panelField.setBorder(BorderFactory.createRaisedBevelBorder());

    JButton btnLogin = new JButton("Login");
    btnLogin.setActionCommand(LoginActionCommand.LOGIN.str);
    btnLogin.addActionListener(panel);

    panelButton.add(btnLogin);

    panel.add(panelField, BorderLayout.CENTER);
    panel.add(panelButton, BorderLayout.PAGE_END);
  }

  /** Returns inputed username. */
  public String getUsername() {
    return tfUsername.getText().trim();
  }

  /** Returns inputed password. */
  public String getPassword() {
    return new String(pfPassword.getPassword());
  }
}

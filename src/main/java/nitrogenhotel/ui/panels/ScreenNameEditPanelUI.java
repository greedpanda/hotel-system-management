package nitrogenhotel.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.ui.actioncommands.EditScreenNameActionCommand;
import nitrogenhotel.ui.utilsgui.GridLayoutCustom;
import nitrogenhotel.ui.utilsgui.GuiBorderLabelUtil;

/** EditScreenNamePanelUI that builds edit screen name fields. */
public class ScreenNameEditPanelUI implements PanelUI {
  public JButton cancelButton;
  public JButton okButton;
  public JTextField screenNameInput;
  public JLabel screenNameLabel;

  @Override
  public void setupUI(CommandPanel panel) {
    panel.setLayout(new GridLayoutCustom(4, 0, 0, 0));

    JPanel inputPanel = new JPanel();
    JPanel labelPanel = new JPanel();

    JPanel buttonPanel = new JPanel();

    screenNameLabel = new JLabel(
        "<html><span style='font-size:11px; font-weight:bold'>" + "Enter a new screen name:" + "</span></html>");
    labelPanel.add(screenNameLabel);
    screenNameInput = new JTextField();
    screenNameInput.setToolTipText("Enter Screen Name");
    screenNameInput.setPreferredSize(new java.awt.Dimension(200, 30));
    inputPanel.add(screenNameInput);

    okButton = new JButton("OK");
    okButton.setActionCommand(EditScreenNameActionCommand.OK.str);
    okButton.addActionListener(panel);

    cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand(EditScreenNameActionCommand.CANCEL.str);
    cancelButton.addActionListener(panel);
    buttonPanel.add(okButton);
    buttonPanel.add(Box.createHorizontalStrut(60));
    buttonPanel.add(cancelButton);

    panel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder("Edit Screen Name")));
    panel.add(Box.createVerticalStrut(50));
    panel.add(labelPanel);
    panel.add(inputPanel);
    panel.add(buttonPanel);

  }

  /** Return new screen name. */
  public String getScreenName() {
    return screenNameInput.getText().trim();
  }
}

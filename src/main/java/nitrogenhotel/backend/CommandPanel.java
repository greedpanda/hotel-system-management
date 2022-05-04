package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import nitrogenhotel.ui.panels.PanelUI;

/**
 * Abstract base class for all command panels. It encapsulates its corresponding UI and abstracts
 * the run logic of the command to be implemented.
 */
public abstract class CommandPanel extends JPanel implements ActionListener {
  protected PanelUI ui;

  public CommandPanel(PanelUI ui) {
    this.ui = ui;
    setupUI();
  }

  public abstract boolean run();

  public abstract void actionPerformed(ActionEvent actionEvent);

  protected void setupUI() {
    ui.setupUI(this);
  }
}

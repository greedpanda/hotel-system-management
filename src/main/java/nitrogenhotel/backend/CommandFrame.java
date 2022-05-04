package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import nitrogenhotel.ui.frames.FrameUI;

/**
 * Setup for the main frame of the application. Each subclass implements the ActionListener
 * interface in order to handle interesting ActionEvents.
 */
public abstract class CommandFrame extends JFrame implements ActionListener {
  protected FrameUI ui;

  public CommandFrame(FrameUI ui) {
    this.ui = ui;
  }

  public abstract void actionPerformed(ActionEvent ae);

  protected void setupUI() {
    ui.setupUI(this);
  }
}

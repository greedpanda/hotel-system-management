package nitrogenhotel.ui.utilsgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Setup for the main frame of the application. Each subclass implements the ActionListener
 * interface in order to handle interesting ActionEvents.
 */
public class RoomInputListener implements ActionListener {

  public void actionPerformed(ActionEvent ae) {
    System.out.println(ae.toString());
  }
}

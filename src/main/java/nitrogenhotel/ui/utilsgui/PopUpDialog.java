package nitrogenhotel.ui.utilsgui;

import javax.swing.JOptionPane;

/** Creates error or info pop ups depending on input message. */
public class PopUpDialog {

  /** Error message pop up. */
  public static void warn(String msg) {
    JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  /** Info message pop up. */
  public static void info(String msg, String title) {
    JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
  }
}

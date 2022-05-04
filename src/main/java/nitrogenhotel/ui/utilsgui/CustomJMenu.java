package nitrogenhotel.ui.utilsgui;

import java.awt.Point;
import javax.swing.JMenu;

/** Class extends JMenu and customise all JMenuItems to pop up to the right. */
public class CustomJMenu extends JMenu {

  private static final long serialVersionUID = -4324896320875874445L;

  public CustomJMenu(String string) {
    super(string);
  }

  /**
   * https://stackoverflow.com/questions/26506060/expand-jmenu-to-left-while-retaining-default-text-alignment.
   */
  public Point getPopupMenuOrigin() {
    return new Point(this.getPreferredSize().width, 0);
  }
}

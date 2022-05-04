package nitrogenhotel.ui.utilsgui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

/** Creates titled border in gui panels, bolds text in labels. */
public class GuiBorderLabelUtil {

  /** Creates titled border in gui panels. */
  public static TitledBorder createTitleBorder(String str) {
    String titleBorder = "<html><span style='font-size:11px; font-weight:bold'>" + str + "</span></html>";
    TitledBorder border = new TitledBorder(BorderFactory.createLineBorder(new Color(69, 61, 85)), titleBorder);
    return border;
  }

  /** Creates bolds text in labels. */
  public static void boldText(JLabel label) {
    Font font = label.getFont();
    label.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
    label.setForeground(Color.lightGray);
  }
}

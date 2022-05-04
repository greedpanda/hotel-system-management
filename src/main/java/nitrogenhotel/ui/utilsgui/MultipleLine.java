package nitrogenhotel.ui.utilsgui;

import java.awt.Component;
import java.awt.FontMetrics;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/** Custom renderer that wraps text inside cells. */
public class MultipleLine extends JTextArea implements TableCellRenderer {
  int rowHeight = 0;

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int column) {

    if (value != null) {
      this.setText(value.toString());
    } else {
      this.setText("");
    }

    this.setWrapStyleWord(true);
    this.setLineWrap(true);
    this.setOpaque(false);

    FontMetrics fm = getFontMetrics(this.getFont());
    int fontHeight = fm.getHeight() + table.getRowMargin();
    int textLength = fm.stringWidth(getText());
    int colWidth = table.getColumnModel().getColumn(column).getWidth();
    int lines = ((int) Math.ceil((double) textLength / colWidth)) + 1;
    int height = fontHeight * lines + 4;

    table.setRowHeight(row, height);
    // if focused then set color to flatlaf violet theme
    if (isSelected) {
      this.setOpaque(true);
      this.setBackground(UIManager.getColor("Table.selectionBackground"));
    }
    return this;
  }
}

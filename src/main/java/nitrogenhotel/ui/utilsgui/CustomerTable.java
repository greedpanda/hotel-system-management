package nitrogenhotel.ui.utilsgui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.utils.PaymentMethod;

/**
 * CustomerTable is inherited by CustomerEditPanelUI.
 */
public class CustomerTable extends AbstractTableModel {

  public JTable table;
  private JTableHeader tableHeader;
  private DefaultTableCellRenderer centerRenderer;
  private JScrollPane pane;
  public final String[] columnNames = new String[] { "customerID", "publicID", "customerName", "address",
      "paymentMethod" };
  private final Class[] columnClass = new Class[] { Integer.class, String.class, String.class, String.class,
      String.class };
  public List<Customer> customerList;
  private Map<Integer, Customer> editedCustomerMap;
  private Map<Integer, String> initialCustomerID;

  /**
   * CustomerTable constructor.
   */
  public CustomerTable(List<Customer> customerList) {
    this.customerList = customerList;
    editedCustomerMap = new HashMap<>();
    initialCustomerID = new HashMap<>();

    for (Customer customer : customerList) {
      initialCustomerID.put(customer.getCustomerID(), customer.getPublicID());
    }

  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return columnClass[columnIndex];
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public int getRowCount() {
    return customerList.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Customer row = customerList.get(rowIndex);
    if (0 == columnIndex) {
      return row.getCustomerID();
    } else if (1 == columnIndex) {
      return row.getPublicID();
    } else if (2 == columnIndex) {
      return row.getCustomerName();
    } else if (3 == columnIndex) {
      return row.getAddress();
    } else if (4 == columnIndex) {
      return row.getPaymentMethod();
    }
    return null;
  }

  @SuppressFBWarnings(value = "DLS_DEAD_LOCAL_STORE", justification = "cellValue is being used for GUI text checking.")
  @Override
  public void setValueAt(Object cellValue, int rowIndex, int columnIndex) {
    String strEmptyCell = "Empty cell value";
    String strIdHasBeenUsed = "This public ID has been used";
    String strIdExists = "Public ID already exists in database";
    Customer row = customerList.get(rowIndex);
    if (1 == columnIndex) {
      boolean validInputs = true;

      // Compare with the initial customer public ids
      for (Map.Entry<Integer, String> entry : initialCustomerID.entrySet()) {
        if (entry.getValue().equals(cellValue) && row.getCustomerID() != entry.getKey()) {
          cellValue = initialCustomerID.get(row.getCustomerID());
          validInputs = false;
          PopUpDialog.warn(strIdExists);
          break;
        }
      }
      if (validInputs) {
        // Compare with the current customer public ids
        for (Customer customer : customerList) {
          if (customer.getPublicID().equals(cellValue)) {
            validInputs = false;
            cellValue = customer.getPublicID();
            PopUpDialog.warn(strIdHasBeenUsed);
            break;
          }
        }
      }
      if (validInputs) {
        if (!cellValue.equals("")) {
          row.setPublicID((String) cellValue);
          editedCustomerMap.put(row.getCustomerID(), row);
        } else {
          validInputs = false;
          cellValue = row.getPublicID();
          PopUpDialog.warn(strEmptyCell);
        }
      }

    } else if (2 == columnIndex) {
      if (!cellValue.equals("")) {
        row.setCustomerName((String) cellValue);
        editedCustomerMap.put(row.getCustomerID(), row);
      } else {
        cellValue = row.getCustomerName();
        PopUpDialog.warn(strEmptyCell);
      }
    } else if (3 == columnIndex) {
      if (!cellValue.equals("")) {
        row.setAddress((String) cellValue);
        editedCustomerMap.put(row.getCustomerID(), row);
      } else {
        cellValue = row.getAddress();
        PopUpDialog.warn(strEmptyCell);
      }
    } else if (4 == columnIndex) {
      row.setPaymentMethod(cellValue.toString());
      editedCustomerMap.put(row.getCustomerID(), row);
    }
  }

  /**
   * Creates table for display or editing customers.
   */
  public void buildCustomerTable(JPanel customerPanel, String str, String str2) {

    customerPanel.setLayout(new GridLayoutCustom(3, 0, 0, 5));

    // titled border
    customerPanel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder(str)));
   
    table = new JTable(this);
    tableHeader = table.getTableHeader();
    tableHeader.setBackground(new Color(69, 61, 85));

    table.setGridColor(new Color(87, 83, 93));
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(true);
    table.setPreferredScrollableViewportSize(new Dimension(450, 250));
    changeName(table, 0, "ID");
    changeName(table, 1, "Public ID");
    changeName(table, 2, "Name");
    changeName(table, 3, "Address");
    changeName(table, 4, "Payment");

    // search filter field and label
    JTextField filterField = RowFilterUtil.createRowFilter(table);
    JLabel searchLabel = new JLabel("Table search:");
    GuiBorderLabelUtil.boldText(searchLabel);
    JPanel jp = new JPanel();
    jp.add(searchLabel);
    jp.add(filterField);

    centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    table.getColumn("Payment").setCellRenderer(centerRenderer);
    table.getColumn("ID").setCellRenderer(centerRenderer);
    table.getColumn("Public ID").setCellRenderer(new MultipleLine());
    table.getColumn("Name").setCellRenderer(new MultipleLine());
    table.getColumn("Address").setCellRenderer(new MultipleLine());

    // Combo box for payment
    TableColumn paymentColumn = table.getColumn("Payment");
    JComboBox<String> paymentcomboBox = new JComboBox<>();
    for (PaymentMethod value : PaymentMethod.values()) {
      paymentcomboBox.addItem(value.toString());
    }
    paymentColumn.setCellEditor(new DefaultCellEditor(paymentcomboBox));

    pane = new JScrollPane(table);
    pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedSoftBevelBorder(), str2));

    customerPanel.add(jp);
    customerPanel.add(pane);
  }

  /**
   * Changes names of header columns.
   */
  private void changeName(JTable table, int colIndex, String colName) {
    table.getColumnModel().getColumn(colIndex).setHeaderValue(colName);
  }

  /**
   * Returns edited customers.
   */
  public Map<Integer, Customer> getEditedCustomerData() {
    return editedCustomerMap;
  }
}

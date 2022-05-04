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
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;

/**
 * RoomTable. The whole idea is taken from
 * https://www.codejava.net/java-se/swing/editable-jtable-example.
 */
public class RoomTable extends AbstractTableModel {

  public JTable table;
  private JTableHeader tableHeader;
  private DefaultTableCellRenderer centerRenderer;
  private JScrollPane pane;
  private MultipleLine wrapper;

  public final String[] columnNames = new String[] { "number", "size", "nbBeds", "floor", "note" };
  private final Class[] columnClass = new Class[] { Integer.class, String.class, Integer.class, Integer.class,
      String.class };
  public List<Room> roomList;
  public List<RoomType> sizeList;
  public List<Floor> floorList;
  private Map<Integer, Room> editedRoomMap;
  private Map<Integer, Integer> initialRoomNumbers;

  /** Hash Map that provides the room edited to the backend. */
  public RoomTable(List<Room> roomList, List<Floor> floorList, List<RoomType> sizeList) {
    this.roomList = roomList;
    this.sizeList = sizeList;
    this.floorList = floorList;
    editedRoomMap = new HashMap<>();
    initialRoomNumbers = new HashMap<>();

    for (Room room : roomList) {
      initialRoomNumbers.put(room.getRoomID(), room.getNumber());
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
    return roomList.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Room row = roomList.get(rowIndex);
    if (0 == columnIndex) {
      return row.getNumber();
    } else if (1 == columnIndex) {
      return row.getSize();
    } else if (2 == columnIndex) {
      return row.getNbBeds();
    } else if (3 == columnIndex) {
      return row.getFloor();
    } else if (4 == columnIndex) {
      return row.getNote();
    }
    return null;
  }

  @SuppressFBWarnings(value = "DLS_DEAD_LOCAL_STORE", justification = "cellValue is being used for GUI text checking.")
  @Override
  public void setValueAt(Object cellValue, int rowIndex, int columnIndex) {
    Room row = roomList.get(rowIndex);
    String strRoomExists = "Room number already exists in database";
    String strNumberUsed = "This number has been used";
    String strOnlyPosIntegers = "Only positive integers";

    if (0 == columnIndex) {
      boolean validInputs = true;

      // Compare with the initial room numbers
      for (Map.Entry<Integer, Integer> entry : initialRoomNumbers.entrySet()) {
        if (entry.getValue().equals(cellValue) && row.getRoomID() != entry.getKey()) {
          cellValue = initialRoomNumbers.get(row.getRoomID());
          validInputs = false;
          PopUpDialog.warn(strRoomExists);
          break;
        }
      }

      if (validInputs) {
        // Compare with the current room numbers
        for (Room room : roomList) {
          if (room.getNumber() == ((Integer) cellValue)) {
            validInputs = false;
            cellValue = room.getNumber();
            PopUpDialog.warn(strNumberUsed);
            break;
          }
        }
      }

      if (validInputs) {
        if ((Integer) cellValue > 0) {
          row.setNumber((Integer) cellValue);
          editedRoomMap.put(row.getRoomID(), row);
        } else {
          validInputs = false;
          cellValue = row.getNumber();
          PopUpDialog.warn(strOnlyPosIntegers);
        }
      }
    } else if (1 == columnIndex) {
      row.setSize((String) cellValue);
      editedRoomMap.put(row.getRoomID(), row);
    } else if (2 == columnIndex) {
      if ((Integer) cellValue > 0) {
        row.setNbBeds((Integer) cellValue);
        editedRoomMap.put(row.getRoomID(), row);
      } else {
        cellValue = row.getNbBeds();
        PopUpDialog.warn(strOnlyPosIntegers);
      }

    } else if (3 == columnIndex) {
      row.setFloor((Integer) cellValue);
      editedRoomMap.put(row.getRoomID(), row);
    } else if (4 == columnIndex) {
      row.setNote((String) cellValue);
      editedRoomMap.put(row.getRoomID(), row);
    }
  }

  /** Creates table for display or editing rooms. */
  public void buildRoomTable(JPanel roomDetailsPanel, String str, String str2) {

    roomDetailsPanel.setLayout(new GridLayoutCustom(3, 0, 0, 5));

    // titled border
    roomDetailsPanel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder(str)));

    table = new JTable(this);
    tableHeader = table.getTableHeader();
    tableHeader.setBackground(new Color(69, 61, 85));
    table.setGridColor(new Color(87, 83, 93));
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(true);
    table.setPreferredScrollableViewportSize(new Dimension(430, 250));
    changeName(table, 0, "Number");
    changeName(table, 1, "Size");
    changeName(table, 2, "Beds");
    changeName(table, 3, "Floor");
    changeName(table, 4, "Note");

    // search filter field and label
    JTextField filterField = RowFilterUtil.createRowFilter(table);
    JLabel searchLabel = new JLabel("Table search:");
    GuiBorderLabelUtil.boldText(searchLabel);
    JPanel jp = new JPanel();
    jp.add(searchLabel);
    jp.add(filterField);

    wrapper = new MultipleLine();

    // positioning to the center
    centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    // Apply for the first four columns only
    for (int column = 0; column < 4; column++) {
      table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
    }

    // For the fifth column, apply line wrapping instead
    table.getColumn("Note").setCellRenderer(wrapper);
    // Combo box for room size
    TableColumn sizeColumn = table.getColumn("Size");
    JComboBox<String> sizecomboBox = new JComboBox<>();
    for (RoomType size : sizeList) {
      sizecomboBox.addItem(size.size);
    }
    sizeColumn.setCellEditor(new DefaultCellEditor(sizecomboBox));
    //

    // Combo box for floor
    TableColumn floorColumn = table.getColumn("Floor");
    JComboBox<Integer> floocomboBox = new JComboBox<>();
    for (Floor floor : floorList) {
      floocomboBox.addItem(floor.getFloorNumber());
    }
    floorColumn.setCellEditor(new DefaultCellEditor(floocomboBox));
    //

    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    pane = new JScrollPane(table);
    pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedSoftBevelBorder(), str2));

    roomDetailsPanel.add(jp);
    roomDetailsPanel.add(pane);
  }

  /** Returns edited rooms. */
  public Map<Integer, Room> getEditedRoomData() {
    return editedRoomMap;
  }

  /** Changes names of header columns. */
  private void changeName(JTable table, int colIndex, String colName) {
    table.getColumnModel().getColumn(colIndex).setHeaderValue(colName);
  }

  /** Checks if user selected a row to delete, not just pressed delete button. */
  public boolean isSelectedRowToDelete() {
    if (table.getSelectedRow() != -1) {
      return true;
    } else {
      return false;
    }
  }

  /** Returns room in order to delete it from database. */
  public Room getSelectedRoomToDelete() {
    return roomList.get(table.convertRowIndexToModel(table.getSelectedRow()));
  }

  /** Deletes row from a table. */
  public void removeRow() {
    int row = table.convertRowIndexToModel(table.getSelectedRow());
    roomList.remove(row);
    fireTableRowsDeleted(row, row);
  }
}

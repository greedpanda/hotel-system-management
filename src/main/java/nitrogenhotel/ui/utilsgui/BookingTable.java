package nitrogenhotel.ui.utilsgui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import nitrogenhotel.backend.CalendarSupportStart;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.utils.CouldNotParseDate;
import nitrogenhotel.utils.DateConverter;
import org.threeten.extra.Interval;
import org.tinylog.Logger;

/**
 * Parent class for booking tables.
 */
public class BookingTable extends AbstractTableModel implements ActionListener, DateChangeListener {

  public JTable table;
  private JTableHeader tableHeader;
  private DefaultTableCellRenderer centerRenderer;
  private JScrollPane pane;
  public final String[] columnNames = new String[] { "reservationID", "customerName", "number", "startDate", "endDate",
      "paid" };
  private final Class[] columnClass = new Class[] { Integer.class, String.class, Integer.class, LocalDate.class,
      LocalDate.class, Boolean.class };
  public List<Booking> bookingList;
  private Map<Integer, Booking> editedBookingMap;
  public List<Customer> customerList;
  public List<Room> roomList;
  public List<RoomType> roomTypeList;
  private HashMap<Integer, Room> roomFullList = new HashMap<>();
  private List<Room> roomsAccordingToRoomType;
  private List<Booking> bookingsAccordingToRoomType;
  private JComboBox<String> roomTypeComboBox;
  private JComboBox<String> customerComboBox;
  private JComboBox<Integer> roomComboBox;
  private JComboBox<Integer> roomInput;
  private JCheckBox paidCheckBox;
  private JButton confirmBtn;
  private JDialog dialog;
  private DatePickerSettings availableDateSettings;
  private DatePickerSettings startDateSettings;
  private DatePickerSettings endDateSettings;
  private DatePicker availableDatePanel;
  private DatePicker startDatePanel;
  private DatePicker endDatePanel;
  private CalendarSupportStart csAvailableDate;
  public int rowIndex;

  /**
   * Constructor for BookingTable used in booking overview.
   *
   * @param bookingList The list of bookings.
   */
  public BookingTable(List<Booking> bookingList, List<Room> roomList) {
    this.bookingList = bookingList;
    this.roomList = roomList;
    editedBookingMap = new HashMap<>();
  }

  /**
   * Constructor for BookingTable used in edit booking.
   */
  public BookingTable(List<Booking> bookingList, List<Customer> customerList, List<Room> roomList,
      List<RoomType> roomTypeList) {
    this(bookingList, roomList);
    this.customerList = customerList;
    this.roomList = roomList;
    this.roomTypeList = roomTypeList;
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
    return bookingList.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Booking row = bookingList.get(rowIndex);
    if (0 == columnIndex) {
      return row.getReservation().getReservationID();
    } else if (1 == columnIndex) {
      return row.getCustomer().getCustomerName() + " - " + row.getCustomer().getPublicID();
    } else if (2 == columnIndex) {
      return row.getRoom().getNumber();
    } else if (3 == columnIndex) {
      return DateConverter.convertToDate(row.getReservation().getStartDate());
    } else if (4 == columnIndex) {
      return DateConverter.convertToDate(row.getReservation().getEndDate());
    } else if (5 == columnIndex) {
      return row.getReservation().getPaid();
    }
    return null;
  }

  /**
   * Updates bookingList and adds data to editedBookingMap after confirm.
   */
  private void setValueAt(int rowIndex) {
    Booking row = bookingList.get(rowIndex);
    row.getReservation().setStartDate(convertStartDate());
    row.getReservation().setEndDate(convertEndDate());
    editedBookingMap.put(row.getReservation().getReservationID(), row);
    if (!getCustomerData().equals(row.getCustomer().getCustomerName() + " - " + row.getCustomer().getPublicID())) {
      for (Customer customer : customerList) {
        if ((customer.getCustomerName() + " - " + customer.getPublicID()).equals(getCustomerData())) {
          row.getReservation().setCustomerID(customer.getCustomerID());
          row.setCustomer(customer);
          editedBookingMap.put(row.getReservation().getReservationID(), row);
        }
      }
    }
    if (!getRoomNumber(roomComboBox).equals(row.getRoom().getNumber())) {
      for (Room room : roomList) {
        if (room.getNumber() == getRoomNumber(roomComboBox)) {
          row.getReservation().setRoomID(room.getRoomID());
          row.setRoom(room);
          editedBookingMap.put(row.getReservation().getReservationID(), row);
        }
      }
    }
    if (!getIsPaid().equals(row.getReservation().getPaid())) {
      row.getReservation().setPaid(getIsPaid());
      editedBookingMap.put(row.getReservation().getReservationID(), row);
    }

  }

  /**
   * Creates table for display or editing bookings.
   */
  public void buildBookingTable(JPanel bookingPanel, String str, String str2) {

    bookingPanel.setLayout(new GridLayoutCustom(3, 0, 0, 5));

    // titled border
    bookingPanel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder(str)));

    table = new JTable(this);
    table.convertRowIndexToView(0);
    tableHeader = table.getTableHeader();
    tableHeader.setBackground(new Color(69, 61, 85));

    table.setGridColor(new Color(87, 83, 93));
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(true);
    table.setPreferredScrollableViewportSize(new Dimension(450, 250));
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    changeName(table, 0, "ID");
    changeName(table, 1, "Customer");
    changeName(table, 2, "Room");
    changeName(table, 3, "Start date");
    changeName(table, 4, "End date");
    changeName(table, 5, "Paid");

    centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    table.getColumnModel().getColumn(0).setPreferredWidth(10);
    table.getColumnModel().getColumn(1).setPreferredWidth(60);
    table.getColumnModel().getColumn(2).setPreferredWidth(25);
    table.getColumnModel().getColumn(5).setPreferredWidth(25);
    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    table.getColumnModel().getColumn(1).setCellRenderer(new MultipleLine());
    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

    // see available dates for rooms functionality
    csAvailableDate = new CalendarSupportStart();
    csAvailableDate.refreshBookings(roomList.get(0));

    JLabel roomLabel = new JLabel("Room:");
    GuiBorderLabelUtil.boldText(roomLabel);
    roomInput = new JComboBox<>();

    for (Room room : roomList) {
      roomFullList.put(room.getNumber(), room);
      roomInput.addItem(room.getNumber());
    }
    roomInput.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == roomInput) {
          csAvailableDate.refreshBookings(roomFullList.get(getRoomNumber(roomInput)));
        }
      }
    });

    availableDateSettings = new DatePickerSettings();
    availableDateSettings.setColor(DateArea.BackgroundTodayLabel, new Color(44, 44, 59));
    availableDateSettings.setColor(DateArea.BackgroundClearLabel, new Color(44, 44, 59));
    availableDateSettings.setColor(DateArea.BackgroundOverallCalendarPanel, new Color(44, 44, 59));
    availableDateSettings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, new Color(44, 44, 59));
    availableDateSettings.setColor(DateArea.CalendarBackgroundNormalDates, new Color(76, 153, 0));
    availableDateSettings.setColor(DateArea.BackgroundTodayLabel, new Color(44, 44, 59));
    availableDatePanel = new DatePicker(availableDateSettings);
    availableDatePanel.getComponentDateTextField().setVisible(false);
    availableDatePanel.getComponentToggleCalendarButton().setText("View available dates");
    availableDateSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(10));
    availableDateSettings.setVetoPolicy(csAvailableDate);
    availableDateSettings.setVisibleClearButton(false);

    // search filter field and label
    JTextField filterField = RowFilterUtil.createRowFilter(table);
    JLabel searchLabel = new JLabel("Table search:");
    GuiBorderLabelUtil.boldText(searchLabel);

    JPanel jp = new JPanel();
    jp.setLayout(new GridLayoutCustom(2, 1));
    JPanel availableDatesPanel = new JPanel();
    JPanel searchPanel = new JPanel();
    availableDatesPanel.add(roomLabel);
    availableDatesPanel.add(roomInput);
    availableDatesPanel.add(availableDatePanel);
    searchPanel.add(searchLabel);
    searchPanel.add(filterField);
    jp.add(availableDatesPanel);
    jp.add(searchPanel);

    pane = new JScrollPane(table);
    pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedSoftBevelBorder(), str2));

    bookingPanel.add(jp);
    bookingPanel.add(pane);
  }

  /**
   * Creates pop up dialog for editing booking.
   */
  public void dialogForEditing(Booking row, int rowIndex) {
    if (rowIndex != -1) {

      // room type label and combo box
      JLabel roomTypeLabel = new JLabel(" Room type");
      roomTypeLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
      GuiBorderLabelUtil.boldText(roomTypeLabel);
      roomTypeComboBox = new JComboBox<>();
      roomTypeComboBox
          .setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 0, 0, 0), roomTypeComboBox.getBorder()));
      for (RoomType roomType : roomTypeList) {
        roomTypeComboBox.addItem(roomType.size);
      }
      roomTypeComboBox.setSelectedItem(row.getRoom().getSize());
      roomTypeComboBox.addActionListener(this);

      // start date label and field
      LocalDate start = DateConverter.convertToDate(row.getReservation().getStartDate());
      LocalDate end = DateConverter.convertToDate(row.getReservation().getEndDate());
      JLabel startLabel = new JLabel(" Start Date");
      GuiBorderLabelUtil.boldText(startLabel);
      Color color = new Color(44, 44, 59);
      Color color2 = new java.awt.Color(56, 56, 77);
      startDateSettings = new DatePickerSettings();
      changeDatePickerSettingsColor(startDateSettings, color, color2);
      startDatePanel = new DatePicker(startDateSettings);
      startDatePanel.setDate(start);
      startDatePanel.addDateChangeListener(this);
      startDatePanel.getComponentDateTextField().setBorder(roomInput.getBorder());
      startDatePanel.getComponentDateTextField().setPreferredSize(new java.awt.Dimension(153, roomInput.getHeight()));
      startDateSettings.setDateRangeLimits(start, LocalDate.now().plusYears(10));

      // end date label and field
      JLabel endLabel = new JLabel(" End Date");
      GuiBorderLabelUtil.boldText(endLabel);
      endDateSettings = new DatePickerSettings();
      changeDatePickerSettingsColor(endDateSettings, color, color2);
      endDatePanel = new DatePicker(endDateSettings);
      endDatePanel.setDate(end);
      endDatePanel.addDateChangeListener(this);
      endDatePanel.getComponentDateTextField().setBorder(roomInput.getBorder());
      endDatePanel.getComponentDateTextField().setPreferredSize(new java.awt.Dimension(153, roomInput.getHeight()));
      endDateSettings.setDateRangeLimits(start, LocalDate.now().plusYears(10));

      // initial rooms combobox (can be changed later with action events)
      JLabel roomLabel = new JLabel(" Room No.");
      GuiBorderLabelUtil.boldText(roomLabel);
      roomComboBox = new JComboBox<>();
      roomTypeDateFilter(row.getRoom().getSize(), row.getReservation().getStartDate(),
          row.getReservation().getEndDate());
      roomComboBox.setMaximumRowCount(4);

      // customer combobox
      JLabel customerLabel = new JLabel(" Customer");
      GuiBorderLabelUtil.boldText(customerLabel);
      customerComboBox = new JComboBox<>();
      customerComboBox.addItem(row.getCustomer().getCustomerName() + " - " + row.getCustomer().getPublicID());
      for (Customer customer : customerList) {
        if (!customer.getPublicID().equals(row.getCustomer().getPublicID())) {
          customerComboBox.addItem(customer.getCustomerName() + " - " + customer.getPublicID());
        }
      }
      customerComboBox.setMaximumRowCount(4);

      // paid checkbox
      JLabel paidLabel = new JLabel(" Paid status");
      GuiBorderLabelUtil.boldText(paidLabel);
      paidCheckBox = new JCheckBox("", row.getReservation().getPaid());

      // confirm buttn and button panel
      confirmBtn = new JButton("Confirm");
      confirmBtn.addActionListener(this);
      JPanel panelButton = new JPanel();
      panelButton.add(Box.createHorizontalStrut(40));
      panelButton.add(confirmBtn);
      panelButton.add(Box.createHorizontalStrut(40));

      dialog = new JDialog(new JFrame(),
          "Editing form for Reservation ID No. " + String.valueOf(row.getReservation().getReservationID()));
      JPanel panelFields = new JPanel();
      panelFields.setLayout(new GridLayoutCustom(6, 2, 10, 10));

      JPanel panel = new JPanel();
      panel.setLayout(new GridLayoutCustom(2, 1, 10, 10));

      panelFields.add(roomTypeLabel);
      panelFields.add(roomTypeComboBox);
      panelFields.add(startLabel);
      panelFields.add(startDatePanel);
      panelFields.add(endLabel);
      panelFields.add(endDatePanel);
      panelFields.add(roomLabel);
      panelFields.add(roomComboBox);
      panelFields.add(customerLabel);
      panelFields.add(customerComboBox);
      panelFields.add(paidLabel);
      panelFields.add(paidCheckBox);
      panel.add(panelFields);
      panel.add(panelButton);
      dialog.add(panel);
      dialog.pack();
      dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      dialog.setLocationRelativeTo(null);
      dialog.setVisible(true);

    }
  }

  /**
   * Filters room numbers according to available dates.
   */
  private void roomTypeDateFilter(String roomType, Instant startDate, Instant endDate) {

    roomComboBox.removeAllItems();

    Booking row = bookingList.get(rowIndex);

    List<Integer> filteredRoomNumbers = new ArrayList<>();

    for (Room room : roomTypeFilter(roomType)) {
      filteredRoomNumbers.add(room.getNumber());
    }
    for (Booking b : bookingRoomTypeFilter(roomType)) {
      Interval existingBooking = Interval.of(b.getReservation().getStartDate(), b.getReservation().getEndDate());
      Interval newBooking = Interval.of(startDate, endDate);
      if (newBooking.overlaps(existingBooking)
          && b.getReservation().getReservationID() != row.getReservation().getReservationID()) {
        filteredRoomNumbers.remove(Integer.valueOf(b.getRoom().getNumber()));
      }
    }
    Collections.sort(filteredRoomNumbers);
    for (Integer roomNumber : filteredRoomNumbers) {
      roomComboBox.addItem(roomNumber);
    }
  }

  /**
   * Filters rooms according to room type.
   */
  private List<Room> roomTypeFilter(String roomType) {
    roomsAccordingToRoomType = new ArrayList<>();
    for (Room room : roomList) {
      if (room.getSize().equals(roomType)) {
        roomsAccordingToRoomType.add(room);
      }
    }
    return roomsAccordingToRoomType;
  }

  /**
   * Filters bookings according to room type.
   */
  private List<Booking> bookingRoomTypeFilter(String roomType) {
    bookingsAccordingToRoomType = new ArrayList<>();
    for (Booking booking : bookingList) {
      if (booking.getRoom().getSize().equals(roomType)) {
        bookingsAccordingToRoomType.add(booking);
      }
    }
    return bookingsAccordingToRoomType;
  }

  /**
   * Changes names of header columns.
   */
  private void changeName(JTable table, int colIndex, String colName) {
    table.getColumnModel().getColumn(colIndex).setHeaderValue(colName);
  }

  /**
   * Returns edited bookings.
   */
  public Map<Integer, Booking> getEditedBookingData() {
    return editedBookingMap;
  }

  private String getCustomerData() {
    return customerComboBox.getSelectedItem().toString();
  }

  private Integer getRoomNumber(JComboBox<Integer> comboBox) {
    Object selectedItem = comboBox.getSelectedItem();
    if (selectedItem != null) {
      return Integer.valueOf(comboBox.getSelectedItem().toString());
    } else {
      return null;
    }
  }

  private Instant convertStartDate() {
    try {
      return DateConverter.castToInstant(startDatePanel.getDate());
    } catch (CouldNotParseDate e) {
      Logger.warn(e);
      return null;
    }
  }

  private Instant convertEndDate() {
    try {
      return DateConverter.castToInstant(endDatePanel.getDate());
    } catch (CouldNotParseDate e) {
      Logger.warn(e);
      return null;
    }
  }

  private Boolean getIsPaid() {
    return paidCheckBox.isSelected();
  }

  // sets date fields to the previous state in case any error occured.
  private void dateFieldsToPreviousState(String str) {
    startDatePanel.closePopup();
    endDatePanel.closePopup();
    startDatePanel.setDate(DateConverter.convertToDate(bookingList.get(rowIndex).getReservation().getStartDate()));
    endDatePanel.setDate(DateConverter.convertToDate(bookingList.get(rowIndex).getReservation().getEndDate()));
    PopUpDialog.warn(str);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == confirmBtn) {
      if (getRoomNumber(roomComboBox) == null) {
        PopUpDialog.warn("No available rooms for chosen dates");
      } else {
        setValueAt(rowIndex);
        dialog.dispose();
      }

    } else if (e.getSource() == roomTypeComboBox) {
      String roomType = roomTypeComboBox.getSelectedItem().toString();
      roomTypeDateFilter(roomType, convertStartDate(), convertEndDate());
    }
  }

  @Override
  public void dateChanged(DateChangeEvent event) {
    if (convertStartDate() == null || convertEndDate() == null) {
      dateFieldsToPreviousState("Date cannot be converted");
    } else if (convertStartDate().isAfter(convertEndDate()) || convertStartDate().equals(convertEndDate())) {
      dateFieldsToPreviousState("User tried to book a room for 0 or negative days");
    } else {
      String roomType = roomTypeComboBox.getSelectedItem().toString();
      roomTypeDateFilter(roomType, convertStartDate(), convertEndDate());
    }
  }

  private void changeDatePickerSettingsColor(DatePickerSettings settings, Color color, Color color2) {
    settings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, color);
    settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, color);
    settings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, color);
    settings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, color);
    settings.setColor(DatePickerSettings.DateArea.TextFieldBackgroundValidDate, color2);
    settings.setColor(DateArea.DatePickerTextValidDate, Color.lightGray);
    settings.setAllowKeyboardEditing(false);
    settings.setVisibleClearButton(false);
  }

}

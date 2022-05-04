package nitrogenhotel.ui.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.ui.actioncommands.BookingAddActionCommand;
import nitrogenhotel.ui.utilsgui.GridLayoutCustom;
import nitrogenhotel.ui.utilsgui.GuiBorderLabelUtil;
import nitrogenhotel.ui.utilsgui.PopUpDialog;
import nitrogenhotel.utils.CouldNotParseDate;
import nitrogenhotel.utils.DateConverter;
import org.threeten.extra.Interval;

/**
 * Panel for adding bookings.
 */
public class BookingAddPanelUI implements PanelUI, ActionListener, DateChangeListener {

  public JButton cancelButton;
  private JComboBox<String> customerInput;
  private JLabel customerLabel;
  private JLabel endDateLabel;
  private JCheckBox isPaidInput;
  public JButton okButton;
  private JComboBox<Integer> roomInput;
  private JComboBox<String> roomTypeInput;
  private JLabel roomLabel;
  private JLabel roomTypeLabel;
  private JLabel startDateLabel;
  private DatePicker startDatePanel;
  private DatePicker endDatePanel;
  private DatePickerSettings startDateSettings;
  private DatePickerSettings endDateSettings;
  private List<Customer> customerList;
  private List<Room> roomList;
  private List<RoomType> roomTypeList;
  private List<Booking> activeBookingList;
  private HashMap<String, Room> roomFullList = new HashMap<>();
  private HashMap<String, Customer> customerFullList = new HashMap<>();
  private List<Room> roomsAccordingToRoomType;
  private List<Booking> bookingsAccordingToRoomType;

  /**
   * Constructor accepts all customers, rooms, room sizes and active bookings.
   */
  public BookingAddPanelUI(List<Customer> customerList, List<Room> roomList, List<RoomType> roomTypeList,
      List<Booking> activeBookingList) {
    this.customerList = customerList;
    this.roomList = roomList;
    this.roomTypeList = roomTypeList;
    this.activeBookingList = activeBookingList;

  }

  @Override
  public void setupUI(CommandPanel panel) {
    customerLabel = new javax.swing.JLabel();
    roomLabel = new javax.swing.JLabel();
    roomTypeLabel = new javax.swing.JLabel();
    startDateLabel = new javax.swing.JLabel();
    endDateLabel = new javax.swing.JLabel();
    customerInput = new javax.swing.JComboBox<>();
    roomInput = new javax.swing.JComboBox<>();
    roomTypeInput = new javax.swing.JComboBox<>();
    isPaidInput = new javax.swing.JCheckBox();
    okButton = new javax.swing.JButton();
    cancelButton = new javax.swing.JButton();

    startDateSettings = new DatePickerSettings();
    changeDatePickerSettingsColor(startDateSettings);
    startDatePanel = new DatePicker(startDateSettings);
    startDatePanel.getComponentDateTextField().setBorder(roomInput.getBorder());
    startDatePanel.getComponentDateTextField().setPreferredSize(new java.awt.Dimension(153, 28));
    startDatePanel.addDateChangeListener(this);
    startDateSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(10));

    endDateSettings = new DatePickerSettings();
    changeDatePickerSettingsColor(endDateSettings);
    endDatePanel = new DatePicker(endDateSettings);
    endDatePanel.getComponentDateTextField().setBorder(roomInput.getBorder());
    endDatePanel.getComponentDateTextField().setPreferredSize(new java.awt.Dimension(153, 28));
    endDatePanel.addDateChangeListener(this);
    endDateSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(10));

    JPanel fieldsPanel = new JPanel();
    java.awt.GridBagConstraints gridBagConstraints;
    fieldsPanel.setLayout(new java.awt.GridBagLayout());

    customerLabel.setText("Customer:");
    GuiBorderLabelUtil.boldText(customerLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.gridwidth = 5;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(16, 109, 0, 0);
    fieldsPanel.add(customerLabel, gridBagConstraints);

    roomLabel.setText("Room No:");
    GuiBorderLabelUtil.boldText(roomLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(16, 109, 0, 0);
    fieldsPanel.add(roomLabel, gridBagConstraints);

    roomTypeLabel.setText("Room Type:");
    GuiBorderLabelUtil.boldText(roomTypeLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(16, 109, 0, 0);
    fieldsPanel.add(roomTypeLabel, gridBagConstraints);

    startDateLabel.setText("Start Date:");
    GuiBorderLabelUtil.boldText(startDateLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(17, 109, 0, 0);
    fieldsPanel.add(startDateLabel, gridBagConstraints);

    endDateLabel.setText("End Date:");
    GuiBorderLabelUtil.boldText(endDateLabel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(16, 109, 0, 0);
    fieldsPanel.add(endDateLabel, gridBagConstraints);

    customerInput.setBackground(new java.awt.Color(56, 56, 77));
    customerInput.setModel(new javax.swing.DefaultComboBoxModel<>());

    for (Customer customer : customerList) {
      String syntax = customer.getCustomerName() + " - " + customer.getPublicID();
      customerFullList.put(syntax, customer);
      customerInput.addItem(syntax);
    }
    customerInput.setSelectedIndex(-1);

    customerInput.setPreferredSize(new java.awt.Dimension(153, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 15;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(16, 6, 0, 107);
    fieldsPanel.add(customerInput, gridBagConstraints);

    roomInput.setBackground(new java.awt.Color(56, 56, 77));
    roomInput.setModel(new javax.swing.DefaultComboBoxModel<>());
    for (Room room : roomList) {
      roomFullList.put(String.valueOf(room.getNumber()), room);
    }

    roomInput.setPreferredSize(new java.awt.Dimension(153, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 15;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(12, 6, 0, 107);
    fieldsPanel.add(roomInput, gridBagConstraints);

    roomTypeInput.setBackground(new java.awt.Color(56, 56, 77));
    roomTypeInput.setModel(new javax.swing.DefaultComboBoxModel<>());
    for (RoomType type : roomTypeList) {
      roomTypeInput.addItem(type.size);
    }
    roomTypeInput.setSelectedIndex(-1);
    roomTypeInput.addActionListener(this);

    roomTypeInput.setPreferredSize(new java.awt.Dimension(153, 28));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 15;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(12, 6, 0, 107);
    fieldsPanel.add(roomTypeInput, gridBagConstraints);

    startDatePanel.setBackground(new java.awt.Color(44, 44, 59));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 15;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(13, 6, 0, 107);
    fieldsPanel.add(startDatePanel, gridBagConstraints);

    endDatePanel.setBackground(new java.awt.Color(44, 44, 59));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 15;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(12, 6, 0, 107);
    fieldsPanel.add(endDatePanel, gridBagConstraints);

    isPaidInput.setBackground(new java.awt.Color(44, 44, 59));
    isPaidInput.setText("booking paid");
    isPaidInput.setPreferredSize(new java.awt.Dimension(125, 24));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 10;
    gridBagConstraints.gridheight = 2;
    gridBagConstraints.ipadx = 15;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 107);
    fieldsPanel.add(isPaidInput, gridBagConstraints);

    okButton.setText("OK");
    okButton.setActionCommand(BookingAddActionCommand.OK.str);
    okButton.addActionListener(panel);
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(okButton);
    buttonPanel.add(Box.createHorizontalStrut(40));

    cancelButton.setText("Cancel");
    cancelButton.setActionCommand(BookingAddActionCommand.CANCEL.str);
    cancelButton.addActionListener(panel);
    buttonPanel.add(cancelButton);

    panel.setLayout(new GridLayoutCustom(3, 1));
    panel.add(fieldsPanel);
    panel.add(buttonPanel);
    panel.add(Box.createVerticalStrut(40));
    panel.setBorder(BorderFactory.createTitledBorder(GuiBorderLabelUtil.createTitleBorder("Book a room")));
  }

  /**
   * Getter for customer.
   */
  public String getCustomerID() {
    try {
      String customer = customerInput.getSelectedItem().toString();
      return String.valueOf(customerFullList.get(customer).getCustomerID());
    } catch (java.lang.NullPointerException e) {
      return null;
    }
  }

  /**
   * Getter for room id.
   */
  public String getRoomID() {
    try {
      String room = roomInput.getSelectedItem().toString();
      return String.valueOf(roomFullList.get(room).getRoomID());
    } catch (java.lang.NullPointerException e) {
      return null;
    }
  }

  /**
   * Getter for room type.
   */
  public String getRoomType() {
    Object selectedItem = roomTypeInput.getSelectedItem();
    if (selectedItem != null) {
      return selectedItem.toString();
    } else {
      return null;
    }
  }

  /**
   * Getter for room number.
   */
  public String getRoomNumber() {
    Object selectedItem = roomInput.getSelectedItem();
    if (selectedItem != null) {
      return selectedItem.toString();
    } else {
      return null;
    }
  }

  /**
   * Getter for start date.
   */
  public Instant getStartDate() {
    try {
      return DateConverter.castToInstant(startDatePanel.getDate());
    } catch (CouldNotParseDate e) {
      return null;
    } catch (NullPointerException e) {
      return null;
    }
  }

  /**
   * Getter for end date.
   */
  public Instant getEndDate() {
    try {
      return DateConverter.castToInstant(endDatePanel.getDate());
    } catch (CouldNotParseDate e) {
      return null;
    } catch (NullPointerException e) {
      return null;
    }
  }

  public boolean isPaid() {
    return isPaidInput.isSelected();
  }

  /**
   * Filters room numbers according to available dates.
   */
  private void roomTypeDateFilter(String roomType, Instant startDate, Instant endDate) {

    roomInput.removeAllItems();
    List<Integer> filteredRoomNumbers = new ArrayList<>();

    for (Room room : roomTypeFilter(roomType)) {
      filteredRoomNumbers.add(room.getNumber());
    }
    for (Booking b : bookingRoomTypeFilter(roomType)) {
      Interval existingBooking = Interval.of(b.getReservation().getStartDate(), b.getReservation().getEndDate());
      Interval newBooking = Interval.of(startDate, endDate);
      if (newBooking.overlaps(existingBooking)) {
        filteredRoomNumbers.remove(Integer.valueOf(b.getRoom().getNumber()));
      }
    }

    Collections.sort(filteredRoomNumbers);
    for (Integer roomNumber : filteredRoomNumbers) {
      roomInput.addItem(roomNumber);
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
    for (Booking booking : activeBookingList) {
      if (booking.getRoom().getSize().equals(roomType)) {
        bookingsAccordingToRoomType.add(booking);
      }
    }
    return bookingsAccordingToRoomType;
  }

  private void changeDatePickerSettingsColor(DatePickerSettings settings) {
    Color color = new Color(44, 44, 59);
    settings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, color);
    settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, color);
    settings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, color);
    settings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, color);
    settings.setColor(DatePickerSettings.DateArea.TextFieldBackgroundValidDate, new java.awt.Color(56, 56, 77));
    settings.setColor(DateArea.DatePickerTextValidDate, Color.lightGray);
    settings.setAllowKeyboardEditing(false);

  }

  @Override
  public void dateChanged(DateChangeEvent event) {

    if (getStartDate() != null && getEndDate() != null && getRoomType() != null) {
      if (getStartDate().isAfter(getEndDate()) || getStartDate().equals(getEndDate())) {
        startDatePanel.closePopup();
        endDatePanel.closePopup();
        startDatePanel.clear();
        endDatePanel.clear();
        roomInput.removeAllItems();
        String str = "User tried to book a room for 0 or negative days";
        PopUpDialog.warn(str);
      } else {
        String roomType = roomTypeInput.getSelectedItem().toString();
        roomTypeDateFilter(roomType, getStartDate(), getEndDate());
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (getStartDate() != null && getEndDate() != null && getRoomType() != null) {
      if (e.getSource() == roomTypeInput) {
        String roomType = roomTypeInput.getSelectedItem().toString();
        roomTypeDateFilter(roomType, getStartDate(), getEndDate());
      }
    }
  }
}

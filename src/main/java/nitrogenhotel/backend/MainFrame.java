package nitrogenhotel.backend;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllActiveBookingException;
import nitrogenhotel.db.exceptions.booking.CouldNotFetchAllBookingException;
import nitrogenhotel.db.exceptions.customer.CouldNotFetchAllCustomersException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllFloorException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllRoomTypesException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllRoomsException;
import nitrogenhotel.ui.frames.MainFrameUI;
import nitrogenhotel.utils.ActiveUser;
import nitrogenhotel.utils.CouldNotParseDate;
import org.tinylog.Logger;


/** Main window of the program logic. Central hub for listening to ActionEvents. */
public class MainFrame extends CommandFrame {

  public final MainFrameUI ui;
  public final LoginPanel loginPanel;
  public final ScreenNameEditPanel screenNameEditPanel;
  public final PasswordEditPanel passwordEditPanel;
  public final UserAddPanel userAddPanel;
  public RoomAddPanel roomAddPanel;
  public RoomEditPanel roomEditPanel;
  public RoomDetailsPanel roomDetailsPanel;
  public RoomDeletePanel roomDeletePanel;
  public final CustomerAddPanel customerAddPanel;
  public CustomerEditPanel customerEditPanel;
  public CustomerDetailsPanel customerDetailsPanel;
  public BookingAddPanel bookingAddPanel;  // BUG for date panels
  public BookingEditPanel bookingEditPanel;
  public BookingDetailsPanel bookingDetailsPanel;

  /** Constructor for the Main Frame logic. */
  public MainFrame() {
    super(new MainFrameUI());

    ui = (MainFrameUI) super.ui;
    loginPanel = new LoginPanel(this);
    screenNameEditPanel = new ScreenNameEditPanel(this);
    passwordEditPanel = new PasswordEditPanel(this);
    userAddPanel = new UserAddPanel(this);
    customerAddPanel = new CustomerAddPanel(this);
    setupUI();

    ui.centerPanel.add(loginPanel);
  }

  /**
   * Listen to MainFrameUI and set centerPanel to a specific Panel
   * <b>CAUTION</b>:
   * The case strings are expected match the SomethingActionCommand.SomeEnum.str string.
   */
  @Override
  public void actionPerformed(ActionEvent ae) {
    String actionCommand = ae.getActionCommand();
    switch (actionCommand) {

      case "logout" -> {
        Logger.debug("ActionEvent from logout button");
        ActiveUser.clear();
        ui.resetToLoginScreen();
      }

      case "edit screen name" -> {
        Logger.debug("ActionEvent from EditScreenNamePanel");
        ui.editScreenNameSetUp();
      }

      case "edit password" -> {
        Logger.debug("ActionEvent from EditPasswordPanel");
        ui.editPasswordSetUp();
      }

      case "room details" -> {
        Logger.debug("ActionEvent from RoomDetailsPanel");
        roomDetailsSetup();
      }

      case "edit room" -> {
        Logger.debug("ActionEvent from RoomEditPanel");
        editRoomSetup();
      }

      case "add room" -> {
        Logger.debug("ActionEvent from RoomEditPanel");
        addRoomSetup();
      }

      case "delete room" -> {
        Logger.debug("ActionEvent from RoomDeletePanel");
        roomDeleteSetup();
      }

      case "add customer" -> {
        Logger.info("Setting up CustomerAddPanel");
        ui.customerAddSetUp();
      }

      case "edit customer" -> {
        Logger.debug("ActionEvent from CustomerEditPanel");
        customerEditSetup();
      }

      case "add user" -> {
        Logger.debug("ActionEvent from UserAddPanel");
        ui.userAddSetUp();
      }

      case "customer details" -> {
        Logger.debug("ActionEvent from CustomerDetailsPanel");
        customerDetailsSetup();
      }

      case "booking details" -> {
        Logger.info("Setting up booking overview");
        bookingDetailsSetup();
      }

      case "edit booking" -> {
        Logger.info("Setting up Booking Edit");
        bookingEditSetup();
      }

      case "add booking" -> {
        Logger.info("Setting up room booking panel");
        bookingAddSetup();
      }

      // keep cancel/close buttons logic at the bottom of the switch case.
      default -> {
        if (actionCommand.endsWith("CANCEL")) {
          ui.cleanCenterPanel();
          break;
        }
        Logger.warn("Action event's command does not match any case:\n'{}' ?", actionCommand);
      }
    }
  }

  public void loginSetup() {
    ui.updateMenuPanel();
  }

  public void updateScreenNameLabel() {
    ui.updateScreenNameLabel();
  }

  public void clearCurrentPanel() {
    ui.cleanCenterPanel();
  }

  /** Method to refresh the current panel. */
  public void refreshCurrentPanel() {
    Component current = ui.centerPanel.getComponent(0);
    Logger.info("Refreshing panel: %s".formatted(current));
    ui.cleanCenterPanel();
    if (current == roomEditPanel) {  // Not cool but current interface demands it
      editRoomSetup();
    } else if (current == roomAddPanel) {
      addRoomSetup();
    } else if (current == roomDetailsPanel) {
      roomDetailsSetup();
    } else if (current == roomDeletePanel) {
      roomDeleteSetup();
    } else if (current == bookingAddPanel) {
      bookingAddSetup();
    } else if (current == bookingDetailsPanel) {
      bookingDetailsSetup();
    } else if (current == bookingEditPanel) {
      bookingEditSetup();
    } else if (current == customerEditPanel) {
      customerEditSetup();
    } else if (current == customerDetailsPanel) {
      customerDetailsSetup();
    } else {
      Logger.warn("Received %s as current panel, cannot proceed".formatted(current));
      ui.warn("Could not apply changes");
    }
  }

  private void editRoomSetup() {
    try {
      List<Room> allRooms = EntryCollector.allRooms();
      List<Floor> allFloors = EntryCollector.allFloors();
      List<RoomType> allRoomTypes = EntryCollector.allRoomTypes();

      roomEditPanel = new RoomEditPanel(this, allRooms, allFloors, allRoomTypes);
      ui.roomEditSetUp();
    } catch (CouldNotFetchAllRoomsException | CouldNotFetchAllFloorException | CouldNotFetchAllRoomTypesException e) {
      Logger.warn("Edit Room setup failed with exception {}", e);
      ui.warn("Unable to setup Edit Room panel, data could not be fetched.");
    }
  }

  private void roomDetailsSetup() {
    try {
      List<Room> allRoomDet = EntryCollector.allRooms();
      List<Floor> allFloorDet = EntryCollector.allFloors();
      List<RoomType> allRoomTypesDet = EntryCollector.allRoomTypes();

      roomDetailsPanel = new RoomDetailsPanel(this, allRoomDet, allFloorDet, allRoomTypesDet);
      ui.roomDetailsSetUp();
    } catch (CouldNotFetchAllRoomsException | CouldNotFetchAllFloorException | CouldNotFetchAllRoomTypesException e) {
      Logger.warn("Room Details setup failed with exception {}", e);
      ui.warn("Unable to setup Room Details panel, data could not be fetched.");
    }
  }

  private void addRoomSetup() {
    try {
      List<Floor> allFloors = EntryCollector.allFloors();
      List<RoomType> allRoomTypes = EntryCollector.allRoomTypes();

      roomAddPanel = new RoomAddPanel(this, allFloors, allRoomTypes);
      ui.addRoomSetUp();
    } catch (CouldNotFetchAllFloorException | CouldNotFetchAllRoomTypesException e) {
      Logger.warn("Add Room setup failed with exception {}", e);
      ui.warn("Unable to setup Add Room panel, data could not be fetched.");
    }
  }

  private void roomDeleteSetup() {
    try {
      List<Room> allRooms = EntryCollector.allRooms();
      List<Floor> allFloors = EntryCollector.allFloors();
      List<RoomType> allRoomTypes = EntryCollector.allRoomTypes();

      roomDeletePanel = new RoomDeletePanel(this, allRooms, allFloors, allRoomTypes);
      ui.roomDeleteSetUp();
    } catch (CouldNotFetchAllRoomsException | CouldNotFetchAllFloorException | CouldNotFetchAllRoomTypesException e) {
      Logger.warn("Room Delete setup failed with exception {}", e);
      ui.warn("TODO IMPLEMENT WARN");
      ui.warn("Unable to setup Room Delete panel, data could not be fetched.");
    }
  }

  private void customerEditSetup() {
    try {
      List<Customer> allCustomers = EntryCollector.allCustomers();

      customerEditPanel = new CustomerEditPanel(this, allCustomers);
      ui.customerEditSetUp();
    } catch (CouldNotFetchAllCustomersException e) {
      Logger.warn("Customer Edit setup failed with exception {}", e);
      ui.warn("Unable to setup Customer Edit panel, data could not be fetched.");
    }
  }

  private void customerDetailsSetup() {
    try {
      List<Customer> allCustomers = EntryCollector.allCustomers();

      customerDetailsPanel = new CustomerDetailsPanel(this, allCustomers);
      ui.customerDetailsSetUp();
    } catch (CouldNotFetchAllCustomersException e) {
      Logger.warn("Customer Details setup failed with exception {}", e);
      ui.warn("Unable to setup Customer Details panel, data could not be fetched.");
    }
  }

  private void bookingDetailsSetup() {
    try {
      List<Booking> allBookings = EntryCollector.allBookings();
      List<Room> allRooms = EntryCollector.allRooms();

      bookingDetailsPanel = new BookingDetailsPanel(this, allBookings, allRooms);
      ui.bookingDetailsSetUp();
    } catch (CouldNotFetchAllBookingException
        | CouldNotFetchAllRoomsException e) {
      Logger.warn("Booking Details setup failed with exception {}", e);
      ui.warn("Unable to setup Booking Details panel, data could not be fetched.");
    }
  }

  private void bookingAddSetup() {
    try {
      List<Customer> allCustomers = EntryCollector.allCustomers();
      List<Room> allRooms = EntryCollector.allRooms();
      List<RoomType> allRoomTypes = EntryCollector.allRoomTypes();
      List<Booking> activeBookings = EntryCollector.activeBookings();

      bookingAddPanel = new BookingAddPanel(this, allCustomers, allRooms,
              allRoomTypes, activeBookings);
      ui.bookingAddSetUp();
    } catch (CouldNotFetchAllCustomersException
        | CouldNotFetchAllRoomsException
        | CouldNotFetchAllRoomTypesException
        | CouldNotFetchAllActiveBookingException e) {
      Logger.warn("Booking Add setup failed with exception {}", e);
      ui.warn("Unable to setup Book Room panel, data could not be fetched.");
    }
  }

  private void bookingEditSetup() {
    try {
      List<Booking> activeBookings = EntryCollector.activeBookings();
      List<Customer> allCustomers = EntryCollector.allCustomers();
      List<Room> allRooms = EntryCollector.allRooms();
      List<RoomType> allRoomTypes = EntryCollector.allRoomTypes();

      bookingEditPanel = new BookingEditPanel(this, activeBookings, allCustomers, allRooms, allRoomTypes);
      ui.bookingEditSetUp();
    } catch (CouldNotFetchAllCustomersException
        | CouldNotFetchAllRoomsException
        | CouldNotFetchAllActiveBookingException
        | CouldNotFetchAllRoomTypesException e) {
      Logger.warn("Booking Edit setup failed with exception {}", e);
      ui.warn("Unable to setup Booking Edit panel, data could not be fetched.");
    }
  }
}
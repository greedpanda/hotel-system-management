package nitrogenhotel.ui.panels;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import nitrogenhotel.ui.actioncommands.BookingAddActionCommand;
import nitrogenhotel.ui.actioncommands.BookingDetailsActionCommand;
import nitrogenhotel.ui.actioncommands.BookingEditActionCommand;
import nitrogenhotel.ui.actioncommands.CustomerAddActionCommand;
import nitrogenhotel.ui.actioncommands.CustomerDetailsActionCommand;
import nitrogenhotel.ui.actioncommands.CustomerEditActionCommand;
import nitrogenhotel.ui.actioncommands.EditPasswordActionCommand;
import nitrogenhotel.ui.actioncommands.EditScreenNameActionCommand;
import nitrogenhotel.ui.actioncommands.RoomAddActionCommand;
import nitrogenhotel.ui.actioncommands.RoomDeleteActionCommand;
import nitrogenhotel.ui.actioncommands.RoomDetailsActionCommand;
import nitrogenhotel.ui.actioncommands.RoomEditActionCommand;
import nitrogenhotel.ui.actioncommands.UserAddActionCommand;
import nitrogenhotel.ui.utilsgui.CustomJMenu;
import nitrogenhotel.utils.ActiveUser;
import nitrogenhotel.utils.UserPrivileges;

/**
 * Builds side left menu panel.
 */
public class MenuPanelUI {

  public CustomJMenu userMenu;
  public CustomJMenu roomMenu;
  public CustomJMenu customerMenu;
  public CustomJMenu bookingMenu;
  public JMenuItem editScreenName;
  public JMenuItem editPassword;
  public JMenuItem roomDetails;
  public JMenuItem roomAdd;
  public JMenuItem roomEdit;
  public JMenuItem roomDelete;
  public JMenuItem customerAdd;
  public JMenuItem customerEdit;
  public JMenuItem customerDetails;
  public JMenuItem userAdd;
  public JMenuItem bookingDetails;
  public JMenuItem bookRoom;
  public JMenuItem bookingEdit;

  // public JMenuItem roomAddSize;
  public ArrayList<CustomJMenu> menuList;

  public MenuPanelUI() {
    menuList = new ArrayList<>();
  }

  /**
   * Method creates content of user settings menu.
   */
  private CustomJMenu userSettingsAdmin() {
    userMenu = new CustomJMenu("User Settings");
    userMenu.setOpaque(true);
    userMenu.setIcon(new ImageIcon("src/main/resources/images/settings.png"));
    userMenu.setMnemonic(KeyEvent.VK_P);

    editScreenName = new JMenuItem("Edit screen name", KeyEvent.VK_E);
    editScreenName.setActionCommand(EditScreenNameActionCommand.EDITSCRNAME.str);
    editPassword = new JMenuItem("Edit password", KeyEvent.VK_E);
    editPassword.setActionCommand(EditPasswordActionCommand.EDITPASSWORD.str);
    userAdd = new JMenuItem("Add User", KeyEvent.VK_E);
    userAdd.setActionCommand(UserAddActionCommand.ADDUSER.str);

    userMenu.add(editScreenName);
    userMenu.add(editPassword);
    userMenu.add(userAdd);

    return userMenu;
  }

  private CustomJMenu userSettingsReception() {
    userMenu = new CustomJMenu("User Settings      ");
    userMenu.setOpaque(true);
    userMenu.setIcon(new ImageIcon("src/main/resources/images/settings.png"));
    userMenu.setMnemonic(KeyEvent.VK_P);

    editScreenName = new JMenuItem("Edit screen name", KeyEvent.VK_E);
    editScreenName.setActionCommand(EditScreenNameActionCommand.EDITSCRNAME.str);
    editPassword = new JMenuItem("Edit password", KeyEvent.VK_E);
    editPassword.setActionCommand(EditPasswordActionCommand.EDITPASSWORD.str);

    userMenu.add(editScreenName);
    userMenu.add(editPassword);

    return userMenu;
  }

  /**
   * Method creates content of room options menu for admin.
   */
  private CustomJMenu roomOptionsAdmin() {
    roomMenu = new CustomJMenu("Room Options");
    roomMenu.setOpaque(true);
    roomMenu.setIcon(new ImageIcon("src/main/resources/images/room.png"));
    roomMenu.setMnemonic(KeyEvent.VK_R);

    roomAdd = new JMenuItem("Add Room", KeyEvent.VK_A);
    roomAdd.setActionCommand(RoomAddActionCommand.ADDROOM.str);

    roomEdit = new JMenuItem("Edit Room", KeyEvent.VK_E);
    roomEdit.setActionCommand(RoomEditActionCommand.EDITROOM.str);
    // roomAddSize = new JMenuItem("Add Room Size", KeyEvent.VK_R);
    // roomAddSize.setActionCommand(RoomAddSizeActionCommand.ADDROOMSIZE.str);

    roomDelete = new JMenuItem("Delete Room", KeyEvent.VK_D);
    roomDelete.setActionCommand(RoomDeleteActionCommand.DELETEROOM.str);

    roomMenu.add(roomAdd);
    roomMenu.add(roomEdit);
    roomMenu.add(roomDelete);
    /* roomMenu.add(roomAddSize); */

    return roomMenu;
  }

  /**
   * Method creates content of room options menu for reception.
   */
  private CustomJMenu roomOptionsReception() {
    roomMenu = new CustomJMenu("Room Options      ");
    roomMenu.setOpaque(true);
    roomMenu.setIcon(new ImageIcon("src/main/resources/images/room.png"));
    roomMenu.setMnemonic(KeyEvent.VK_R);

    roomDetails = new JMenuItem("Room details", KeyEvent.VK_R);
    roomDetails.setActionCommand(RoomDetailsActionCommand.ROOMDETAILS.str);

    roomMenu.add(roomDetails);

    return roomMenu;
  }

  /**
   * Method creates content of customer options menu.
   */
  private CustomJMenu customerOptions() {
    customerMenu = new CustomJMenu("Customer Options");
    customerMenu.setOpaque(true);
    customerMenu.setIcon(new ImageIcon("src/main/resources/images/customer.png"));
    customerMenu.setMnemonic(KeyEvent.VK_C);

    customerAdd = new JMenuItem("Add customer", KeyEvent.VK_E);
    customerAdd.setActionCommand(CustomerAddActionCommand.ADDCUSTOMER.str);
    customerEdit = new JMenuItem("Edit customer", KeyEvent.VK_R);
    customerEdit.setActionCommand(CustomerEditActionCommand.EDITCUSTOMER.str);
    customerDetails = new JMenuItem("Customer details", KeyEvent.VK_C);
    customerDetails.setActionCommand(CustomerDetailsActionCommand.CUSTOMERDETAILS.str);

    customerMenu.add(customerAdd);
    customerMenu.add(customerEdit);
    customerMenu.add(customerDetails);

    return customerMenu;
  }

  /**
   * Method creates content of booking options menu.
   */
  private CustomJMenu bookingOptions() {
    bookingMenu = new CustomJMenu("Booking Options");
    bookingMenu.setOpaque(true);
    bookingMenu.setIcon(new ImageIcon("src/main/resources/images/booking.png"));
    bookingMenu.setMnemonic(KeyEvent.VK_B);

    bookingDetails = new JMenuItem("Booking details", KeyEvent.VK_B);
    bookingDetails.setActionCommand(BookingDetailsActionCommand.BOOKINGDETAILS.str);
    bookRoom = new JMenuItem("Book room", KeyEvent.VK_B);
    bookRoom.setActionCommand(BookingAddActionCommand.BOOKROOM.str);
    bookingEdit = new JMenuItem("Edit booking", KeyEvent.VK_E);
    bookingEdit.setActionCommand(BookingEditActionCommand.EDITBOOKING.str);

    bookingMenu.add(bookRoom);
    bookingMenu.add(bookingDetails);
    bookingMenu.add(bookingEdit);
    
    return bookingMenu;
  }

  /**
   * Method populates list of menus that should be displayed in side panel.
   */
  public void listOfMenus() {
    if (ActiveUser.privileges() == UserPrivileges.ADMIN) {
      menuList.add(userSettingsAdmin());
      menuList.add(roomOptionsAdmin());
    } else {
      menuList.add(userSettingsReception());
      menuList.add(roomOptionsReception());
      menuList.add(customerOptions());
      menuList.add(bookingOptions());
    }
  }
}

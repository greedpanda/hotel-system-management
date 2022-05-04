package nitrogenhotel.ui.panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.ui.actioncommands.BookingDetailsActionCommand;
import nitrogenhotel.ui.utilsgui.BookingTable;
import nitrogenhotel.ui.utilsgui.PopUpDialog;

/** BookingDetailsPanelUI displays table of bookings in NON editable table. */
public class BookingDetailsPanelUI extends BookingTable implements PanelUI {
  public BookingDetailsPanelUI(List<Booking> bookingList, List<Room> roomList) {
    super(bookingList, roomList);
  }

  @Override
  public void setupUI(CommandPanel panel) {
    buildBookingTable(panel, "Booking Details", "click on room/customer for additional info");

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        int rowIndex = table.convertRowIndexToModel(table.rowAtPoint(evt.getPoint()));
        int columnIndex = table.columnAtPoint(evt.getPoint());
        Booking row = bookingList.get(rowIndex);
        if (columnIndex == 2) {
          String infoRoom = "Size: " + row.getRoom().getSize() + "\nBeds: " + row.getRoom().getNbBeds() + "\nFloor: "
              + row.getRoom().getFloor();
          PopUpDialog.info(infoRoom, "Room information");

        } else if (columnIndex == 1) {
          String infoCustomer = "ID: " + row.getCustomer().getCustomerID() + "\nPublic ID: "
              + row.getCustomer().getPublicID() + "\nAddress: " + row.getCustomer().getAddress() + "\nPayment method: "
              + row.getCustomer().getPaymentMethod();
          PopUpDialog.info(infoCustomer, "Customer information");
        }
      }
    });

    JPanel buttonPanel = new JPanel();

    JButton btnClose = new JButton("Close");
    btnClose.setActionCommand(BookingDetailsActionCommand.CLOSE.str);
    btnClose.addActionListener(panel);

    buttonPanel.add(Box.createHorizontalStrut(40));
    buttonPanel.add(btnClose);
    buttonPanel.add(Box.createHorizontalStrut(40));

    panel.add(buttonPanel);

  }
}

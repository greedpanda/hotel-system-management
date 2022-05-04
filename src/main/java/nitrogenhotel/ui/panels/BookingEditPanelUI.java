package nitrogenhotel.ui.panels;

import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Booking;
import nitrogenhotel.db.entries.Customer;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.ui.actioncommands.BookingEditActionCommand;
import nitrogenhotel.ui.utilsgui.BookingTable;

/** BookingEditPanelUI displays table of bookings in editable table. */
public class BookingEditPanelUI extends BookingTable implements PanelUI {
  public BookingEditPanelUI(List<Booking> bookingList, List<Customer> customerList, List<Room> roomList,
      List<RoomType> roomTypeList) {
    super(bookingList, customerList, roomList, roomTypeList);
  }

  @Override
  public void setupUI(CommandPanel panel) {
    buildBookingTable(panel, "Edit active bookings", "doubleclick on row for editing");

    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        rowIndex = table.convertRowIndexToModel(table.rowAtPoint(evt.getPoint()));
        Booking row = bookingList.get(rowIndex);
        if (evt.getClickCount() == 2) {
          dialogForEditing(row, rowIndex);
        }
      }
    });

    JPanel buttonPanel = new JPanel();

    JButton applyButton = new JButton("Apply");
    applyButton.setActionCommand(BookingEditActionCommand.APPLY.str);
    applyButton.addActionListener(panel);

    JButton okButton = new JButton("OK");
    okButton.setActionCommand(BookingEditActionCommand.OK.str);
    okButton.addActionListener(panel);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand(BookingEditActionCommand.CANCEL.str);
    cancelButton.addActionListener(panel);

    buttonPanel.add(Box.createHorizontalStrut(40));
    buttonPanel.add(applyButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(okButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(cancelButton);
    buttonPanel.add(Box.createHorizontalStrut(40));

    panel.add(buttonPanel);
  }
}

package nitrogenhotel.ui.panels;

import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.ui.actioncommands.RoomDeleteActionCommand;
import nitrogenhotel.ui.utilsgui.RoomTable;

/** RoomDeletePanelUI deletes one room at a time from table. */
public class RoomDeletePanelUI extends RoomTable implements PanelUI {

  public RoomDeletePanelUI(List<Room> roomList, List<Floor> floorList, List<RoomType> sizeList) {
    super(roomList, floorList, sizeList);

  }

  @Override
  public void setupUI(CommandPanel panel) {
    buildRoomTable(panel, "Delete Room", "select a row to delete");

    // allows only one row selection
    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    JPanel buttonPanel = new JPanel();

    JButton deleteButton = new JButton("Delete");
    deleteButton.setActionCommand(RoomDeleteActionCommand.DELETE.str);
    deleteButton.addActionListener(panel);
    JButton closeButton = new JButton("Close");
    closeButton.setActionCommand(RoomDeleteActionCommand.CLOSE.str);
    closeButton.addActionListener(panel);

    buttonPanel.add(Box.createHorizontalStrut(40));
    buttonPanel.add(deleteButton);
    buttonPanel.add(Box.createHorizontalStrut(20));
    buttonPanel.add(closeButton);
    buttonPanel.add(Box.createHorizontalStrut(40));

    panel.add(buttonPanel);

  }
}

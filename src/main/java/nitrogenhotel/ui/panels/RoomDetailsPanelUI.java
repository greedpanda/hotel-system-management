package nitrogenhotel.ui.panels;

import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.ui.actioncommands.RoomDetailsActionCommand;
import nitrogenhotel.ui.utilsgui.RoomTable;

/** RoomDetailsPanelUI displays table of rooms. */
public class RoomDetailsPanelUI extends RoomTable implements PanelUI {

  public RoomDetailsPanelUI(List<Room> roomList, List<Floor> floorList, List<RoomType> sizeList) {
    super(roomList, floorList, sizeList);
  }

  @Override
  public void setupUI(CommandPanel panel) {
    JPanel buttonPanel = new JPanel();

    buildRoomTable(panel, "Room Details", "");
    JButton btnClose = new JButton("Close");
    btnClose.setActionCommand(RoomDetailsActionCommand.CLOSE.str);
    btnClose.addActionListener(panel);
    buttonPanel.add(Box.createHorizontalStrut(40));
    buttonPanel.add(btnClose);
    buttonPanel.add(Box.createHorizontalStrut(40));
    panel.add(buttonPanel);

  }
}

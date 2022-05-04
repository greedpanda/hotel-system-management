package nitrogenhotel.ui.panels;

import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import nitrogenhotel.backend.CommandPanel;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.ui.actioncommands.RoomEditActionCommand;
import nitrogenhotel.ui.utilsgui.RoomTable;

/** RoomEditPanelUI displays table of rooms in editable table. */
public class RoomEditPanelUI extends RoomTable implements PanelUI {

  public RoomEditPanelUI(List<Room> roomList, List<Floor> floorList, List<RoomType> sizeList) {
    super(roomList, floorList, sizeList);
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

  @Override
  public void setupUI(CommandPanel p) {
    buildRoomTable(p, "Edit Room", "click on table cells to edit/confirm by pressing Enter");
    
    JPanel buttonPanel = new JPanel();

    JButton applyButton = new JButton("Apply");
    applyButton.setActionCommand(RoomEditActionCommand.APPLY.str);
    applyButton.addActionListener(p);

    JButton okButton = new JButton("OK");
    okButton.setActionCommand(RoomEditActionCommand.OK.str);
    okButton.addActionListener(p);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand(RoomEditActionCommand.CANCEL.str);
    cancelButton.addActionListener(p);

    buttonPanel.add(Box.createHorizontalStrut(40));
    buttonPanel.add(applyButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(okButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(cancelButton);
    buttonPanel.add(Box.createHorizontalStrut(40));

    p.add(buttonPanel);
  }
}

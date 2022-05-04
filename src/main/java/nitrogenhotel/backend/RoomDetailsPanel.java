package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.util.List;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.ui.panels.RoomDetailsPanelUI;
import org.tinylog.Logger;

/** Backend Panel to handle the show detail room panel logic. */
public class RoomDetailsPanel extends CommandPanel {
  public RoomDetailsPanelUI ui;
  private MainFrame mf;

  public RoomDetailsPanel(List<Room> rooms, List<Floor> floors, List<RoomType> roomTypes) {
    super(new RoomDetailsPanelUI(rooms, floors, roomTypes));
    ui = (RoomDetailsPanelUI) super.ui;
  }

  public RoomDetailsPanel(MainFrame mf, List<Room> rooms, List<Floor> floors, List<RoomType> roomTypes) {
    this(rooms, floors, roomTypes);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    if ("cancel".equals(actionCommand)) {
      mf.clearCurrentPanel();
    } else {
      Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    return true;
  }
}

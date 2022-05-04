package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.util.List;
import nitrogenhotel.db.dataaccessobjects.RoomDao;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.room.CouldNotDeleteRoomException;
import nitrogenhotel.db.exceptions.room.InvalidRoomInputException;
import nitrogenhotel.ui.panels.RoomDeletePanelUI;
import org.tinylog.Logger;

/** Backend Panel to handle the delete room panel logic. */
public class RoomDeletePanel extends CommandPanel {
  public RoomDeletePanelUI ui;
  private MainFrame mf;

  public RoomDeletePanel(List<Room> rooms, List<Floor> floors, List<RoomType> roomTypes) {
    super(new RoomDeletePanelUI(rooms, floors, roomTypes));
    ui = (RoomDeletePanelUI) super.ui;
  }

  public RoomDeletePanel(MainFrame mf, List<Room> rooms, List<Floor> floors, List<RoomType> roomTypes) {
    this(rooms, floors, roomTypes);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    switch (actionEvent.getActionCommand()) {
      case "delete room delete" -> {
        if (run()) {
          mf.refreshCurrentPanel();
          ui.success("Room deleted.");
        }
      }

      case "cancel" -> mf.clearCurrentPanel();

      default -> Logger.warn("Received unknown action command '{}'", actionEvent.getActionCommand());
    }
  }

  @Override
  public boolean run() {
    if (!ui.isSelectedRowToDelete()) {
      Logger.warn("User selected no room to delete");
      ui.warn("Please select a room for deletion.");
      return false;
    }

    RoomDao roomDao = new RoomDao();
    try {
      roomDao.delete(ui.getSelectedRoomToDelete());
    } catch (CouldNotDeleteRoomException e) {
      Logger.warn("Database failed to delete room '{}'", ui.getSelectedRoomToDelete().getNumber());
      ui.warn("Database error: could delete the room.");
      return false;
    } catch (InvalidRoomInputException e) {
      Logger.warn("Database did not accept room input for deletion.");
      ui.warn("Database error: invalid input.");
      return false;
    }

    Logger.info("Deleted room '{}'", ui.getSelectedRoomToDelete().getNumber());
    ui.removeRow();
    return true;
  }
}

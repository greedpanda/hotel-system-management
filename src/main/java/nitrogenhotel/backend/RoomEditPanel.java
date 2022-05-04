package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import nitrogenhotel.db.dataaccessobjects.RoomDao;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.room.CouldNotFetchAllRoomsException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchRoomException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateFloorNumberException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateNbBedsException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateNoteException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateRoomNumberException;
import nitrogenhotel.db.exceptions.room.CouldNotUpdateRoomSizeException;
import nitrogenhotel.db.exceptions.room.InvalidRoomInputException;
import nitrogenhotel.ui.panels.RoomEditPanelUI;
import nitrogenhotel.utils.ActiveUser;
import org.tinylog.Logger;

/** Backend Panel to handle the edit room panel logic. */
public class RoomEditPanel extends CommandPanel {
  public RoomEditPanelUI ui;
  private Map<Integer, Room> rooms;
  private List<Room> roomsList;
  private MainFrame mf;

  public RoomEditPanel(List<Room> rooms, List<Floor> floors, List<RoomType> roomTypes) {
    super(new RoomEditPanelUI(rooms, floors, roomTypes));
    ui = (RoomEditPanelUI) super.ui;
  }

  public RoomEditPanel(MainFrame mf, List<Room> rooms, List<Floor> floors, List<RoomType> roomTypes) {
    this(rooms, floors, roomTypes);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {

      case "edit room apply" -> {
        if (run()) {
          mf.refreshCurrentPanel();
          ui.success("Room edited.");
        }
      }

      case "edit room ok" -> {
        run();
        mf.clearCurrentPanel();
      }

      case "cancel" -> mf.clearCurrentPanel();

      default -> Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    rooms = ui.getEditedRoomData();
    if (rooms == null) {
      ui.warn("No changes were made. Please edit something and click OK or Apply.");
      Logger.warn("User '{}' attempted to submit blank list of edited rooms.", ActiveUser.get().getUserName());
      return false;
    }

    if (rooms.size() == 0) {
      Logger.info("No room data edited skipping refresh");
      return false;
    }

    Logger.debug("Edited rooms returned from GUI\n%s".formatted(rooms));

    Room room = null;
    RoomDao roomDao = new RoomDao();

    // list with pre-edit values for rooms
    try {
      roomsList = roomDao.getAll();
    } catch (CouldNotFetchAllRoomsException e) {
      ui.warn("Database error: could not fetch the list of rooms from the database.");
      Logger.warn(e);
      return false;
    }

    for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
      try {
        // Fetching the relative room "r" pre-edit to the entry row.
        for (Room r : roomsList) {
          if (r.getRoomID() == entry.getKey()) {
            room = roomDao.get(r);
            Logger.info("Now editing room number : {}", r.getNumber());
          }
        }
      } catch (CouldNotFetchRoomException | InvalidRoomInputException e) {
        // these are not user mistakes
        Logger.warn(e);
        ui.warn("Database error.");
        return false;
      }

      if (room.getNumber() != entry.getValue().getNumber()) {
        Logger.info("Now editing room number from {} to {}", room.getNumber(), entry.getValue().getNumber());
        // Check if the room number is already used.
        try {
          Logger.info("Checking if room number {} is already in use...", entry.getValue().getNumber());
          roomDao.get(new Room(entry.getValue().getNumber()));
          Logger.warn("Room number already in use.");
          ui.warn(String.format("The room number %s is not available."
                  + " Please use a different one.", entry.getValue().getNumber()));
          return false;
        } catch (InvalidRoomInputException e) {
          Logger.warn(e);
          ui.warn(String.format("Database error: invalid number %s.",
                  entry.getValue().getNumber()));
          return false;
        } catch (CouldNotFetchRoomException e) {
          try {
            Logger.info("Room number edited from {} to {}", room.getNumber(), entry.getValue().getNumber());
            roomDao.updateRoomNumber(room, entry.getValue().getNumber());
            room.setNumber(entry.getValue().getNumber());
          } catch (CouldNotUpdateRoomNumberException | InvalidRoomInputException er) {
            Logger.warn(er);
            ui.warn(String.format("Database error: could not update room number from %s to %s.",
                    room.getNumber(), entry.getValue().getNumber()));
            return false;
          }
        }
      }

      if (room.getFloor() != entry.getValue().getFloor()) {
        Logger.info("Now editing room floor from {} to {}", room.getFloor(), entry.getValue().getFloor());
        try {
          roomDao.updateFloor(room, entry.getValue().getFloor());
        } catch (CouldNotUpdateFloorNumberException | InvalidRoomInputException e) {
          Logger.warn(e);
          ui.warn(String.format("Database error: could not update floor number from %s to %s.",
                  room.getFloor(), entry.getValue().getFloor()));
          return false;
        }
      }

      if (room.getNbBeds() != entry.getValue().getNbBeds()) {
        Logger.info("Now editing room nb beds {} to {}", room.getNbBeds(), entry.getValue().getNbBeds());
        try {
          roomDao.updateNbBeds(room, entry.getValue().getNbBeds());
        } catch (CouldNotUpdateNbBedsException | InvalidRoomInputException e) {
          Logger.warn(e);
          ui.warn(String.format("Database error when updating number of beds from %s to %s.",
                  room.getNbBeds(), entry.getValue().getNbBeds()));
          return false;
        }
      }

      try {
        Logger.info("Now editing room note from {} to {}", room.getNote(), entry.getValue().getNote());
        roomDao.updateNote(room, entry.getValue().getNote());
      } catch (CouldNotUpdateNoteException | InvalidRoomInputException e) {
        Logger.warn(e);
        ui.warn(String.format("Database error when updating room note from %s to %s.",
                room.getNote(), entry.getValue().getNote()));
        return false;
      }

      if (!room.getSize().equals(entry.getValue().getSize())) {
        Logger.info("Now editing room size from {} to {}", room.getSize(), entry.getValue().getSize());
        try {
          roomDao.updateSize(room, entry.getValue().getSize());
        } catch (CouldNotUpdateRoomSizeException | InvalidRoomInputException e) {
          Logger.warn(e);
          ui.warn(String.format("Database error when updating room size from %s to %s.",
                  room.getSize(), entry.getValue().getSize()));
          return false;
        }
      }
    }

    return true;
  }
}

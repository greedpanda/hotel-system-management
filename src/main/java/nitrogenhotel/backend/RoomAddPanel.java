package nitrogenhotel.backend;

import java.awt.event.ActionEvent;
import java.util.List;
import nitrogenhotel.db.dataaccessobjects.RoomDao;
import nitrogenhotel.db.entries.Floor;
import nitrogenhotel.db.entries.Room;
import nitrogenhotel.db.entries.RoomType;
import nitrogenhotel.db.exceptions.room.CouldNotAddRoomException;
import nitrogenhotel.db.exceptions.room.CouldNotFetchRoomException;
import nitrogenhotel.db.exceptions.room.InvalidRoomInputException;
import nitrogenhotel.ui.panels.RoomAddPanelUI;
import org.tinylog.Logger;


/** Backend Panel to handle the Room Add logic.*/
public class RoomAddPanel extends CommandPanel {
  public RoomAddPanelUI ui;
  private MainFrame mf;

  public RoomAddPanel(List<Floor> floors, List<RoomType> roomTypes) {
    super(new RoomAddPanelUI(floors, roomTypes));
    ui = (RoomAddPanelUI) super.ui;
  }

  public RoomAddPanel(MainFrame mf, List<Floor> floors, List<RoomType> roomTypes) {
    this(floors, roomTypes);
    this.mf = mf;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String actionCommand = actionEvent.getActionCommand();
    switch (actionCommand) {

      case "add room ok" -> {
        if (run()) {
          mf.clearCurrentPanel();
          ui.success("Room added.");
        }
      }
      
      case "cancel" -> mf.clearCurrentPanel();

      default -> Logger.warn("Received unknown action command '{}'", actionCommand);
    }
  }

  @Override
  public boolean run() {
    String roomNumber = ui.getRoomNumber();
    String roomNoOfBeds = ui.getRoomNoOfBeds();
    String roomFloor = ui.getRoomFloor();
    Room newRoom;
    try {
      newRoom = new Room(
          roomNumber.isEmpty() ? -1 : Integer.parseInt(roomNumber),
          ui.getRoomSize(),
          roomNoOfBeds.isEmpty() ? -1 : Integer.parseInt(roomNoOfBeds),
          roomFloor.isEmpty() ? -1 : Integer.parseInt(roomFloor),
          ui.getRoomNote()
      );
    } catch (NumberFormatException e) {
      Logger.warn(e);
      ui.warn("All fields must be filled.");
      clearUI();
      return false;
    }

    RoomDao roomDao = new RoomDao();
    try {
      Logger.debug("Fetching room PK:{}", newRoom.getNumber());
      roomDao.get(newRoom);
      Logger.debug("Room exists");
      ui.warn(String.format("Room %s already exists.", newRoom.getNumber()));
      clearUI();
      return false;  // Technically the user gave bad input
    } catch (InvalidRoomInputException e) {
      Logger.warn(e);
      ui.warn("All fields must be filled. No room was added.");
      clearUI();
      return false;
    } catch (CouldNotFetchRoomException e) {
        // Move on
    }

    try {
      roomDao.add(newRoom);
    } catch (InvalidRoomInputException e) {
      ui.warn("All fields must be filled. No room was added.");
      Logger.warn(e);
      clearUI();
      return false;
    } catch (CouldNotAddRoomException e) {
      ui.warn("Database error: Failed to add the room.");
      Logger.warn(e);
      clearUI();
      return false;
    }

    clearUI();
    return true;
  }

  private void clearUI() {
    ui.roomNumberInput.setText("");
    ui.noOfBedsInput.setText("");
    ui.noteInput.setText("");
  }
}

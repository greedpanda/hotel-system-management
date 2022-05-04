package nitrogenhotel.db.entries;

/**
 * Represents the room view in the database.
 */
public class Room {

  private int roomID;
  private int number;
  private String size;
  private int nbBeds;
  private String note = null;
  private int floor;

  /**
   * Creates a new room instance. The parameters is the key (number) room number of the specified
   * room.
   */
  public Room(int number) {
    this.number = number;
  }

  /**
   * Creates a new room instance. The parameter is the size of the specified
   * room.
   */
  public Room(String size) {
    this.size = size;
  }

  /**
   * Creates a new room instance. The parameters are all room attributes except note.
   */
  public Room(int number, String size, int nbBeds, int floor) {
    this.number = number;
    this.size = size;
    this.nbBeds = nbBeds;
    this.floor = floor;
  }


  /**
   * Creates a new room instance. The parameters are all room attributes except the roomID.
   */
  public Room(int number, String size, int nbBeds, int floor, String note) {
    this.number = number;
    this.size = size;
    this.nbBeds = nbBeds;
    this.floor = floor;
    this.note = note;
  }

  /**
   * Creates a new room instance. The parameters are all room attributes.
   */
  public Room(int roomID, int number, String size, int nbBeds, int floor, String note) {
    this.roomID = roomID;
    this.number = number;
    this.size = size;
    this.nbBeds = nbBeds;
    this.floor = floor;
    this.note = note;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public int getNbBeds() {
    return nbBeds;
  }

  public void setNbBeds(int nbBeds) {
    this.nbBeds = nbBeds;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public int getFloor() {
    return floor;
  }

  public void setFloor(int floor) {
    this.floor = floor;
  }

  public int getRoomID() {
    return roomID;
  }

}

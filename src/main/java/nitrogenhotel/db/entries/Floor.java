package nitrogenhotel.db.entries;

/**
 * Used to transfer the floor data.
 */
public class Floor {

  private int floorNumber;

  public Floor(int floorNumber) {
    this.floorNumber = floorNumber;
  }

  public int getFloorNumber() {
    return floorNumber;
  }

  public void setFloorNumber(int floorNumber) {
    this.floorNumber = floorNumber;
  }
}

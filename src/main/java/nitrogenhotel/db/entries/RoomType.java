package nitrogenhotel.db.entries;

/**
 * This class is used to transfer room type data to the database and from the database module to the
 * business layer.
 */
public enum RoomType {
  SINGLE("SINGLE"),
  DOUBLE("DOUBLE"),
  TRIPLE("TRIPLE"),
  QUAD("QUAD"),
  NONE("NONE");

  public final String size;

  RoomType(String s) {
    size = s;
  }

  /**
   * Return index of RoomType based on how they are declared in the enum.
   */
  public static int indexOf(RoomType rt) {
    RoomType[] values = values();
    for (int i = 0; i < values.length; ++i) {
      if (values[i] == rt) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Return a RoomType enum based on a string "size".
   */
  public static RoomType fromSize(String size) {
    for (RoomType value : values()) {
      if (value.size.equals(size)) {
        return value;
      }
    }
    return NONE;
  }
}

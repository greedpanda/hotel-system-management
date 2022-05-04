package nitrogenhotel.db.entries;

/**
 * Represents the booking view in the database.
 */
public class Booking {

  private Reservation reservation;
  private Customer customer;
  private Room room;

  /**
   * Constructor used to fetch a booking.
   */
  public Booking(int reservationID) {
    this.reservation = new Reservation(reservationID);
  }

  /**
   * Used to fetch all bookings for a specified room from the database.
   *
   * @param room     The room to be fetched.
   */
  public Booking(Room room) {
    this.room = room;
  }

  /**
   * Used to fetch a booking from the database.
   *
   * @param res      The reservation to be fetched for the specified customer and room.
   * @param room     The room to be fetched.
   * @param customer The customer for the given reservation.
   */
  public Booking(Reservation res, Room room, Customer customer) {
    this.reservation = res;
    this.room = room;
    this.customer = customer;
  }

  /**
   * Used to add a new booking to the database.
   *
   * @param res      The reservation to be added for the specified customer and room.
   */
  public Booking(Reservation res) {
    this.reservation = res;
  }



  public Reservation getReservation() {
    return reservation;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public boolean equalCustomerID(Booking other) {
    return customer.getCustomerID() == other.customer.getCustomerID();
  }

  public boolean equalRoomID(Booking other) {
    return room.getRoomID() == other.room.getRoomID();
  }

  public boolean equalStartDate(Booking other) {
    return reservation.getStartDate().equals(other.reservation.getStartDate());
  }

  public boolean equalEndDate(Booking other) {
    return reservation.getEndDate().equals(other.reservation.getEndDate());
  }

  public boolean equalPaid(Booking other) {
    return reservation.getPaid() == other.reservation.getPaid();
  }
}

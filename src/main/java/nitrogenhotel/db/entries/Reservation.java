package nitrogenhotel.db.entries;

import java.time.Instant;

/**
 * Represents the reservation table in the database.
 */
public class Reservation {

  private int reservationID;
  private int customerID;
  private int roomID;
  private Instant startDate;
  private Instant endDate;
  private boolean paid;

  /**
   * Creates a new reservation instance. The parameter is the reservationID of the specified
   * reservation.
   */
  public Reservation(int reservationID) {
    this.reservationID = reservationID;
  }

  /**
   * Creates a new reservation instance. The parameters are all reservation attributes except the
   * reservationID.
   */
  public Reservation(int customerID, int roomID, Instant startDate, Instant endDate, boolean paid) {
    this.customerID = customerID;
    this.roomID = roomID;
    this.startDate = startDate;
    this.endDate = endDate;
    this.paid = paid;
  }

  /**
   * Creates a new reservation instance. The parameters are all reservation attributes.
   */
  public Reservation(int reservationID, int customerID, int roomID, Instant startDate,
      Instant endDate,
      boolean paid) {
    this.reservationID = reservationID;
    this.customerID = customerID;
    this.roomID = roomID;
    this.startDate = startDate;
    this.endDate = endDate;
    this.paid = paid;
  }


  public int getReservationID() {
    return reservationID;
  }

  public int getCustomerID() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public int getRoomID() {
    return roomID;
  }

  public void setRoomID(int roomID) {
    this.roomID = roomID;
  }

  public Instant getStartDate() {
    return startDate;
  }

  public void setStartDate(Instant startDate) {
    this.startDate = startDate;
  }

  public Instant getEndDate() {
    return endDate;
  }

  public void setEndDate(Instant endDate) {
    this.endDate = endDate;
  }

  public boolean getPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }


}

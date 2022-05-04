package nitrogenhotel.utils;

/** Payment enums. */
public enum PaymentMethod {
  VISA("Visa"),
  MASTERCARD("Master Card"),
  PAYPAL("PayPal"),
  CASH("Cash"),
  INVOICE("Invoice"),
  VOUCHER("Voucher");

  public final String str;

  PaymentMethod(String str) {
    this.str = str;
  }

  @Override
  public String toString() {
    return str;
  }
}

package hr.java.restaurant.exception;


/**
 * Exception koji se baca u slučaju da je unesena "neispravna" cijena ili plaća
 */
public class InvalidPriceOrSalaryException extends RuntimeException {
  public InvalidPriceOrSalaryException() {
  }

  public InvalidPriceOrSalaryException(String message) {
    super(message);
  }

  public InvalidPriceOrSalaryException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidPriceOrSalaryException(Throwable cause) {
    super(cause);
  }

  public InvalidPriceOrSalaryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

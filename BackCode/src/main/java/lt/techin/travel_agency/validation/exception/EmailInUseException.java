package lt.techin.travel_agency.validation.exception;

public class EmailInUseException extends RuntimeException {
  public EmailInUseException(String message) {
    super(message);
  }
}

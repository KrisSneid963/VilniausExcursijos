package lt.techin.travel_agency.validation.exception;

public class TravelExistsException extends RuntimeException {
  public TravelExistsException(String message) {
    super(message);
  }
}

package lt.techin.travel_agency.validation;

import lt.techin.travel_agency.validation.exception.EmailInUseException;
import lt.techin.travel_agency.validation.exception.TravelExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(EmailInUseException.class)
  public ResponseEntity<?> handleEmailInUse(EmailInUseException e) {
    return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TravelExistsException.class)
  public ResponseEntity<Map<String, String>> handleTravelExists(TravelExistsException e) {
    return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.BAD_REQUEST);
  }

}

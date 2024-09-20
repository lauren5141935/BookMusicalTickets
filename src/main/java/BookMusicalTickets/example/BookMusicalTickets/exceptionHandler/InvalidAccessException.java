package BookMusicalTickets.example.BookMusicalTickets.exceptionHandler;

public class InvalidAccessException extends RuntimeException {
  public InvalidAccessException(String message) {
    super(message);
  }
}

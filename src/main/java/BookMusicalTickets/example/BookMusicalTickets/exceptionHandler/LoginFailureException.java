package BookMusicalTickets.example.BookMusicalTickets.exceptionHandler;

public class LoginFailureException extends RuntimeException {
  public LoginFailureException(String message) {
    super(message);
  }
}

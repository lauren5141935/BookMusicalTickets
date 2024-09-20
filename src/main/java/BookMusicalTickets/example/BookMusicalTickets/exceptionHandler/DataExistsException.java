package BookMusicalTickets.example.BookMusicalTickets.exceptionHandler;

import lombok.Getter;

@Getter
public class DataExistsException extends RuntimeException {

  private final String type;
  public DataExistsException(String message, String type) {
    super(message);
    this.type = type;
  }
}

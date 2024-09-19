package BookMusicalTickets.example.BookMusicalTickets.exceptionHandler;

import lombok.Getter;
@Getter
public class DataNotExistsException extends RuntimeException {

  private final String type;
  public DataNotExistsException(String message, String type) {
    super(message);
    this.type = type;
  }
}
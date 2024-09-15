package BookMusicalTickets.example.BookMusicalTickets.exception;

import lombok.Getter;

@Getter
public class Exception extends RuntimeException {

  private final ErrorCode errorCode;
  private final String errorMessage;

  public Exception(ErrorCode error) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}

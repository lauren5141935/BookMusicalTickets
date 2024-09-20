package BookMusicalTickets.example.BookMusicalTickets.exceptionHandler;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends RuntimeException {

  ErrorResponse errorResponse;
  public JwtAuthenticationException(String message) {
    super(message);

    this.errorResponse = ErrorResponse.builder()
        .status(HttpStatus.FORBIDDEN)
        .message(message)
        .errorCode("JWT_ERROR")
        .build();
  }

  public ErrorResponse getErrorResponse() {
    return errorResponse;
  }
}

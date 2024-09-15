package BookMusicalTickets.example.BookMusicalTickets.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class UserExceptionHandler {
  @ExceptionHandler(Exception.class)
  public Error handleCommonException(Exception e) {
    log.error("{} is occurred.", e.getErrorCode());

    return new Error(e.getErrorCode(), e.getMessage());
  }
}

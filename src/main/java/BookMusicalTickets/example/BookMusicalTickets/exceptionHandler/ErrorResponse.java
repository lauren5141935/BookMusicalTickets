package BookMusicalTickets.example.BookMusicalTickets.exceptionHandler;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Builder
@Data
@ToString
public class ErrorResponse {

  private HttpStatus status;
  private String errorCode;
  private String message;
}
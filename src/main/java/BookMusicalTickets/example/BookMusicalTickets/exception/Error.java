package BookMusicalTickets.example.BookMusicalTickets.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {

  private ErrorCode errorcode;
  private String errorMessage;
}

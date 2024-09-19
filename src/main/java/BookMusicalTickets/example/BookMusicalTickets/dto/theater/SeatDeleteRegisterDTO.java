package BookMusicalTickets.example.BookMusicalTickets.dto.theater;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SeatDeleteRegisterDTO {
  private String seatsToDelete;

}

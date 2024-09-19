package BookMusicalTickets.example.BookMusicalTickets.dto.theater;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SeatDeleteDTO {

  private int column;
  private Character row;

}

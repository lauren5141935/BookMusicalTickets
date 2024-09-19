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
public class SeatDTO {

  private String seatId;
  private Long theaterId;
  private char row;
  private int column;
  private int price;

}

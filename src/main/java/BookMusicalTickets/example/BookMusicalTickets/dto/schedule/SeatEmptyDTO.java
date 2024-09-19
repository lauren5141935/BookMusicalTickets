package BookMusicalTickets.example.BookMusicalTickets.dto.schedule;

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
public class SeatEmptyDTO {

  private String seatId;
  private char row;
  private int column;
  private int price;
  private boolean isEmpty;

}

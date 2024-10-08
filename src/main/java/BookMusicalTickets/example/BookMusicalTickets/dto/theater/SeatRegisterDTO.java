package BookMusicalTickets.example.BookMusicalTickets.dto.theater;

import java.util.List;
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
public class SeatRegisterDTO {

  private List<String> seatIds;
  private int price;

}

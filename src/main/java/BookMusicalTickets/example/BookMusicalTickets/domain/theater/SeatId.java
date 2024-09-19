package BookMusicalTickets.example.BookMusicalTickets.domain.theater;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatId {

  private String seatId;
  private Theater theater;
}

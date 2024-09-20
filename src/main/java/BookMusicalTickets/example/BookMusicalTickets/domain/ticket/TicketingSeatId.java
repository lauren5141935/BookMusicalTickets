package BookMusicalTickets.example.BookMusicalTickets.domain.ticket;

import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Seat;
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
public class TicketingSeatId {

  private Ticket ticket;
  private Seat seat;

}

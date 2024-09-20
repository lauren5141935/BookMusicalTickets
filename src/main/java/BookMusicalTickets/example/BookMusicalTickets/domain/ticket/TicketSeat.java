package BookMusicalTickets.example.BookMusicalTickets.domain.ticket;

import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Seat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "TICKET_SEAT")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(TicketingSeatId.class)
@Getter
public class TicketSeat {

  @Id
  @JoinColumn(name = "TICKET_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Ticket ticket;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
      @JoinColumn(name = "SEAT_ID", referencedColumnName = "SEAT_ID"),
      @JoinColumn(name = "THEATER_ID", referencedColumnName = "THEATER_ID")
  })
  private Seat seat;
}

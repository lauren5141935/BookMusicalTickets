package BookMusicalTickets.example.BookMusicalTickets.dto.ticket;

import java.sql.Timestamp;
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
public class TicketShortDTO {

  private Long ticketId;
  private Timestamp ticketingTime;
  private String musicalName;
  private String theaterName;
  private Timestamp startTime;
  private boolean isPayed;

}

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
public class CustomerTicketDTO {

  private Long ticketId;
  private Timestamp ticketTime;
  private Long scheduleId;
  private String loginId;
  private boolean isPayed;

}

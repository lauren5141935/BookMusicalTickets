package BookMusicalTickets.example.BookMusicalTickets.dto.ticket;

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
public class TicketBookDTO {

  private Long ticketId;
  private String phoneNo;
  private String password;
  private Long scheduleId;
  private List<String> seats;

}

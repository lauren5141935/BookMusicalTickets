package BookMusicalTickets.example.BookMusicalTickets.dto.ticket;

import BookMusicalTickets.example.BookMusicalTickets.dto.theater.SeatDTO;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketDetailCustomerDTO {

  private Long ticketId;
  private Long scheduleId;
  private Timestamp ticketTime;
  private Long musicalId;
  private String musicalTitle;
  private String theaterName;
  private int floor;
  private Timestamp startTime;
  private int runningTime;
  private String posterFileName;
  private List<SeatDTO> seats;
  private String discount;
  private boolean isPayed;
}

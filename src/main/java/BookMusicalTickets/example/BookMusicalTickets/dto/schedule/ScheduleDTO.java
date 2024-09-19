package BookMusicalTickets.example.BookMusicalTickets.dto.schedule;

import BookMusicalTickets.example.BookMusicalTickets.dto.theater.TheaterDTO;
import java.time.LocalDateTime;
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
public class ScheduleDTO {

  private Long scheduleId;
  private LocalDateTime startTime;
  private String discount;
  private Long musicalId;
  private String musicalName;
  private int runningTime;
  private TheaterDTO theaterDTO;
  private int totalSeat;
  private int filledSeat;
}

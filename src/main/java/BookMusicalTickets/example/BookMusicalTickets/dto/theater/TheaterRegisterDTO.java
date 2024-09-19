package BookMusicalTickets.example.BookMusicalTickets.dto.theater;

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
public class TheaterRegisterDTO {

  private Long theaterId;
  private String name;
  private String type;
  private int floor;

}

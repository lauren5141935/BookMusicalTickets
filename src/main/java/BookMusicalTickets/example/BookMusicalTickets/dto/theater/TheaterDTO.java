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
public class TheaterDTO {
  private Long theaterId;
  private String name;
  private String typeCode;
  private String typeName;
  private int floor;

}

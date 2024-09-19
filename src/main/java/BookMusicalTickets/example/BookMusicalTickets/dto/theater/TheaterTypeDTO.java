package BookMusicalTickets.example.BookMusicalTickets.dto.theater;

import jakarta.validation.constraints.Size;
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
public class TheaterTypeDTO {

  @Size(max = 5)
  private String code;
  private String name;

}

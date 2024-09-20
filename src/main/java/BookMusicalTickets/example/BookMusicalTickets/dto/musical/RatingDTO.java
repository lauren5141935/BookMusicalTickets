package BookMusicalTickets.example.BookMusicalTickets.dto.musical;

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
public class RatingDTO {

  @Size(max = 5)
  private String code;

  private String name;
}

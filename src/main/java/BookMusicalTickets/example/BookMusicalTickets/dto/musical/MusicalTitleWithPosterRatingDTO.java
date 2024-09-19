package BookMusicalTickets.example.BookMusicalTickets.dto.musical;

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
public class MusicalTitleWithPosterRatingDTO {

  private Long musicalId;
  private String title;
  private String fileName;
  private String rating;

}

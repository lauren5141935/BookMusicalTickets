package BookMusicalTickets.example.BookMusicalTickets.dto.musical;

import java.util.Date;

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
public class MusicalTitleDTO {

  private Long musicalId;
  private String title;
  private String directorName;
  private Date releaseDate;

}

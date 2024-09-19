package BookMusicalTickets.example.BookMusicalTickets.dto.musical;

import java.util.Date;
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
public class MusicalDTO {

  private Long musicalId;
  private String title;
  private Date releaseDate;
  private int runningTime;
  private String info;
  private ImageDTO poster;
  private CastInMusicalDTO director;
  private String rating;
  private List<String> genreList;
  private List<RoleInMusicalDTO> roleList;
}

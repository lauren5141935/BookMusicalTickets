package BookMusicalTickets.example.BookMusicalTickets.dto.musical;

import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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
public class MusicalRegisterDTO {

  private Long musicalId;
  private String title;
  @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
  private String releaseDate;
  private int runningTime;
  private String info;
  private MultipartFile poster;
  private Long directorId;
  private String ratingCode;
  private List<String> genreCodes;

}

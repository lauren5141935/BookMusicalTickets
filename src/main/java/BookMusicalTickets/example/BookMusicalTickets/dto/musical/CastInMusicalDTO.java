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
public class CastInMusicalDTO {

  private Long castId;
  private String name;
  private Date birthDate;
  private String fileName;
}


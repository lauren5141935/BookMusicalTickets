package BookMusicalTickets.example.BookMusicalTickets.dto;

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
public class ImageDTO {

  private String uuid;
  private String fileName;
  private String fileUrl;
}

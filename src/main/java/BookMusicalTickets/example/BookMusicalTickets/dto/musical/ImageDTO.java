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
public class ImageDTO {
  private Long imageId;
  private String uuid;
  private String fileName;
  private String fileUrl;
}

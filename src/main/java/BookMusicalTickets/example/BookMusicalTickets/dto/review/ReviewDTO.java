package BookMusicalTickets.example.BookMusicalTickets.dto.review;

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
public class ReviewDTO {

  private Long id;
  private String nickname;
  private String musicalTitle;
  private String content;
  private String photo_url;
  private Integer rating;

}

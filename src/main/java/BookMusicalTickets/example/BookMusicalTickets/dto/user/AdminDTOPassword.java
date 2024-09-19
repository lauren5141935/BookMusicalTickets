package BookMusicalTickets.example.BookMusicalTickets.dto.user;

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
public class AdminDTOPassword {

  private Long adminId;
  private String name;
  private String loginId;
}

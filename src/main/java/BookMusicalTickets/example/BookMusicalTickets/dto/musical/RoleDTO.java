package BookMusicalTickets.example.BookMusicalTickets.dto.musical;

import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Cast;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
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
public class RoleDTO {

  private Musical musical;
  private Cast cast;
  private String role;
  private boolean starring;

}

package BookMusicalTickets.example.BookMusicalTickets.dto.ticket;

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
public class NonMemberTicketModifyDTO {

  private Long ticketId;
  private String password;
  private String newPassword;
  private List<String> seats;

}

package BookMusicalTickets.example.BookMusicalTickets.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NonMemberDTO {

  private String phoneNo;
  private String password;

}

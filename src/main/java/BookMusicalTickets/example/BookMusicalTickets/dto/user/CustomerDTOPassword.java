package BookMusicalTickets.example.BookMusicalTickets.dto.user;

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
public class CustomerDTOPassword {

  private String name;
  private String loginId;
  private String nickname;
  private Date birthdate;
  private int gender;
  private String phoneNo;
  private String email;
  private int point;

}

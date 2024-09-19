package BookMusicalTickets.example.BookMusicalTickets.dto.musical;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleAddDTO {

  private Long musicalId;
  private Long castId;
  private String role;
  private boolean starring;

}

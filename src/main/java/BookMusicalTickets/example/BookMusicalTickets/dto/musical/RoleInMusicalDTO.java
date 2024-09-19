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
public class RoleInMusicalDTO {

  private String name;
  private Long castId;
  private String role;
  private boolean starring;
  private String profileImage;

}

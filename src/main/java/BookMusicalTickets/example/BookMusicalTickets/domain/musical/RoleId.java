package BookMusicalTickets.example.BookMusicalTickets.domain.musical;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleId implements Serializable {
  private Musical musical;
  private Cast cast;
}

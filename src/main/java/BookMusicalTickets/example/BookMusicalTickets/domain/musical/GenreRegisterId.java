package BookMusicalTickets.example.BookMusicalTickets.domain.musical;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
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
public class GenreRegisterId implements Serializable {
  private Code genre;
  private Musical musical;
}
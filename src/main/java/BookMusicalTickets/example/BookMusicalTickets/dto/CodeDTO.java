package BookMusicalTickets.example.BookMusicalTickets.dto;


import jakarta.validation.constraints.Size;
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
public class CodeDTO {

  @Size(max = 5)
  private String code;
  private String name;
}

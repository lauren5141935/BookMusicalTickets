package BookMusicalTickets.example.BookMusicalTickets.dto.payment;

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
public class PaymentRegisterDTO {

  private Long ticketId;
  private String code;

}

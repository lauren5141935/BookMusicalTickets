package BookMusicalTickets.example.BookMusicalTickets.dto.payment;

import java.sql.Timestamp;
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
public class PaymentShortDTO {

  private Long paymentId;
  private Timestamp paymentTime;
  private String musicalTitle;
  private int price;

}
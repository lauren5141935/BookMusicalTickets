package BookMusicalTickets.example.BookMusicalTickets.dto.payment;

import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalTitleWithPosterRatingDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.SeatDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.TheaterDTO;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailDTO {

  private Long paymentId;
  private int price;
  private Timestamp paymentTime;
  private String method;
  private boolean status;
  private MusicalTitleWithPosterRatingDTO musical;
  private TheaterDTO theater;
  private List<SeatDTO> seats;

}


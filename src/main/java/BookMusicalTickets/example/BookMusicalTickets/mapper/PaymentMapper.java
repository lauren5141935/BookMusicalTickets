package BookMusicalTickets.example.BookMusicalTickets.mapper;

import BookMusicalTickets.example.BookMusicalTickets.domain.payment.Payment;
import BookMusicalTickets.example.BookMusicalTickets.dto.payment.PaymentDetailDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.payment.PaymentShortDTO;
import java.util.stream.Collectors;

public class PaymentMapper {

  private PaymentMapper(){}

  public static PaymentDetailDTO paymentToPaymentDetailDTO(Payment payment) {
    return PaymentDetailDTO.builder()
        .paymentId(payment.getPaymentId())
        .price(payment.getPrice())
        .paymentTime(payment.getPaymentTime())
        .method(payment.getMethod().getName())
        .status(payment.isStatus())
        .musical(MusicalMapper.musicalToMusicalTitleWithPosterRatingDTO(payment.getTicket().getSchedule().getMusical()))
        .theater(TheaterMapper.theaterToTheaterDTO(payment.getTicket().getSchedule().getTheater()))
        .seats(payment.getTicket().getTicketSeats().stream().map(ticketSeat -> SeatMapper.seatToSeatDTO(ticketSeat.getSeat())).collect(Collectors.toList()))
        .build();
  }

  public static PaymentShortDTO paymentToPaymentShortDTO(Payment payment) {
    return PaymentShortDTO.builder()
        .paymentId(payment.getPaymentId())
        .paymentTime(payment.getPaymentTime())
        .musicalTitle(payment.getTicket().getSchedule().getMusical().getTitle())
        .price(payment.getPrice())
        .build();
  }
}

package BookMusicalTickets.example.BookMusicalTickets.mapper;

import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Seat;
import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Theater;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.Ticket;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.TicketSeat;
import BookMusicalTickets.example.BookMusicalTickets.dto.schedule.SeatEmptyDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.SeatDTO;

public class SeatMapper {

  private SeatMapper() {}

  public static Seat seatRegisterDTOToSeat(String seatId, int price, Theater theater) {
    return Seat.builder()
        .theater(theater)
        .row(seatId.charAt(0))
        .column(Integer.parseInt(seatId.substring(1)))
        .price(price)
        .build();
  }

  public static SeatDTO seatToSeatDTO(Seat seat) {
    return SeatDTO.builder()
        .seatId(seat.getSeatId())
        .theaterId(seat.getTheater().getTheaterId())
        .price(seat.getPrice())
        .column(seat.getColumn())
        .row(seat.getRow())
        .build();
  }

  public static SeatEmptyDTO seatDTOToSeatEmptyDTO(SeatDTO seatDTO) {
    return SeatEmptyDTO.builder()
        .seatId(seatDTO.getSeatId())
        .isEmpty(true)
        .row(seatDTO.getRow())
        .column(seatDTO.getColumn())
        .price(seatDTO.getPrice())
        .build();
  }

  public static TicketSeat SeatToTicketingSeat(Ticket ticket, Seat seat) {
    return TicketSeat.builder()
        .ticket(ticket)
        .seat(seat)
        .build();
  }

}

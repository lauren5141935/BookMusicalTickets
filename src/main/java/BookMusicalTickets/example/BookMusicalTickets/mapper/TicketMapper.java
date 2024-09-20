package BookMusicalTickets.example.BookMusicalTickets.mapper;

import BookMusicalTickets.example.BookMusicalTickets.domain.schedule.Schedule;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.Ticket;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Customer;
import BookMusicalTickets.example.BookMusicalTickets.dto.ticket.CustomerTicketDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.ticket.TicketBookDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.ticket.TicketDetailCustomerDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.ticket.TicketShortDTO;
import java.sql.Timestamp;

public class TicketMapper {

  private TicketMapper() {}

  public static Ticket TicketBookDTOToTicket(TicketBookDTO ticketBookDTO, Customer customer, Schedule schedule) {
    return Ticket.builder()
        .password(ticketBookDTO.getPassword())
        .phoneNo(ticketBookDTO.getPhoneNo())
        .customer(customer)
        .schedule(schedule)
        .build();
  }

  public static TicketShortDTO ticketToTicketShortDTO(Ticket ticket, String musicalId, String theaterName, Timestamp startTime) {
    return TicketShortDTO.builder()
        .ticketId(ticket.getTicketId())
        .ticketingTime(ticket.getTicketTime())
        .musicalName(musicalId)
        .theaterName(theaterName)
        .startTime(startTime)
        .isPayed(ticket.getPayment() != null ? ticket.getPayment().isStatus() : false)
        .build();
  }

  public static CustomerTicketDTO ticketToTicketDTO(Ticket ticket) {
    return CustomerTicketDTO.builder()
        .ticketId(ticket.getTicketId())
        .ticketTime(ticket.getTicketTime())
        .loginId(ticket.getCustomer() != null ? ticket.getCustomer().getLoginId() : null)
        .scheduleId(ticket.getSchedule().getScheduleId())
        .isPayed(ticket.getPayment() != null ? ticket.getPayment().isStatus() : false)
        .build();
  }

  /**
   * 좌석은 제외
   **/
  public static TicketDetailCustomerDTO ticketToTicketDetailCustomerDTO(Ticket ticket) {
    return TicketDetailCustomerDTO.builder()
        .ticketId(ticket.getTicketId())
        .scheduleId(ticket.getSchedule().getScheduleId())
        .ticketTime(ticket.getTicketTime())
        .musicalId(ticket.getSchedule().getMusical().getMusicalId())
        .musicalTitle(ticket.getSchedule().getMusical().getTitle())
        .theaterName(ticket.getSchedule().getTheater().getName())
        .floor(ticket.getSchedule().getTheater().getFloor())
        .startTime(ticket.getSchedule().getStartTime())
        .runningTime(ticket.getSchedule().getMusical().getRunningTime())
        .posterFileName(ticket.getSchedule().getMusical().getPoster().getFileName())
        .discount(ticket.getSchedule().getDiscount())
        .isPayed(ticket.getPayment() != null ? ticket.getPayment().isStatus() : false)
        .build();
  }
}

package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.schedule.Schedule;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.TicketSeat;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.TicketingSeatId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketSeatRepository extends JpaRepository<TicketSeat, TicketingSeatId> {

  @Query("select ts.seat.seatId from TicketSeat ts inner join ts.ticket tk where tk.schedule = :schedule")
  List<String> findSeatIdBySchedule(Schedule schedule);

  @Query("select ts from TicketSeat ts inner join ts.ticket tk where tk.schedule = :schedule")
  List<TicketSeat> findSeatBySchedule(Schedule schedule);

  @Query("select count(*) from TicketSeat ts inner join ts.ticket tk where tk.schedule = :schedule")
  Integer countFilledSeatBySchedule(Schedule schedule);

  @Query("select ts from TicketSeat ts where ts.ticket.ticketId = :ticketId")
  List<TicketSeat> findAllByTicketId(Long ticketId);

}

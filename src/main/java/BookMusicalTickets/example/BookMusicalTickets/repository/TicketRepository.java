package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.Ticket;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> findAllByCustomer(Customer customer);
}

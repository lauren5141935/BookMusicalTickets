package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.payment.Payment;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

  @Query("select p from Payment p inner join Customer c on p.ticket.customer = c where p.ticket.customer.loginId = :loginId")
  List<Payment> findAllByCustomerLoginId(@Param("loginId") String loginId);

  boolean existsByTicket(Ticket ticket);
}
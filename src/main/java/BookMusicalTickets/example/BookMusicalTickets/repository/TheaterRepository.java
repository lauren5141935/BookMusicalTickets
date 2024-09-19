package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

  boolean existsByName(String name);

  boolean existsByType(Code type);
}

package BookMusicalTickets.example.BookMusicalTickets.repository;


import BookMusicalTickets.example.BookMusicalTickets.domain.user.Authority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

  Optional<Authority> findByLoginId(String loginId);
  boolean existsByLoginId(String loginId);
}

package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.musical.GenreRegister;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.GenreRegisterId;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRegisterRepository extends JpaRepository<GenreRegister, GenreRegisterId> {

  List<GenreRegister> findAllByMusical(Musical musical);

  void deleteAllByMusical(Musical musical);

}

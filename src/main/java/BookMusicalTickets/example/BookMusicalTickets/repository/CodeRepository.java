package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodeRepository extends JpaRepository<Code, String> {

  @Query("select c from Code c where c.code = 'GR0'")
  Code findGenreUpperCode();

  @Query("select c from Code c where c.code = 'RT0'")
  Code findRatingUpperCode();

  @Query("select c from Code c where c.code = 'PM0'")
  Code findPaymentMethodUpperCode();

  @Query("select c from Code c where c.code = 'TH0'")
  Code findTheaterTypeUpperCode();

  List<Code> findAllByUpperCode(Code code);

  boolean existsByName(String name);
}

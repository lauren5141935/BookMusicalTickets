package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("SELECT AVG(r.rating) FROM Review r WHERE r.musical.id = :musicalId")
  Double findAverageRatingByMusicalId(@Param("musicalId") Long musicalId);

  boolean existsByTicketId(Long ticketId);

  Page<Review> findAllByCustomerId(Long customerId, Page page);
}

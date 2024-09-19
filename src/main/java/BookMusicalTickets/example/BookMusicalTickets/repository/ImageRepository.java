package BookMusicalTickets.example.BookMusicalTickets.repository;

import java.awt.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

  boolean existsByFileUrl(String fileUrl);

}

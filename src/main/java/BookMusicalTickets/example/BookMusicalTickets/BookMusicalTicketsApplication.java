package BookMusicalTickets.example.BookMusicalTickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookMusicalTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMusicalTicketsApplication.class, args);
	}
}

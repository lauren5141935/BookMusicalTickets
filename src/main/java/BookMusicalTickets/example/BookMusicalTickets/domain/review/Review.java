package BookMusicalTickets.example.BookMusicalTickets.domain.review;

import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.Ticket;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "reservation_id")
  private Ticket ticket;

  @ManyToOne
  @JoinColumn(name = "musical_id")
  private Musical musical;

  @Column(length = 250)
  private String content;

  private String photo_url;

  private Integer rating;

}
package BookMusicalTickets.example.BookMusicalTickets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Seat {
  @Entity
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @Table(name = "seat")
  public class seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    private String column;

    private Integer row;

  }
}

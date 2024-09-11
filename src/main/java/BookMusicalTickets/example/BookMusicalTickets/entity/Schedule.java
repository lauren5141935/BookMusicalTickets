package BookMusicalTickets.example.BookMusicalTickets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
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
  @Table(name = "schedule")
  public class Schedule extends baseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "musical_id")
    private Musical musical;

    private LocalTime startTime;

    private LocalTime ticketOpen;

  }

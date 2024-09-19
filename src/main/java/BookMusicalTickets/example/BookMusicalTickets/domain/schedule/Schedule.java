package BookMusicalTickets.example.BookMusicalTickets.domain.schedule;

import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Theater;
import BookMusicalTickets.example.BookMusicalTickets.domain.ticket.Ticket;
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
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Builder
@Table(name = "SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_sequence")
  @SequenceGenerator(name = "schedule_sequence", sequenceName = "schedule_sequence", allocationSize = 1)
  @Column(name = "SCHEDULE_ID")
  private Long scheduleId;

  @Column(name = "START_TIME")
  private Timestamp startTime;

  @Column(name = "DISCOUNT")
  private String discount;

  @JoinColumn(name = "MUSICAL_ID", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Musical musical;

  @JoinColumn(name = "THEATER_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Theater theater;

  @OneToMany(mappedBy = "schedule", cascade = CascadeType.DETACH)
  private List<Ticket> ticket;

}
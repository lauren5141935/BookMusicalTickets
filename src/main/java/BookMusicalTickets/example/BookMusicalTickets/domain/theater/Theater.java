package BookMusicalTickets.example.BookMusicalTickets.domain.theater;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;

@Entity
@Table(name = "THEATER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Theater {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theater_sequence")
  @SequenceGenerator(name = "theater_sequence", sequenceName = "theater_sequence", allocationSize = 1)
  @Column(name = "THEATER_ID")
  private Long theaterId;

  @Column(name = "NAME",nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "Address", nullable = false)
  private Code address;

  @Column(name = "FLOOR", nullable = false)
  private int floor;

  @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
  private List<Seat> seats = new ArrayList<>();

  @Builder
  public Theater(Long theaterId, String name, Code address, int floor) {
    this.theaterId = theaterId;
    this.name = name;
    this.address = address;
    this.floor = floor;
  }
}
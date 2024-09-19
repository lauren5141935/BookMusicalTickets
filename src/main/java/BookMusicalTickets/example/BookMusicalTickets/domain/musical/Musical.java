package BookMusicalTickets.example.BookMusicalTickets.domain.musical;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import jakarta.persistence.*;
import java.awt.Image;
import lombok.*;
import java.sql.Date;
import java.util.List;


@Entity
@Builder
@Table(name = "MUSICAL")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Musical {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "musical_sequence")
  @SequenceGenerator(name = "musical_sequence", sequenceName = "musical_sequence", allocationSize = 1)
  @Column(name = "MUSICAL_ID")
  private Long musicalId;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "RELEASE_DATE", nullable = false)
  private Date releaseDate;

  @Column(name = "RUNNING_TIME", nullable = false)
  private int runningTime;

  @Column(name = "INFO", nullable = false)
  private String info;

  @OneToOne
  @JoinColumn(name = "POSTER")
  private Image poster;

  @OneToOne
  @JoinColumn(name = "DIRECTOR", nullable = false)
  private Cast director;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RATING", nullable = false)
  private Code rating;

  @OneToMany(mappedBy = "musical", cascade = CascadeType.ALL)
  private List<Role> roles;

  @OneToMany(mappedBy = "musical", cascade = CascadeType.ALL)
  private List<GenreRegister> genreList;

}
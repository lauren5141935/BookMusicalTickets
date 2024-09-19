package BookMusicalTickets.example.BookMusicalTickets.domain.musical;

import BookMusicalTickets.example.BookMusicalTickets.domain.user.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.awt.Image;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CAST")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Cast {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cast_sequence")
  @SequenceGenerator(name = "cast_sequence", sequenceName = "cast_sequence", allocationSize = 1)
  @Column(name = "CAST_ID")
  private Long castId;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "BIRTHDATE", nullable = false)
  private Date birthDate;

  @Column(name = "INFO", nullable = false)
  private String info;

  @OneToMany(mappedBy = "cast", cascade = CascadeType.ALL)
  private List<Role> roles;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PROFILE_IMAGE")
  private Image profileImage;

}
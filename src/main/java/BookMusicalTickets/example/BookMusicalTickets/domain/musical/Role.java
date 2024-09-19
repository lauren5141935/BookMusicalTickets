package BookMusicalTickets.example.BookMusicalTickets.domain.musical;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROLE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(RoleId.class)
@Builder
public class Role {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MUSICAL_ID")
  private Musical musical;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CAST_ID")
  private Cast cast;

  @Column(name = "ROLE", nullable = false)
  private String role;

  @Column(name = "STARRING", nullable = false)
  private boolean starring;
}
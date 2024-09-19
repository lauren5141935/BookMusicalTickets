package BookMusicalTickets.example.BookMusicalTickets.domain.musical;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
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
@Table(name = "GENRE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(GenreRegisterId.class)
@Builder
public class GenreRegister {

  @Id
  @JoinColumn(name = "GENRE_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Code genre;

  @Id
  @JoinColumn(name = "Musical_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Musical musical;

}

package BookMusicalTickets.example.BookMusicalTickets.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "CODE")
@AllArgsConstructor
@ToString
@Getter
public class Code {

  @Id
  @Column(name = "CODE")
  private String code;

  @Column(name = "NAME")
  private String name;

  @ManyToOne
  @JoinColumn(name = "UPPER_CODE")
  private Code upperCode;

}

package BookMusicalTickets.example.BookMusicalTickets.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "POSTER_IMAGE")
@AllArgsConstructor
@ToString
@Getter
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poster_sequence")
  @SequenceGenerator(name = "poster_sequence", sequenceName = "poster_sequence", allocationSize = 1)
  @Column(name = "IMAGE_ID")
  private Long imageId;

  @Column(name = "UUID", nullable = false)
  private String uuid;
  @Column(name = "FILE_NAME", nullable = false)
  private String fileName;
  @Column(name = "FILE_URL", nullable = false)
  private String fileUrl;

}

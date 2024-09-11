package BookMusicalTickets.example.BookMusicalTickets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
  @Table(name = "musical")
  public class Musical extends baseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String genre;

    private String plot;

    private String runningTime;

    private String viewClass;
  }
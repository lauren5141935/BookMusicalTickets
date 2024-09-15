package BookMusicalTickets.example.BookMusicalTickets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import javax.management.relation.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class User {

  @Entity
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @Table(name = "user")
  public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private LocalDateTime birthdate;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
  }
}

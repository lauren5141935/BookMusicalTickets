package BookMusicalTickets.example.BookMusicalTickets.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AUTHORITY")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Authority {

  @Id
  @Column(name = "login_id")
  private String loginId;

  @Column(name = "authority")
  private String authority;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @OneToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  @OneToOne
  @JoinColumn(name = "ADMIN_ID")
  private Admin admin;

  public void setPassword(String encryptedPassword) {
    this.password = encryptedPassword;
  }
}
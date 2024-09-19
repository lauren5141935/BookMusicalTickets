package BookMusicalTickets.example.BookMusicalTickets.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

import java.time.LocalDateTime;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@Table(name = "Admin")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public class Admin implements Persistable<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_sequence")
  @SequenceGenerator(name = "admin_sequence", sequenceName = "admin_sequence", allocationSize = 1)
  @Column(name = "ADMIN_ID")
  private Long adminId;


  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "LOGIN_ID", nullable = false)
  private String loginId;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  @Override
  public Long getId() {
    return adminId;
  }

  @Override
  public boolean isNew() {
    return createdAt == null;
  }
}
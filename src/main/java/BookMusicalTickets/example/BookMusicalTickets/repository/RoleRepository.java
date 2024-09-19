package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.RoleId;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, RoleId> {

  List<Role> findAllByMusical(Musical musical);

  @Modifying
  @Query("update Role r set r.role = :role, r.starring = :starring where r.musical = :#{#id.musical} and r.cast = :#{#id.cast}")
  void updateRoleById(@Param("id") RoleId roleId, @Param("role") String role, @Param("starring") boolean starring);

}

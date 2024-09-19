package BookMusicalTickets.example.BookMusicalTickets.repository;

import BookMusicalTickets.example.BookMusicalTickets.domain.schedule.Schedule;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  @Query("select sc from Schedule sc where sc.startTime > :currentTime")
  List<Schedule> findAllShowing(Timestamp currentTime);

  @Query("select sc from Schedule sc where sc.startTime > :startTime and sc.startTime < :endTime")
  List<Schedule> findAllShowingDuration(Timestamp startTime, Timestamp endTime);

  @Query("select count(*) > 0 from Schedule sc where sc.musical.musicalId = :musicalId")
  boolean existsByMusicalId(@Param("musicalId") Long musicalId);
}

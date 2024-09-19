package BookMusicalTickets.example.BookMusicalTickets.mapper;

import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
import BookMusicalTickets.example.BookMusicalTickets.domain.schedule.Schedule;
import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Theater;
import BookMusicalTickets.example.BookMusicalTickets.dto.schedule.ScheduleAddDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.schedule.ScheduleDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleMapper {

  private ScheduleMapper() {}

  public static Schedule scheduleAddDTOToSchedule(ScheduleAddDTO scheduleAddDTO, Theater theater, Musical musical) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Timestamp startTime = Timestamp.valueOf(LocalDateTime.parse(scheduleAddDTO.getStartTime(), formatter));
    return Schedule.builder()
        .scheduleId(scheduleAddDTO.getScheduleId())
        .theater(theater)
        .musical(musical)
        .startTime(startTime)
        .discount(scheduleAddDTO.getDiscount())
        .build();
  }

  public static ScheduleDTO scheduleToScheduleDTO(Schedule schedule) {
    return ScheduleDTO.builder()
        .scheduleId(schedule.getScheduleId())
        .theaterDTO(TheaterMapper.theaterToTheaterDTO(schedule.getTheater()))
        .startTime(schedule.getStartTime().toLocalDateTime())
        .discount(schedule.getDiscount())
        .musicalId(schedule.getMusical().getMusicalId())
        .musicalName(schedule.getMusical().getTitle())
        .runningTime(schedule.getMusical().getRunningTime())
        .build();
  }
}

package BookMusicalTickets.example.BookMusicalTickets.controller;

import BookMusicalTickets.example.BookMusicalTickets.dto.schedule.ScheduleAddDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.schedule.ScheduleDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.schedule.SeatEmptyDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.SeatDTO;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DateErrorException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.InvalidAccessException;
import BookMusicalTickets.example.BookMusicalTickets.service.ScheduleService;
import BookMusicalTickets.example.BookMusicalTickets.service.TheaterService;
import BookMusicalTickets.example.BookMusicalTickets.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalTitleWithPosterRatingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "상영일정 관련", description = "상영 일정 조회 및 수정 API")
public class ScheduleController {

  private final ScheduleService scheduleService;
  private final TheaterService theaterService;
  private final TicketService ticketService;

  @PostMapping("/add")
  public List<ScheduleDTO> addSchedule(@RequestBody ScheduleAddDTO scheduleAddDTO) {
    log.info("schedule add request: " + scheduleAddDTO);
    return scheduleService.updateSchedule(scheduleAddDTO);
  }

  @PostMapping("/{id}/modify")
  public List<ScheduleDTO> modifySchedule(@PathVariable("id") Long scheduleId, @RequestBody ScheduleAddDTO scheduleAddDTO) {
    log.info("schedule modify request: " + scheduleAddDTO);
    if(!scheduleId.equals(scheduleAddDTO.getScheduleId())) {
      throw new InvalidAccessException("잘못된 데이터/링크로 수정을 시도하였습니다.");
    }

    return scheduleService.modifySchedule(scheduleAddDTO);
  }

  @DeleteMapping("/{id}/delete")
  public void deleteSchedule(@PathVariable("id") Long scheduleId) {
    log.info("schedule delete request: " + scheduleId);
    scheduleService.deleteSchedule(scheduleId);
  }

  @GetMapping("/date/{date}")
  public List<ScheduleDTO> getScheduleByDate(@PathVariable("date")String date) {
    log.info("schedule by date: " + date);
    LocalDate now = LocalDate.now();
    LocalDate inputDate = LocalDate.parse(date);
    if(inputDate.isBefore(now)) {
      throw new DateErrorException("현재보다 이전 날짜로는 입력할 수 없습니다.");
    }
    return scheduleService.getScheduleSortByDate(inputDate);
  }

  //뮤지컬에 따른 상여하는 뮤지컬 공연 조회
  @GetMapping("/musical/{musicalId}")
  public List<ScheduleDTO> getScheduleByMusical(@PathVariable("musicalId") Long musicalId) {
    log.info("schedule by musical: " + musicalId);
    return scheduleService.getScheduleSortByMusical(musicalId);
  }

  @GetMapping("/allMusical")
  public List<MusicalTitleWithPosterRatingDTO> getAllShowingMusicalsTitle() {
    log.info("all showing musicals");
    return scheduleService.getShowingMusicalsOnlyTitle();
  }

  @GetMapping("/previous")
  public List<ScheduleDTO> getPreviousSchedules(@RequestParam("start") String startDate, @RequestParam("end") String endDate) {
    log.info("previous schedules, startDate: " + startDate + ", endDate: " + endDate);
    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);
    LocalDate now = LocalDate.now();

    if(start.isAfter(end)) {
      throw new DateErrorException("끝나는 날짜가 시작 날짜보다 빠를 순 없습니다.");
    }

    if(end.isAfter(now.plusDays(1))) {
      throw new DateErrorException("과거의 공연 일정만 조회할 수 있습니다.");
    }

    return scheduleService.getScheduleFromDateToDate(start, end);
  }

  @GetMapping("/{id}/seats")
  public List<SeatEmptyDTO> getEmptySeats(@PathVariable("id") Long scheduleId) {
    log.info("empty seats load: " + scheduleId);
    ScheduleDTO scheduleDTO = scheduleService.getScheduleDetail(scheduleId);

    List<SeatDTO> originalSeats = theaterService.getSeats(scheduleDTO.getTheaterDTO().getTheaterId());
    return ticketService.getTicketedSeats(scheduleId, originalSeats);
  }

  @GetMapping("/{id}/seats/ticket")
  public List<SeatEmptyDTO> getEmptySeatsWithTicket(@PathVariable("id") Long scheduleId, @RequestParam("id") Long ticketId) {
    log.info("empty seats while ticket modifying");
    ScheduleDTO scheduleDTO = scheduleService.getScheduleDetail(scheduleId);

    List<SeatDTO> originalSeats = theaterService.getSeats(scheduleDTO.getTheaterDTO().getTheaterId());
    return ticketService.getTicketedSeatsWithTicket(scheduleId, originalSeats, ticketId);
  }
}
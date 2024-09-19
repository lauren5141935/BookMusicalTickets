package BookMusicalTickets.example.BookMusicalTickets.service;

import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Role;
import BookMusicalTickets.example.BookMusicalTickets.domain.schedule.Schedule;
import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Theater;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalTitleWithPosterRatingDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.schedule.ScheduleAddDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.schedule.ScheduleDTO;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataNotExistsException;
import BookMusicalTickets.example.BookMusicalTickets.mapper.MusicalMapper;
import BookMusicalTickets.example.BookMusicalTickets.mapper.ScheduleMapper;
import BookMusicalTickets.example.BookMusicalTickets.repository.GenreRegisterRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.MusicalRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.RoleRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.ScheduleRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.SeatRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.TheaterRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.TicketSeatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final MusicalRepository musicalRepository;
  private final TheaterRepository theaterRepository;
  private final TicketSeatRepository ticketSeatRepository;
  private final SeatRepository seatRepository;
  private final GenreRegisterRepository genreRegisterRepository;
  private final RoleRepository roleRepository;

  @Transactional
  public List<ScheduleDTO> updateSchedule(ScheduleAddDTO scheduleAddDTO) {
    Musical musical = musicalRepository.findById(scheduleAddDTO.getMusicalId()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬입니다.", "Musical"));
    Theater theater = (scheduleAddDTO.getTheaterId() != null) ? theaterRepository.findById(scheduleAddDTO.getTheaterId())
        .orElseThrow((() -> new DataNotExistsException("존재하지 않는 공연장 ID 입니다.", "Theater"))) : null;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime startTimeOfRegister = LocalDateTime.parse(scheduleAddDTO.getStartTime(), formatter);
    List<ScheduleDTO> scheduleOfDate = getScheduleSortByDate(startTimeOfRegister.toLocalDate());

    //해당 시간에 해당 공연장에 뮤지컬 상영예정인 경우
    if(scheduleOfDate.stream().filter(scheduleDTO -> scheduleDTO.getTheaterDTO().getTheaterId().equals(scheduleAddDTO.getTheaterId()))
        .anyMatch(scheduleDTO ->
            !(scheduleDTO.getStartTime().isAfter(startTimeOfRegister.plusMinutes(musical.getRunningTime()))
                || scheduleDTO.getStartTime().plusMinutes(musicalRepository.findById(scheduleDTO.getMusicalId()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬입니다.", "Musical")).getRunningTime()).isBefore(startTimeOfRegister)))) {
      throw new DateErrorException("해당 공연장에서 이미 공연 예정인 뮤지컬이 존재합니다. 다른 시간을 선택해주세요");
    }

    scheduleRepository.save(ScheduleMapper.scheduleAddDTOToSchedule(scheduleAddDTO, theater, musical));

    return getShowingSchedule().stream().map(schedule -> ScheduleMapper.scheduleToScheduleDTO(schedule)).collect(Collectors.toList());
  }

  @Transactional
  public List<ScheduleDTO> modifySchedule(ScheduleAddDTO scheduleAddDTO) {
    if(!scheduleRepository.existsById(scheduleAddDTO.getScheduleId())) {
      throw new DataNotExistsException("존재하지 않는 공연 일정입니다.", "Schedule");
    }

    return updateSchedule(scheduleAddDTO);
  }

  @Transactional
  public void deleteSchedule(Long scheduleId) {
    scheduleRepository.deleteById(scheduleId);
  }

  @Transactional(readOnly = true)
  public List<ScheduleDTO> getScheduleSortByDate(LocalDate date) {

    List<Schedule> schedules = getShowingSchedule()
        .stream()
        .filter(schedule -> schedule.getStartTime().before(Timestamp.valueOf(date.plusDays(1).atStartOfDay()))
            && schedule.getStartTime().after(Timestamp.valueOf(date.atStartOfDay())))
        .collect(Collectors.toList());
    return schedules.stream().map(schedule -> getLeftSeats(schedule)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<ScheduleDTO> getScheduleSortByMusical(Long musicalId) {

    return getShowingSchedule().stream().filter(schedule -> schedule.getMusical().getMusicalId().equals(musicalId)).map(schedule -> getLeftSeats(schedule)).collect(Collectors.toList());
  }

  private ScheduleDTO getLeftSeats(Schedule schedule) {
    ScheduleDTO scheduleDTO = ScheduleMapper.scheduleToScheduleDTO(schedule);
    scheduleDTO.setFilledSeat(ticketSeatRepository.countFilledSeatBySchedule(schedule));
    scheduleDTO.setTotalSeat(seatRepository.countSeatByTheater(schedule.getTheater()));

    return scheduleDTO;
  }


  private List<Schedule> getShowingSchedule() {
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    return scheduleRepository.findAllShowing(currentTime)
        .stream()
        .sorted(((o1, o2) -> {
          int result = o1.getMusical().getTitle().compareTo(o2.getMusical().getTitle());
          if(result == 0) {
            return o1.getStartTime().compareTo(o2.getStartTime());
          }
          return result;
        }))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<MusicalTitleWithPosterRatingDTO> getShowingMusicalsOnlyTitle() {
    List<Schedule> schedules = getShowingSchedule();
    return schedules.stream().map(schedule -> schedule.getMusical()).distinct().map(musical -> MusicalMapper.musicalToMusicalTitleWithPosterRatingDTO(musical)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<MusicalDTO> getShowingMusicals() {
    List<Schedule> schedules = getShowingSchedule();
    return schedules.stream().map(schedule -> schedule.getMusical()).distinct().map(musical -> MusicalMapper.musicalToMusicalDTO(musical,
        genreRegisterRepository.findAllByMusical(musical)
            .stream()
            .map(genreRegister -> genreRegister.getGenre().getName())
            .collect(Collectors.toList())
        , roleRepository.findAllByMusical(musical).stream().sorted(Comparator.comparing(Role::isStarring).reversed().thenComparing(role -> role.getCast().getName())).collect(Collectors.toList()))).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<ScheduleDTO> getScheduleFromDateToDate(LocalDate startDate, LocalDate endDate) {
    Timestamp startTime, endTime;

    startTime = Timestamp.valueOf(startDate.atStartOfDay());
    endTime = endDate.isEqual(LocalDate.now()) ? Timestamp.valueOf(LocalDateTime.now().minusSeconds(1)) : Timestamp.valueOf(endDate.plusDays(1).atStartOfDay());

    return scheduleRepository.findAllShowingDuration(startTime, endTime).stream().map(schedule -> ScheduleMapper.scheduleToScheduleDTO(schedule)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ScheduleDTO getScheduleDetail(Long scheduleId) {
    Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 공연 일정 ID입니다.", "SCHEDULE"));
    return ScheduleMapper.scheduleToScheduleDTO(schedule);
  }
}

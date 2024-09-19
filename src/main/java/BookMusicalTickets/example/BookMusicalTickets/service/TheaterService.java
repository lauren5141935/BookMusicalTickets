package BookMusicalTickets.example.BookMusicalTickets.service;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Seat;
import BookMusicalTickets.example.BookMusicalTickets.domain.theater.SeatId;
import BookMusicalTickets.example.BookMusicalTickets.domain.theater.Theater;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.SeatDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.SeatDeleteRegisterDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.SeatRegisterDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.TheaterDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.TheaterRegisterDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.theater.TheaterTypeDTO;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataExistsException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataNotExistsException;
import BookMusicalTickets.example.BookMusicalTickets.mapper.SeatMapper;
import BookMusicalTickets.example.BookMusicalTickets.mapper.TheaterMapper;
import BookMusicalTickets.example.BookMusicalTickets.repository.CodeRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.SeatRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.TheaterRepository;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TheaterService {

  private final TheaterRepository theaterRepository;
  private final SeatRepository seatRepository;
  private final CodeRepository codeRepository;

  @Transactional
  public TheaterDTO register(TheaterRegisterDTO registerDTO) {
    if(theaterRepository.existsByName(registerDTO.getName())) {
      throw new DataExistsException("이미 존재하는 공연장입니다.", "theater");
    }
    Theater theater = theaterRepository.save(
        TheaterMapper.theaterRegisterDTOToTheater(registerDTO, codeRepository.findById(registerDTO.getType()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 상영관 코드 타입 입니다.", "THEATER"))));
    return TheaterMapper.theaterToTheaterDTO(theater);
  }

  @Transactional
  public void registerSeat(Long theaterId, SeatRegisterDTO seatToRegister) {
    Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 공연장ID입니다.", "Theater"));

    seatToRegister.getSeatIds().forEach(seatId -> {
      if(seatId.charAt(0) < 'A' || seatId.charAt(0) > 'Z') {
        throw new InvalidDataException("열은 A~Z의 대문자이어야 합니다: " + seatId);
      }
      if(Integer.parseInt(seatId.substring(1)) <= 0) {
        throw new InvalidDataException("행은 0이하의 숫자는 불가능합니다: " + seatId);
      }
    });
    List<Seat> seats = seatToRegister.getSeatIds().stream().map(seatId -> SeatMapper.seatRegisterDTOToSeat(seatId, seatToRegister.getPrice(), theater)).collect(Collectors.toList());
    seats.forEach(seat -> {
      if(seatRepository.existsBySeatIdAndTheater(seat.getSeatId(), theater)) {
        throw new DataExistsException("이미 존재하는 좌석입니다: " + seat.getSeatId(), "SEAT");
      }
    });
    seatRepository.saveAll(seats.stream().distinct().collect(Collectors.toList()));
  }

  @Transactional(readOnly = true)
  public TheaterDTO getTheater(Long theaterId) {
    Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 상영관입니다.", "Theater"));
    return TheaterMapper.theaterToTheaterDTO(theater);
  }

  @Transactional(readOnly = true)
  public List<TheaterDTO> getAllTheater() {
    return theaterRepository.findAll().stream().sorted(Comparator.comparing(Theater::getName)).map(theater -> TheaterMapper.theaterToTheaterDTO(theater)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<SeatDTO> getSeats(Long theaterId) {
    Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 상영관입니다.", "Theater"));

    List<Seat> seats = seatRepository.findAllByTheaterOrderByRowAscOrderByColumnAsc(theater);

    return seats.stream().map(seat -> SeatMapper.seatToSeatDTO(seat)).collect(Collectors.toList());
  }

  @Transactional
  public TheaterDTO modifyTheater(TheaterRegisterDTO registerDTO) {
    if(!theaterRepository.existsById(registerDTO.getTheaterId())) {
      throw new DataNotExistsException("존재하지 않는 상영관 ID 입니다.", "Theater");
    }

    Theater theater = theaterRepository.save(TheaterMapper.theaterRegisterDTOToTheater(registerDTO, codeRepository.findById(registerDTO.getType()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 상영관 코드 타입 입니다.", "THEATER"))));

    return TheaterMapper.theaterToTheaterDTO(theater);

  }

  @Transactional
  public void modifySeatList(Long theaterId, SeatRegisterDTO seatToModify) {
    Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 상영관 ID 입니다.", "Theater"));

    seatToModify.getSeatIds().forEach(seatId -> {
      if(seatId.charAt(0) < 'A' || seatId.charAt(0) > 'Z') {
        throw new InvalidDataException("열은 A~Z의 대문자이어야 합니다: " + seatId);
      }
      if(Integer.parseInt(seatId.substring(1)) <= 0) {
        throw new InvalidDataException("행은 0이하의 숫자는 불가능합니다: " + seatId);
      }
    });
    List<Seat> seats = seatToModify.getSeatIds().stream().map(seatId -> SeatMapper.seatRegisterDTOToSeat(seatId, seatToModify.getPrice(), theater)).collect(Collectors.toList());
    seats.forEach(seat -> {
      if(!seatRepository.existsBySeatIdAndTheater(seat.getSeatId(), theater)) {
        throw new DataNotExistsException("존재하지 않는 좌석입니다: " + seat.getSeatId(), "SEAT");
      }
    });
    seatRepository.updateSeatPriceByList(seatToModify.getSeatIds(), theater, seatToModify.getPrice());
  }

  @Transactional
  public void deleteTheater(Long theaterId) {
    theaterRepository.deleteById(theaterId);
  }

  @Transactional
  public void deleteSeatList(Long theaterId, SeatDeleteRegisterDTO seatIds) {
    Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 상영관 ID 입니다.", "Theater"));
    List<String> seatIdParsed = Arrays.stream(seatIds.getSeatsToDelete().split(",")).toList();
    List<SeatId> seats = seatIdParsed.stream().map(seat -> SeatId.builder().theater(theater).seatId(seat).build()).collect(Collectors.toList());

    seatRepository.deleteAllById(seats);
  }

  @Transactional(readOnly = true)
  public List<Code> loadTheaterTypeList() {
    return codeRepository.findAllByUpperCode(codeRepository.findTheaterTypeUpperCode());
  }

  @Transactional
  public List<Code> addTheaterType(TheaterTypeDTO theaterTypeDTO) {
    if(codeRepository.existsByName(theaterTypeDTO.getName())) {
      throw new DataExistsException("존재하는 공연장 타입 이름 입니다.", "THEATER CODE");
    }
    if(codeRepository.existsById(theaterTypeDTO.getCode())) {
      throw new DataExistsException("존재하는 공연장 타입 코드입니다.", "THEATER CODE");
    }

    Code upperCode = codeRepository.findTheaterTypeUpperCode();
    if(!theaterTypeDTO.getCode().substring(0, 3).equals(upperCode.getCode())) {
      throw new InvalidDataException("잘못된 코드 형식입니다. 공연장 타입은 TH0 형식으로 이루어져야 합니다.");
    }

    codeRepository.save(TheaterMapper.theaterTypeDTOToCode(theaterTypeDTO, upperCode));

    return loadTheaterTypeList();
  }

  @Transactional
  public List<Code> updateTheaterType(TheaterTypeDTO theaterTypeDTO) {
    if(codeRepository.existsByName(theaterTypeDTO.getName())) {
      throw new DataExistsException("존재하는 공연장 타입 이름 입니다.", "THEATER CODE");
    }

    if(!codeRepository.existsById(theaterTypeDTO.getCode())) {
      throw new DataExistsException("존재하지 않는 공연장 타입 코드입니다.", "THEATER CODE");
    }

    Code upperCode = codeRepository.findTheaterTypeUpperCode();
    if(!theaterTypeDTO.getCode().substring(0, 3).equals(upperCode.getCode())) {
      throw new InvalidDataException("잘못된 코드 형식입니다. 공연장 타입은 TH0 형식으로 이루어져야 합니다.");
    }

    codeRepository.save(TheaterMapper.theaterTypeDTOToCode(theaterTypeDTO, upperCode));

    return loadTheaterTypeList();
  }

  @Transactional
  public void deleteTheaterType(String code) {
    if(theaterRepository.existsByType(codeRepository.findById(code).orElseThrow(() -> new DataNotExistsException("존재하지 않는 공연장 타입 코드입니다.", "THEATER CODE")))) {
      throw new DataExistsException("해당 공연장 타입을 가진 공연장이 존재합니다.", "THEATER");
    }

    else {
      codeRepository.deleteById(code);
    }
  }
}
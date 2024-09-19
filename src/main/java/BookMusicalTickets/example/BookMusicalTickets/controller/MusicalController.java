package BookMusicalTickets.example.BookMusicalTickets.controller;

import BookMusicalTickets.example.BookMusicalTickets.dto.CodeDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.RatingDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.RoleAddDTO;
import io.swagger.v3.oas.annotations.Operation;
import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastInMusicalDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastInfoDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalRegisterDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalTitleDTO;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.InvalidAccessException;
import BookMusicalTickets.example.BookMusicalTickets.service.MusicalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
  @Log4j2
  @RequiredArgsConstructor
  @RequestMapping("/musical")
  @Tag(name = "뮤지컬 관련 로직", description = "뮤지컬 페이지 등록 시 뮤지컬/장르/등급/출연진/역할 추가 API")
  public class MusicalController {

    private final MusicalService musicalService;

    //영화관련
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MusicalDTO registerMusical(@ModelAttribute MusicalRegisterDTO musicalRegisterDTO) {
      log.info("musical register request: " + musicalRegisterDTO);
      return musicalService.register(musicalRegisterDTO);
    }

    @GetMapping("/detail")
    public MusicalDTO getMusicalDetail(@RequestParam("id") Long musicalId) {
      log.info("musical detail request: " + musicalId);
      return musicalService.getMusical(musicalId);
    }

    @Operation(description = "뮤지컬 간략 목록 조회(뮤지컬 이름, 사진, 등급")
    @GetMapping("/all")
    public List<MusicalTitleDTO> getAllMusicals() {
      log.info("musical list request");
      return musicalService.getShortMusicalList();
    }

    @PostMapping(value = "/modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MusicalDTO modifyMusical(@ModelAttribute MusicalRegisterDTO musicalRegisterDTO) {
      log.info("musical modify request: " + musicalRegisterDTO);
      return musicalService.modifyMusical(musicalRegisterDTO);
    }

    @DeleteMapping("/delete")
    public void deleteMusical(@RequestParam("id") Long musicalId) {
      log.info("musical delete request: " + musicalId);
      musicalService.deleteMusical(musicalId);
    }

    //장르
    @PostMapping(value = "/genre/add")
    public List<Code> addGenre(@Valid @RequestBody CodeDTO codeDTO) {
      log.info("genre add request: " + codeDTO);
      if(codeDTO.getCode() == null || codeDTO.getName() == null) {
        throw new InvalidAccessException("데이터가 비어있습니다.");
      }
      return musicalService.addGenre(codeDTO.getName(), codeDTO.getCode());
    }

    @GetMapping("/genre/list")
    public List<Code> getGenreList() {
      log.info("load genre list");
      return musicalService.loadGenreList();
    }

    @DeleteMapping("/genre/delete")
    public void deleteGenre(@RequestParam("id") String genreId) {
      log.info("delete genre: " + genreId);
      musicalService.deleteGenre(genreId);
    }

    @PostMapping(value = "/genre/modify")
    public void modifyGenre(@Valid @RequestBody CodeDTO codeDTO) {
      log.info("modify genre: " + codeDTO);
      if(codeDTO.getCode() == null || codeDTO.getName() == null) {
        throw new InvalidAccessException("데이터가 비어있습니다.");
      }
      musicalService.modifyGenre(codeDTO.getName(), codeDTO.getCode());
    }


    //등급
    @PostMapping(value = "/rating/add")
    public List<Code> registerRating(@Valid @RequestBody RatingDTO ratingDTO) {
      log.info("add rating request: " + ratingDTO);
      if(ratingDTO.getCode() == null || ratingDTO.getName() == null) {
        throw new InvalidAccessException("데이터가 비어있습니다.");
      }
      return musicalService.addRating(ratingDTO);
    }

    @PostMapping(value = "/rating/modify")
    public List<Code> modifyRating(@Valid @RequestBody RatingDTO ratingDTO) {
      log.info("rating modify request: " + ratingDTO);
      if(ratingDTO.getCode() == null || ratingDTO.getName() == null) {
        throw new InvalidAccessException("데이터가 비어있습니다.");
      }
      return musicalService.updateRating(ratingDTO);
    }

    @DeleteMapping("/rating/delete")
    public void deleteRating(@RequestParam("id") String ratingId) {
      log.info("rating delete request: " + ratingId);
      musicalService.deleteRating(ratingId);
    }

    @GetMapping("/rating/list")
    public List<Code> getRatingList() {
      log.info("rating list");
      return musicalService.getRatingList();
    }

    @PostMapping(value = "/cast/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<CastInMusicalDTO> registerCast(@ModelAttribute CastInfoDTO infoDTO) {
      log.info("cast register request: " + infoDTO);
      return musicalService.addCast(infoDTO);
    }

    @Operation(description = "cast id 보내주기: ex) /cast/detail?castId=1")
    @GetMapping("/cast/detail")
    public CastDTO getCastDetail(@RequestParam("castId") Long castId) {
      log.info("cast detail: " + castId);
      return musicalService.getCast(castId);
    }

    @PostMapping(value = "/cast/modify")
    public List<CastInMusicalDTO> modifyCast(@ModelAttribute CastInfoDTO infoDTO) {
      log.info("cast modify request: " + infoDTO);
      return musicalService.modifyCast(infoDTO);
    }

    @DeleteMapping(value = "/cast/delete")
    public void deleteCast(@RequestParam("castId") Long castId) {
      log.info("cast delete request: " + castId);
      musicalService.deleteCast(castId);
    }

    @GetMapping("/cast/getList")
    public List<CastInMusicalDTO> getDirectorShortInfoList() {
      log.info("cast list");
      return musicalService.getDirectorList();
    }


    //역할
    @PostMapping(value = "/{musicalId}/role/add")
    public void addRole(@PathVariable("musicalId") Long musicalId, @RequestBody RoleAddDTO rolesToAddDTO) {
      log.info("add role request: " + rolesToAddDTO);
      musicalService.addRole(musicalId, rolesToAddDTO);
    }

    @PostMapping(value = "/{musicalId}/role/modify")
    public void modifyRole(@PathVariable("musicalId") Long musicalId, @RequestBody RoleAddDTO roleAddDTO) {
      log.info("role modify request: " + roleAddDTO);
      musicalService.addRole(musicalId, roleAddDTO);
    }

    @DeleteMapping("/{musicalId}/role/delete")
    public void deleteRole(@PathVariable("musicalId") Long musicalId, @RequestParam("castId") Long castId) {
      log.info("role delete request: " + castId);
      musicalService.deleteRole(musicalId, castId);
    }
  }
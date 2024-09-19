package BookMusicalTickets.example.BookMusicalTickets.service;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import BookMusicalTickets.example.BookMusicalTickets.domain.Image;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Cast;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.GenreRegister;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.RoleId;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Role;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastInMusicalDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastInfoDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.ImageDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalRegisterDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalTitleDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.RatingDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.RoleAddDTO;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataExistsException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataNotExistsException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.InvalidAccessException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.InvalidDataException;
import BookMusicalTickets.example.BookMusicalTickets.mapper.MusicalMapper;
import BookMusicalTickets.example.BookMusicalTickets.repository.CastRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.CodeRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.GenreRegisterRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.ImageRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.MusicalRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.RoleRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.ScheduleRepository;
import io.lettuce.core.dynamic.annotation.Value;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.ServerException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
@RequiredArgsConstructor
public class MusicalService {

  private final FileService fileService;
  private final MusicalRepository musicalRepository;
  private final ImageRepository imageRepository;
  private final CastRepository castRepository;
  private final RoleRepository roleRepository;
  private final GenreRegisterRepository genreRegisterRepository;
  private final CodeRepository codeRepository;
  private final ScheduleRepository scheduleRepository;

  @Value("${spring.servlet.multipart.location}")
  private String uploadPath;

  @Transactional
  public MusicalDTO register(MusicalRegisterDTO registerDTO) {
    log.debug("musical register start");
    Cast director = castRepository.findById(registerDTO.getDirectorId()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 감독 ID입니다.", "cast"));
    Code ratingUpperCode = codeRepository.findRatingUpperCode();
    if(!ratingUpperCode.getCode().equals(registerDTO.getRatingCode().substring(0, 3))) {
      throw new InvalidDataException("잘못된 등급 타입입니다.");
    }
    Code rating = codeRepository.findById(registerDTO.getRatingCode()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 등급입니다.", "rating"));
    Image poster = createImage(registerDTO.getPoster(), "poster");

    Musical musical = MusicalMapper.musicalRegisterDTOToMusical(registerDTO, poster, director, rating);
    log.info(musical);
    Musical musicalSaved = musicalRepository.save(musical);

    if(registerDTO.getGenreCodes() != null) {
      registerDTO.getGenreCodes().stream().forEach(genreCode -> {
        if(!genreCode.substring(0, 3).equals(codeRepository.findGenreUpperCode().getCode())) {
          throw new InvalidAccessException("장르 코드가 아닙니다. 다시 확인해주세요.");
        }
        Code genre = codeRepository.findById(genreCode).orElseThrow(() -> new DataNotExistsException("존재하지 않는 장르 코드입니다.", "Genre"));
        genreRegisterRepository.save(GenreRegister.builder().musical(musicalSaved).genre(genre).build());
      });
    }

    return MusicalMapper.musicalToMusicalDTO(musical,
        genreRegisterRepository.findAllByMusical(musical)
            .stream()
            .map(genreRegister -> genreRegister.getGenre().getName())
            .collect(Collectors.toList())
        , roleRepository.findAllByMusical(musicalSaved).stream().sorted(Comparator.comparing(Role::isStarring).reversed().thenComparing(role -> role.getCast().getName())).collect(Collectors.toList())
    );
  }

  @Transactional
  public MusicalDTO modifyMusical(MusicalRegisterDTO registerDTO) {
    Musical musical = musicalRepository.findById(registerDTO.getMusicalId()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬입니다.", "MUSICAL"));

    log.debug("musical register start");
    Cast director = castRepository.findById(registerDTO.getDirectorId()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 감독 ID입니다.", "cast"));
    Code ratingUpperCode = codeRepository.findRatingUpperCode();
    if(!ratingUpperCode.getCode().equals(registerDTO.getRatingCode().substring(0, 3))) {
      throw new InvalidDataException("잘못된 등급 타입입니다.");
    }
    Code rating = codeRepository.findById(registerDTO.getRatingCode()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 등급입니다.", "rating"));
    Image poster = musical.getPoster();
    if(registerDTO.getPoster() != null) {
      createImage(registerDTO.getPoster(), "poster");
    }

    genreRegisterRepository.deleteAllByMusical(musical);

    musical = MusicalMapper.musicalRegisterDTOToMusical(registerDTO, poster, director, rating);


    Musical musicalSaved = musicalRepository.save(musical);

    if(registerDTO.getGenreCodes() != null) {
      registerDTO.getGenreCodes().stream().forEach(genreCode -> {
        if(!genreCode.substring(0, 3).equals(codeRepository.findGenreUpperCode().getCode())) {
          throw new InvalidAccessException("장르 코드가 아닙니다. 다시 확인해주세요.");
        }
        Code genre = codeRepository.findById(genreCode).orElseThrow(() -> new DataNotExistsException("존재하지 않는 장르 코드입니다.", "Genre"));
        genreRegisterRepository.save(GenreRegister.builder().musical(musicalSaved).genre(genre).build());
      });
    }

    return MusicalMapper.musicalToMusicalDTO(musical,
        genreRegisterRepository.findAllByMusical(musical)
            .stream()
            .map(genreRegister -> genreRegister.getGenre().getName())
            .collect(Collectors.toList())
        , roleRepository.findAllByMusical(musicalSaved).stream().sorted(Comparator.comparing(Role::isStarring).reversed().thenComparing(role -> role.getCast().getName())).collect(Collectors.toList())
    );

  }

  private Image createImage(MultipartFile file, String target) {
    String originalName = file.getOriginalFilename();
    Path root = Paths.get(uploadPath, target);

    try {
      ImageDTO imageDTO =  fileService.createImageDTO(originalName, root);
      Image poster = Image.builder()
          .uuid(imageDTO.getUuid())
          .fileName(imageDTO.getFileName())
          .fileUrl(imageDTO.getFileUrl())
          .build();

      file.transferTo(Paths.get(imageDTO.getFileUrl()));

      return imageRepository.save(poster);
    } catch (IOException e) {
      throw new ServerException("파일 저장 실패");
    }
  }

  @Transactional
  public List<Code> addRating(RatingDTO ratingDTO) {

    if(codeRepository.existsByName(ratingDTO.getName())) {
      throw new DataExistsException("존재하는 rating 이름입니다.", "rating");
    }
    if(codeRepository.existsById(ratingDTO.getCode())) {
      throw new DataExistsException("존재하는 rating ID입니다.", "rating");
    }

    Code upperCode = codeRepository.findRatingUpperCode();
    if(!ratingDTO.getCode().substring(0, 3).equals(upperCode.getCode())) {
      throw new InvalidDataException("잘못된 코드 형식입니다. 장르는 GR0 형식으로 이루어져야 합니다.");
    }

    codeRepository.save(MusicalMapper.ratingDTOToCode(ratingDTO, upperCode));

    return getRatingList();
  }

  @Transactional
  public List<Code> updateRating(RatingDTO ratingDTO) {

    if(codeRepository.existsByName(ratingDTO.getName())) {
      throw new DataExistsException("존재하는 rating 이름입니다.", "rating");
    }

    if(!codeRepository.existsById(ratingDTO.getCode())) {
      throw new DataNotExistsException("존재하지 않는 rating ID입니다.", "rating");
    }

    Code upperCode = codeRepository.findRatingUpperCode();
    if(!ratingDTO.getCode().substring(0, 3).equals(upperCode.getCode())) {
      throw new InvalidDataException("잘못된 코드 형식입니다. 장르는 GR0 형식으로 이루어져야 합니다.");
    }

    codeRepository.save(MusicalMapper.ratingDTOToCode(ratingDTO, upperCode));

    return getRatingList();
  }

  @Transactional(readOnly = true)
  public List<Code> getRatingList() {
    return codeRepository.findAllByUpperCode(codeRepository.findRatingUpperCode()).stream().sorted(Comparator.comparing(Code::getCode)).collect(Collectors.toList());
  }

  @Transactional
  public List<CastInMusicalDTO> addCast(CastInfoDTO infoDTO) {
    Image profileImage = createImage(infoDTO.getProfileImage(), "cast");

    castRepository.save(MusicalMapper.castInfoDTOWithImageToCast(infoDTO, profileImage));
    return getDirectorList();
  }

  @Transactional
  public List<CastInMusicalDTO> modifyCast(CastInfoDTO infoDTO) {
    if(infoDTO.getProfileImage() == null) {
      castRepository.updateWithoutImage(MusicalMapper.castInfoDTOToCast(infoDTO));
      return getDirectorList();
    }

    Image profileImage = createImage(infoDTO.getProfileImage(), "cast");

    Cast cast = MusicalMapper.castInfoDTOWithImageToCast(infoDTO, profileImage);
    castRepository.save(cast);
    return getDirectorList();
  }

  @Transactional(readOnly = true)
  public List<CastInMusicalDTO> getDirectorList() {
    List<Cast> directorList = castRepository.findAll();
    return directorList.stream().sorted(Comparator.comparing(Cast::getName)).map(cast -> MusicalMapper.castToCastInMusicalDTO(cast)).collect(Collectors.toList());
  }

  @Transactional
  public void addRole(Long musicalId, RoleAddDTO roleAddDTO) {
    Musical musical = musicalRepository.findById(musicalId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬 ID 입니다.", "musical"));
    Cast cast = castRepository.findById(roleAddDTO.getCastId()).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬배우 ID 입니다.", "cast"));

    RoleId roleId = RoleId.builder().musical(musical).cast(cast).build();
    if(roleRepository.existsById(roleId)) {
      roleRepository.updateRoleById(roleId, roleAddDTO.getRole(), roleAddDTO.isStarring());
      return;
    }

    Role role = Role.builder()
        .musical(musical)
        .cast(cast)
        .role(roleAddDTO.getRole())
        .starring(roleAddDTO.isStarring())
        .build();

    roleRepository.save(role);
  }

  @Transactional
  public void modifyGenre(String name, String code) {
    if(codeRepository.existsByName(name)) {
      throw new DataExistsException("이미 저장된 장르 입니다.", "genre");
    }

    if(!codeRepository.existsById(code)) {
      throw new DataNotExistsException("존재하지 않는 장르 ID 입니다.", "genre");
    }

    Code upperCode = codeRepository.findGenreUpperCode();

    if(!code.substring(0, 3).equals(upperCode.getCode())) {
      throw new InvalidDataException("잘못된 코드 형식입니다. 장르는 GR0 형식으로 이루어져야 합니다.");
    }

    Code genre = Code.builder()
        .code(code)
        .name(name)
        .upperCode(upperCode)
        .build();

    codeRepository.save(genre);
  }

  @Transactional
  public void deleteGenre(String codeId) {
    codeRepository.deleteById(codeId);
  }

  @Transactional
  public List<Code> addGenre(String name, String code) {
    if(codeRepository.existsByName(name)) {
      throw new DataExistsException("이미 저장된 장르 입니다.", "genre");
    }

    if(codeRepository.existsById(code)) {
      throw new DataExistsException("이미 사용중인 장르 ID 입니다.", "genre");
    }

    Code upperCode = codeRepository.findGenreUpperCode();

    if(!code.substring(0, 3).equals(upperCode.getCode())) {
      throw new InvalidDataException("잘못된 코드 형식입니다. 장르는 GR0 형식으로 이루어져야 합니다.");
    }
    Code genre = Code.builder()
        .code(code)
        .name(name)
        .upperCode(upperCode)
        .build();

    codeRepository.save(genre);

    return loadGenreList();
  }

  @Transactional(readOnly = true)
  public List<Code> loadGenreList() {
    return codeRepository.findAllByUpperCode(codeRepository.findGenreUpperCode()).stream().sorted(Comparator.comparing(Code::getCode)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public MusicalDTO getMusical(Long musicalId) {
    Musical musical = musicalRepository.findById(musicalId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬 ID 입니다.", "musical"));

    return MusicalMapper.musicalToMusicalDTO(musical,
        genreRegisterRepository.findAllByMusical(musical).stream().map(genreRegister -> genreRegister.getGenre().getName()).collect(Collectors.toList())
        , roleRepository.findAllByMusical(musical).stream().sorted(Comparator.comparing(Role::isStarring).reversed().thenComparing(role -> role.getCast().getName())).collect(Collectors.toList()));
  }

  @Transactional
  public void deleteCast(Long castId) {
    Cast cast = castRepository.findById(castId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬배우 ID입니다", "CAST"));
    castRepository.deleteById(castId);
    imageRepository.deleteById(cast.getProfileImage().getImageId());
  }

  @Transactional
  public void deleteRole(Long musicalId, Long castId) {
    RoleId roleId = RoleId.builder()
        .cast(castRepository.findById(castId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬배우 ID 입니다.", "cast")))
        .musical(musicalRepository.findById(musicalId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬 ID 입니다.", "musical")))
        .build();
    roleRepository.deleteById(roleId);
  }

  @Transactional
  public void deleteRating(String ratingCode) {
    if(musicalRepository.existsByRating(codeRepository.findById(ratingCode).orElseThrow(() -> new DataNotExistsException("존재하지 않는 등급 ID입니다.", "Rating")))) {
      throw new DataExistsException("해당 등급을 가진 뮤지컬이 존재합니다.", "RATING");
    }

    else {
      codeRepository.deleteById(ratingCode);
    }
  }

  @Transactional(readOnly = true)
  public CastDTO getCast(Long castId) {
    return castRepository.findById(castId).map(cast -> MusicalMapper.castToCastDTO(cast)).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬배우 ID입니다.", "CAST"));
  }

  @Transactional(readOnly = true)
  public List<MusicalTitleDTO> getShortMusicalList() {
    return musicalRepository.findAll().stream().sorted(
        Comparator.comparing(Musical::getReleaseDate).reversed().thenComparing(Musical::getTitle)).distinct().map(musical -> MusicalMapper.musicalToMusicalTitleDTO(musical)).collect(Collectors.toList());
  }

  @Transactional
  public void deleteMusical(Long musicalId) {
    if(scheduleRepository.existsByMusicalId(musicalId)) {
      throw new DataExistsException("이미 등록된 공연 일정이 존재하여 뮤지컬 삭제가 불가합니다. 공연 일정을 삭제 후 다시 진행해주세요.", "Musical");
    }

    log.info(musicalId);

    Musical musical = musicalRepository.findById(musicalId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 뮤지컬입니다.", "Musical"));

    genreRegisterRepository.deleteAllByMusical(musical);

    musicalRepository.deleteById(musicalId);

    imageRepository.deleteById(musical.getPoster().getImageId());
  }

}

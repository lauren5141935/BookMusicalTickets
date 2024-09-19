package BookMusicalTickets.example.BookMusicalTickets.mapper;

import BookMusicalTickets.example.BookMusicalTickets.domain.Code;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Cast;
import BookMusicalTickets.example.BookMusicalTickets.domain.musical.Musical;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Role;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastInMusicalDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.CastInfoDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.GenreDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.ImageDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalRegisterDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalTitleDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.MusicalTitleWithPosterRatingDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.RatingDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.musical.RoleInMusicalDTO;
import java.awt.Image;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MusicalMapper {

  private MusicalMapper() {}

  public static Musical musicalRegisterDTOToMusical(MusicalRegisterDTO musicalRegisterDTO, Image poster, Cast cast, Code rating) {
    return Musical.builder()
        .title(musicalRegisterDTO.getTitle())
        .releaseDate(Date.valueOf(musicalRegisterDTO.getReleaseDate()))
        .runningTime(musicalRegisterDTO.getRunningTime())
        .info(musicalRegisterDTO.getInfo())
        .poster(poster)
        .director(cast)
        .rating(rating)
        .build();
  }

  public static MusicalDTO musicalToMusicalDTO(Musical musical, List<String> genreList, List<Role> roleList) {
    return MusicalDTO.builder()
        .musicalId(musical.getMusicalId())
        .title(musical.getTitle())
        .releaseDate(musical.getReleaseDate())
        .runningTime(musical.getRunningTime())
        .info(musical.getInfo())
        .poster(imageToImageDTO(musical.getPoster()))
        .director(castToCastInMusicalDTO(musical.getDirector()))
        .rating(musical.getRating().getName())
        .genreList(genreList)
        .roleList(roleList.stream().map(role -> roleToRoleInMusicalDTO(role)).collect(Collectors.toList()))
        .build();
  }

  private static RoleInMusicalDTO roleToRoleInMusicalDTO(Role role) {
    return RoleInMusicalDTO.builder()
        .role(role.getRole())
        .castId(role.getCast().getCastId())
        .name(role.getCast().getName())
        .starring(role.isStarring())
        .profileImage(role.getCast().getProfileImage().getFileName())
        .build();
  }

  public static Code ratingDTOToCode(RatingDTO ratingDTO, Code upperCode) {
    return Code.builder()
        .code(ratingDTO.getCode())
        .name(ratingDTO.getName())
        .upperCode(upperCode)
        .build();
  }

  public static Cast castInfoDTOWithImageToCast(CastInfoDTO castInfoDTO, Image profileImage) {
    return Cast.builder()
        .castId(castInfoDTO.getCastId())
        .name(castInfoDTO.getName())
        .birthDate(Date.valueOf(castInfoDTO.getBirthDate()))
        .info(castInfoDTO.getInfo())
        .profileImage(profileImage)
        .build();
  }

  public static Cast castInfoDTOToCast(CastInfoDTO castInfoDTO) {
    return Cast.builder()
        .castId(castInfoDTO.getCastId())
        .name(castInfoDTO.getName())
        .birthDate(Date.valueOf(castInfoDTO.getBirthDate()))
        .info(castInfoDTO.getInfo())
        .build();
  }

  public static CastInMusicalDTO castToCastInMusicalDTO(Cast cast) {
    return CastInMusicalDTO.builder()
        .castId(cast.getCastId())
        .name(cast.getName())
        .birthDate(cast.getBirthDate())
        .fileName(cast.getProfileImage().getFileName())
        .build();
  }

  public static Code genreDTOToCode(GenreDTO genreDTO, Code upperCode) {
    return Code.builder()
        .code(genreDTO.getGenreId())
        .name(genreDTO.getName())
        .upperCode(upperCode)
        .build();
  }

  public static MusicalTitleWithPosterRatingDTO musicalToMusicalTitleWithPosterRatingDTO(Musical musical) {

    return MusicalTitleWithPosterRatingDTO.builder()
        .title(musical.getTitle())
        .musicalId(musical.getMusicalId())
        .rating(musical.getRating().getName())
        .fileName(musical.getPoster().getFileName())
        .build();
  }

  public static MusicalTitleDTO musicalToMusicalTitleDTO(Musical musical) {
    return MusicalTitleDTO.builder()
        .title(musical.getTitle())
        .musicalId(musical.getMusicalId())
        .directorName(musical.getDirector().getName())
        .releaseDate(musical.getReleaseDate())
        .build();
  }

  public static CastDTO castToCastDTO(Cast cast) {
    return CastDTO.builder()
        .castId(cast.getCastId())
        .birthDate(cast.getBirthDate())
        .name(cast.getName())
        .info(cast.getInfo())
        .profileImage(imageToImageDTO(cast.getProfileImage()))
        .build();
  }

  private static ImageDTO imageToImageDTO(Image image) {
    return ImageDTO.builder()
        .imageId(image.getImageId())
        .uuid(image.getUuid())
        .fileName(image.getFileName())
        .fileUrl(image.getFileUrl())
        .build();
  }
}
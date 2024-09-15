package BookMusicalTickets.example.BookMusicalTickets.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RegisterUserDto {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Request {

    private String email;
    private String password;
    private String username;
    private LocalDate birthdate;
    private String phoneNo;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Response {

    private String email;
    private String username;

    public static Response from(UserDto userDto) {
      return Response.builder()
          .email(userDto.getEmail())
          .username(userDto.getUsername())
          .build();
    }
  }
}

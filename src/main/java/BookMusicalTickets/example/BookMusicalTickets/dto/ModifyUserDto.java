package BookMusicalTickets.example.BookMusicalTickets.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ModifyUserDto {
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Request {

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

    private String password;
    private String username;
    private LocalDate birthdate;
    private String phoneNo;

    public static Response from(UserDto userDto) {
      return Response.builder()
          .password(userDto.getPassword())
          .username(userDto.getUsername())
          .birthdate(userDto.getBirthdate())
          .phoneNo(userDto.getPhoneNo())
          .build();
    }
  }
}
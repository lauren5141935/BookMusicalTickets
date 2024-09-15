package BookMusicalTickets.example.BookMusicalTickets.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoginDto {
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Request {

    @Email
    private String email;
    private String password;
  }
}

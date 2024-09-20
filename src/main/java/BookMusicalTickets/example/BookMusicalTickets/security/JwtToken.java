package BookMusicalTickets.example.BookMusicalTickets.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

  private String accessToken;
  private String refreshToken;
  private Duration duration;

}


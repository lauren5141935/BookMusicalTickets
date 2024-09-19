package BookMusicalTickets.example.BookMusicalTickets.dto;

import BookMusicalTickets.example.BookMusicalTickets.constant.Role;
import BookMusicalTickets.example.BookMusicalTickets.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

  public class UserDto {
  @NotBlank(message = "아이디를 입력해주세요")
  private Long id;

  @NotBlank(message = "이메일을 입력해주세요")
  private String email;

  @NotBlank(message = "사용자 이름을 입력해주세요.")
  @Size(min=2, message = "사용자 이름이 너무 짧습니다.")
  private String username;

  @NotNull(message = "생일을 입력해주세요")
  @Range(min = 0, max = 150)
  private LocalDate birthdate;

  @NotNull(message = "전화번호를 입력해주세요")
  @Range(min = 0, max = 11)
  private String phoneNo;

  @NotBlank(message = "비밀번호를 입력해주세요")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
      message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
  private String password;

  private Role role;

  public static UserDto fromEntity(User user) {
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .username(user.getUsername())
        .password(user.getPassword())
        .birthdate(user.getBirthdate())
        .phoneNo(user.getPhoneNo())
        .role(user.getRole())
        .build();
  }
}
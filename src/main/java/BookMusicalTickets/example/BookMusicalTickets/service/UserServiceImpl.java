package BookMusicalTickets.example.BookMusicalTickets.service;


import static BookMusicalTickets.example.BookMusicalTickets.exception.ErrorCode.ALREADY_EXISTED_EMAIL;
import static BookMusicalTickets.example.BookMusicalTickets.exception.ErrorCode.PASSWORD_NOT_MATCHED;
import static BookMusicalTickets.example.BookMusicalTickets.exception.ErrorCode.USER_NOT_FOUND;

import BookMusicalTickets.example.BookMusicalTickets.dto.LoginDto;
import BookMusicalTickets.example.BookMusicalTickets.entity.User;
import BookMusicalTickets.example.BookMusicalTickets.exception.Exception;
import BookMusicalTickets.example.BookMusicalTickets.dto.RegisterUserDto;
import BookMusicalTickets.example.BookMusicalTickets.dto.ModifyUserDto;
import BookMusicalTickets.example.BookMusicalTickets.dto.UserDto;
import BookMusicalTickets.example.BookMusicalTickets.constant.Role;
import BookMusicalTickets.example.BookMusicalTickets.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public UserDto registerUser(RegisterUserDto.Request request, Role role) {
    if (this.userRepository.existsByEmail(request.getEmail())) {
      throw new Exception(ALREADY_EXISTED_EMAIL);
    }

    request.setPassword(this.passwordEncoder.encode(request.getPassword()));

    return UserDto.fromEntity(this.userRepository.save(User.builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .username(request.getUsername())
        .birthdate(request.getBirthdate())
        .phoneNo(request.getPhoneNo())
        .role(role)
        .build()));
  }

  public UserDto loginUser(LoginDto.Request request) {
    UserDto userDto = UserDto.fromEntity(checkEmail(request.getEmail()));

    if (!this.passwordEncoder.matches(request.getPassword(), userDto.getPassword())) {
      throw new Exception(PASSWORD_NOT_MATCHED);
    }

    return userDto;
  }

  @Override
  @Transactional
  public UserDto modifyUser(Long userId, ModifyUserDto.Request request) {
    User User = authenticationService.getAuthenticatedUser(userId);

    request.setPassword(this.passwordEncoder.encode(request.getPassword()));

    User.setPassword(request.getPassword());
    User.setUsername(request.getUsername());
    User.setBirthdate(request.getBirthdate());
    User.setPhoneNo(request.getPhoneNo());

    return UserDto.fromEntity(User);
  }

  @Override
  @Transactional
  public void deleteUser(Long userId) {
    this.userRepository.delete(
        authenticationService.getAuthenticatedUser(userId));
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new
        UsernameNotFoundException("USER not found with email: " + email));

    return createUserDetail(user.getEmail(), user.getPassword(), user.getRole());
  }

  private User checkEmail(String email) {
    return this.userRepository.findByEmail(email)
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));
  }

  private UserDetails createUserDetail(String email, String password, Role role) {
    return org.springframework.security.core.userdetails.User.withUsername(email)
        .password(this.passwordEncoder.encode(password))
        .roles(String.valueOf(role))
        .build();
  }
}
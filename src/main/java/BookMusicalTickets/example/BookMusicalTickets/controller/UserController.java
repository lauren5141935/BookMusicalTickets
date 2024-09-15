package BookMusicalTickets.example.BookMusicalTickets.controller;

import BookMusicalTickets.example.BookMusicalTickets.dto.LoginDto;
import BookMusicalTickets.example.BookMusicalTickets.security.TokenProvider;
import BookMusicalTickets.example.BookMusicalTickets.constant.Role;
import BookMusicalTickets.example.BookMusicalTickets.dto.RegisterUserDto;
import BookMusicalTickets.example.BookMusicalTickets.dto.ModifyUserDto;
import BookMusicalTickets.example.BookMusicalTickets.dto.UserDto;
import BookMusicalTickets.example.BookMusicalTickets.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final TokenProvider tokenProvider;

  @PostMapping("/users")
  public RegisterUserDto.Response registerUser(
      @RequestBody RegisterUserDto.Request request
  ) {
    return RegisterUserDto.Response.from(
        this.userService.registerUser(request, Role.USER));
  }

  @PostMapping("/admins")
  public RegisterUserDto.Response registerAdmin(
      @RequestBody RegisterUserDto.Request request
  ) {
    return RegisterUserDto.Response.from(
        this.userService.registerUser(request, Role.ADMIN));
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(
      @RequestBody @Valid LoginDto.Request request
  ) {
    UserDto user = this.userService.loginUser(request);
    return ResponseEntity.ok(
        this.tokenProvider.generateToken(
            user.getEmail(),
            user.getRole())
    );
  }

  @PutMapping("/users/{userId}")
  public ModifyUserDto.Response modifyUser(
      @PathVariable("userId") Long userId,
      @RequestBody @Valid ModifyUserDto.Request request
  ) {
    return ModifyUserDto.Response.from(
        this.userService.modifyUser(userId, request));
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<?> deleteUser(
      @PathVariable("userId") Long userId
  ) {
    this.userService.deleteUser(userId);
    return ResponseEntity.ok("사용자 정보가 삭제되었습니다.");
  }
}
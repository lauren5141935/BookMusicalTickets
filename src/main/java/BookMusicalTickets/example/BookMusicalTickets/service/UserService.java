package BookMusicalTickets.example.BookMusicalTickets.service;

import org.springframework.security.core.userdetails.UserDetails;
import BookMusicalTickets.example.BookMusicalTickets.dto.LoginDto;
import BookMusicalTickets.example.BookMusicalTickets.dto.RegisterUserDto;
import BookMusicalTickets.example.BookMusicalTickets.dto.ModifyUserDto;
import BookMusicalTickets.example.BookMusicalTickets.constant.Role;
import BookMusicalTickets.example.BookMusicalTickets.dto.UserDto;

public interface UserService {
  UserDetails loadUserByUsername(String email);

  UserDto registerUser(RegisterUserDto.Request request, Role role);

  UserDto loginUser(LoginDto.Request request);

  UserDto modifyUser(Long userId, ModifyUserDto.Request request);

  void deleteUser(Long userId);

}

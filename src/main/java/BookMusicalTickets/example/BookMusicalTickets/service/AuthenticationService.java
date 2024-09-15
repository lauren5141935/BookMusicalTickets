package BookMusicalTickets.example.BookMusicalTickets.service;

import static BookMusicalTickets.example.BookMusicalTickets.exception.ErrorCode.AUTHORIZATION_ERROR;
import static BookMusicalTickets.example.BookMusicalTickets.exception.ErrorCode.USER_NOT_FOUND;

import BookMusicalTickets.example.BookMusicalTickets.entity.User;
import BookMusicalTickets.example.BookMusicalTickets.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;

  public User getAuthenticatedUser(Long userId) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    User authenticatedUser = userRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new Exception(USER_NOT_FOUND));

    if (!authenticatedUser.getId().equals(userId)) {
      throw new Exception(AUTHORIZATION_ERROR);
    }

    return authenticatedUser;
  }
}

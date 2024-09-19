package BookMusicalTickets.example.BookMusicalTickets.service;


import BookMusicalTickets.example.BookMusicalTickets.domain.user.Admin;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Authority;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Customer;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Role;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.InvalidAccessException;
import BookMusicalTickets.example.BookMusicalTickets.repository.AuthorityRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthorityService implements UserDetailsService {

  private final AuthorityRepository authorityRepository;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    return authorityRepository.findByLoginId(loginId).map(this::createUserDetails).orElseThrow(() -> new InvalidAccessException("존재하지 않는 사용자입니다."));
  }
  private UserDetails createUserDetails(Authority authority) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
    Collection<GrantedAuthority> role = new ArrayList<>();
    role.add(grantedAuthority);

    if(authority.getAuthority().equals(Role.ROLE_USER.getType())) {
      Customer customer = authority.getCustomer();
      return new User(
          customer.getLoginId(),
          customer.getPassword(),
          Collections.singleton(grantedAuthority)
      );
    }
    else if(authority.getAuthority().equals(Role.ROLE_ADMIN.getType())) {
      Admin admin = authority.getAdmin();
      User user = new User(
          admin.getLoginId(),
          admin.getPassword(),
          role
      );
      return user;
    }

    throw new RuntimeException("권한 오류");
  }
}
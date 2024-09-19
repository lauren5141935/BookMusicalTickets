package BookMusicalTickets.example.BookMusicalTickets.service;

import BookMusicalTickets.example.BookMusicalTickets.domain.user.Admin;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Authority;
import BookMusicalTickets.example.BookMusicalTickets.domain.user.Role;
import BookMusicalTickets.example.BookMusicalTickets.dto.user.AdminDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.user.AdminDTOPassword;
import BookMusicalTickets.example.BookMusicalTickets.dto.user.LoginDTO;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataExistsException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.DataNotExistsException;
import BookMusicalTickets.example.BookMusicalTickets.exceptionHandler.InvalidAccessException;
import BookMusicalTickets.example.BookMusicalTickets.mapper.AdminMapper;
import BookMusicalTickets.example.BookMusicalTickets.repository.AdminRepository;
import BookMusicalTickets.example.BookMusicalTickets.repository.AuthorityRepository;
import BookMusicalTickets.example.BookMusicalTickets.security.JwtToken;
import BookMusicalTickets.example.BookMusicalTickets.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminService {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final AdminRepository adminRepository;
  private final JwtTokenProvider tokenProvider;
  private final AuthorityRepository authorityRepository;

  @Transactional
  public void signup(AdminDTO signupDTO) {
    if(adminRepository.existsByLoginId(signupDTO.getLoginId())) {
      throw new DataExistsException("중복된 아이디입니다.", "Admin");
    }

    Admin admin = Admin.builder()
        .name(signupDTO.getName())
        .loginId(signupDTO.getLoginId())
        .password(signupDTO.getPassword())
        .build();

    admin = adminRepository.save(admin);

    if(authorityRepository.existsByLoginId(admin.getLoginId())) {
      throw new DataExistsException("이미 사용중인 ID입니다.", "Admin");
    }
    Authority authority = Authority.builder()
        .loginId(admin.getLoginId())
        .password(admin.getPassword())
        .authority(Role.ROLE_ADMIN.getType())
        .admin(admin)
        .build();

    authorityRepository.save(authority);

  }

  @Transactional(readOnly = true)
  public JwtToken signIn(LoginDTO loginDTO) {
    UsernamePasswordAuthenticationToken authenticationToken = loginDTO.toAuthentication();
    authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    return tokenProvider.createToken(loginDTO.getLoginId(), Role.ROLE_ADMIN);
  }

  @Transactional
  public AdminDTO updateAdminData(AdminDTO modifyDTO, String loginId) {
    if(!modifyDTO.getLoginId().equals(loginId)) {
      throw new InvalidAccessException("관리자 ID가 변경시도 되었습니다. 다시 시도해주세요.");
    }

    Admin admin = adminRepository.findByLoginId(loginId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 회원입니다.", "User"));

    if(modifyDTO.getPassword() == null) {
      modifyDTO.setPassword(admin.getPassword());
    }

    admin = AdminMapper.adminInfoDTOToAdmin(modifyDTO);
    log.info(admin);
    adminRepository.modifyAdmin(admin, loginId);
    return adminRepository.findByLoginId(loginId).map(admin1 -> AdminMapper.adminToAdminInfoDTO(admin1)).orElseThrow(() -> new DataNotExistsException("존재하지 않는 회원입니다.", "ADMIN"));
  }

  @Transactional
  public void deleteAdmin(String loginId, String password, PasswordEncoder passwordEncoder) {
    Admin admin = adminRepository.findByLoginId(loginId).orElseThrow(() -> new DataNotExistsException("존재하지 않는 토큰입니다.", "Admin"));
    if(passwordEncoder.matches(password, admin.getPassword())) {
      adminRepository.deleteById(admin.getId());
      authorityRepository.deleteById(loginId);
    }
    else {
      throw new InvalidAccessException("잘못된 비밀번호입니다.");
    }
  }

  @Transactional(readOnly = true)
  public AdminDTOPassword getAdminDetail(String loginId) {
    return adminRepository.findByLoginId(loginId).map(admin -> AdminMapper.adminToAdminDTOPassword(admin)).orElseThrow(() -> new DataNotExistsException("잘못된 토큰으로 접근했습니다.", "ADMIN"));
  }
}
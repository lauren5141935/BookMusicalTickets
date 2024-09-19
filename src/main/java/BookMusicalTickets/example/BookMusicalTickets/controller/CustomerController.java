package BookMusicalTickets.example.BookMusicalTickets.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/customer")
@Tag(name = " 회원 데이터 처리 관련", description = "로그인, 회원가입...")
public class CustomerController {

  private final AdminService adminService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping(value = "/signup")
  public ResponseEntity<String> adminSignup(@Valid @RequestBody AdminInfoDTO signupDTO) {
    log.info("admin signup request: " + signupDTO);
    signupDTO.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
    adminService.signup(signupDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping(value = "/signin")
  public ResponseEntity<String> adminSignIn(@RequestBody LoginDTO loginDTO) {
    log.debug("admin signIn request: " + loginDTO);
    JwtToken token = adminService.signIn(loginDTO);

    ResponseCookie responseCookie = ResponseCookie
        .from("refresh_token", token.getRefreshToken())
        .httpOnly(true)
        .secure(true)
        .sameSite("None")
        .maxAge(token.getDuration())
        .path("/")
        .build();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Set-Cookie", responseCookie.toString());

    return new ResponseEntity<>(token.getAccessToken(), headers, HttpStatus.OK);
  }

  @GetMapping("/detail")
  public AdminInfoDTOExcludePassword getAdminInfo() {
    log.info("admin get info");
    String loginId = SecurityUtil.getCurrentUsername();
    return adminService.getAdminDetail(loginId);
  }

  @PostMapping(value = "/modify")
  public ResponseEntity<AdminInfoDTO> adminModify(@Valid @RequestBody AdminInfoDTO modifyDTO) {
    log.info("admin modify request : " + modifyDTO);
    if(!(modifyDTO.getPassword() == null || modifyDTO.getPassword() == "")) {
      modifyDTO.setPassword(passwordEncoder.encode(modifyDTO.getPassword()));
    }
    else {
      modifyDTO.setPassword(null);
    }
    log.info(modifyDTO);
    AdminInfoDTO dto = adminService.updateAdminData(modifyDTO, SecurityUtil.getCurrentUsername());

    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> adminDelete(@RequestBody String password) {
    log.info("admin delete request : " + password);
    String loginId = SecurityUtil.getCurrentUsername();

    adminService.deleteAdmin(loginId, password, passwordEncoder);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
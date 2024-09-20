package BookMusicalTickets.example.BookMusicalTickets.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 권한이 없는 사용자가 접근 시
 */
@Component
@Log4j2
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
    log.warn("access denied in handler");
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 인증 오류");
  }
}
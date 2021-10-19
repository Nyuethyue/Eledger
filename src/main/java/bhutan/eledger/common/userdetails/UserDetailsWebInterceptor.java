package bhutan.eledger.common.userdetails;

import bhutan.eledger.common.interceptor.Interceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
class UserDetailsWebInterceptor implements Interceptor {
    private static final String USERNAME_HEADER = "Requested-With-Username";
    private final UserDetailsHolder userDetailsHolder;

    @Override
    public boolean doesMatch(HttpServletRequest request) {

        return ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) && request.getRequestURI().endsWith("/config/gl/account");
    }

    @Override
    public boolean before(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String username = request.getHeader(USERNAME_HEADER);

        if (!StringUtils.hasText(username)) {
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            response.getWriter().write(USERNAME_HEADER + " header is required.");

            return false;
        }

        userDetailsHolder.set(new UserDetailsImpl(username));

        return true;
    }

    @Override
    public void afterView(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        userDetailsHolder.remove();
    }
}

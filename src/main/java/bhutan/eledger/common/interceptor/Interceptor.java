package bhutan.eledger.common.interceptor;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Interceptor extends Ordered {
    default boolean doesMatch(HttpServletRequest request) {
        return false;
    }

    default boolean before(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    default boolean after(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return true;
    }

    default void afterView(HttpServletRequest request, HttpServletResponse response, Exception ex) {

    }

    default int getOrder() {
        return 0;
    }
}

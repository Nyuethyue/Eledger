package bhutan.eledger.common.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("NullableProblems")
@Log4j2
@Component
public class InterceptorHandlerInterceptorAdapter implements HandlerInterceptor {

    private static final String ATTRIBUTE_MATCHED_INTERCEPTORS = InterceptorHandlerInterceptorAdapter.class.getName() + ".MATCHED_INTERCEPTORS";

    private final List<Interceptor> interceptors;

    public InterceptorHandlerInterceptorAdapter(List<Interceptor> interceptors) {
        this.interceptors = Collections.unmodifiableList(interceptors);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!interceptors.isEmpty()) {
            List<Interceptor> matchInterceptors = new ArrayList<>();

            for (Interceptor i : interceptors) {
                if (i.doesMatch(request)) {
                    matchInterceptors.add(i);
                    if (!i.before(request, response, handler)) {
                        request.setAttribute(ATTRIBUTE_MATCHED_INTERCEPTORS, Collections.unmodifiableList(matchInterceptors));
                        return false;
                    }
                }
            }

            request.setAttribute(ATTRIBUTE_MATCHED_INTERCEPTORS, matchInterceptors);

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        @SuppressWarnings("unchecked")
        List<Interceptor> matchedInterceptorsObject = (List<Interceptor>) request.getAttribute(ATTRIBUTE_MATCHED_INTERCEPTORS);

        if (!CollectionUtils.isEmpty(matchedInterceptorsObject)) {

            List<Interceptor> reversedInterceptors = matchedInterceptorsObject.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(), handlerBeanInterceptors -> {
                                Collections.reverse(handlerBeanInterceptors);
                                return Collections.unmodifiableList(handlerBeanInterceptors);
                            }

                    ));

            request.setAttribute(ATTRIBUTE_MATCHED_INTERCEPTORS, reversedInterceptors);
            for (Interceptor i : reversedInterceptors) {
                if (!i.after(request, response, modelAndView)) {
                    modelAndView.clear();
                    break;
                }
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        @SuppressWarnings("unchecked")
        List<Interceptor> matchedInterceptorsObject = (List<Interceptor>) request.getAttribute(ATTRIBUTE_MATCHED_INTERCEPTORS);

        if (!CollectionUtils.isEmpty(matchedInterceptorsObject)) {
            for (Interceptor i : matchedInterceptorsObject) {
                i.afterView(request, response, ex);
            }
        }
    }
}

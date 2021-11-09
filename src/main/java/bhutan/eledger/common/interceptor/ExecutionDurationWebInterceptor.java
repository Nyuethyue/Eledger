package bhutan.eledger.common.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Log4j2
public class ExecutionDurationWebInterceptor implements WebRequestInterceptor {

    private static final String PRE_HANDLE_START_TIME_MILLIS_ATTRIBUTE = ExecutionDurationWebInterceptor.class.getName() + ".PRE_HANDLE_START_TIME_MILLIS_ATTRIBUTE";
    private static final String POST_HANDLE_START_TIME_MILLIS_ATTRIBUTE = ExecutionDurationWebInterceptor.class.getName() + ".PRE_START_TIME_MILLIS";
    private static final String PRE_POST_HANDLERS_DIFF_TIME_MILLIS_ATTRIBUTE = ExecutionDurationWebInterceptor.class.getName() + ".PRE_POST_HANDLERS_DIFF_TIME_MILLIS";

    @Override
    public void preHandle(WebRequest request) {
        log.debug("ExecutionDurationWebInterceptor triggered for Endpoint: [{}]", () -> request.getDescription(false));

        request.setAttribute(PRE_HANDLE_START_TIME_MILLIS_ATTRIBUTE, System.currentTimeMillis(), SCOPE_REQUEST);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) {
        Long preStartTimeMillis = (Long) request.getAttribute(PRE_HANDLE_START_TIME_MILLIS_ATTRIBUTE, SCOPE_REQUEST);

        if (preStartTimeMillis != null) {
            long currentTimeMillis = System.currentTimeMillis();

            request.setAttribute(PRE_POST_HANDLERS_DIFF_TIME_MILLIS_ATTRIBUTE, currentTimeMillis - preStartTimeMillis, SCOPE_REQUEST);
            request.setAttribute(POST_HANDLE_START_TIME_MILLIS_ATTRIBUTE, currentTimeMillis, SCOPE_REQUEST);
        }
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) {
        Long preStartTimeMillis = (Long) request.getAttribute(PRE_HANDLE_START_TIME_MILLIS_ATTRIBUTE, SCOPE_REQUEST);
        Long prePostDiffMillis = (Long) request.getAttribute(PRE_POST_HANDLERS_DIFF_TIME_MILLIS_ATTRIBUTE, SCOPE_REQUEST);
        Long postStartTimeMillis = (Long) request.getAttribute(POST_HANDLE_START_TIME_MILLIS_ATTRIBUTE, SCOPE_REQUEST);

        long currentTimeMillis = System.currentTimeMillis();

        log.debug(
                "EndPoint: [{}]. Completion: {}, Execution: {}, Rendering: {} ",
                () -> request.getDescription(false),
                () -> humanTime(currentTimeMillis, preStartTimeMillis),
                () -> humanTime(prePostDiffMillis),
                () -> humanTime(currentTimeMillis, postStartTimeMillis)
        );
    }

    static String humanTime(long startTimeMillis, Long endTimeMillis) {
        return endTimeMillis == null ? null : humanTime(startTimeMillis - endTimeMillis);
    }

    static String humanTime(Long difference) {
        if (difference == null) {
            return null;
        }

        long seconds = MILLISECONDS.toSeconds(difference);
        return String.format("%d sec, %d millis", seconds, difference - SECONDS.toMillis(seconds));
    }
}

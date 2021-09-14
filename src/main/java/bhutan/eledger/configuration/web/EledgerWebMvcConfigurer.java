package bhutan.eledger.configuration.web;

import bhutan.eledger.common.interceptor.InterceptorHandlerInterceptorAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class EledgerWebMvcConfigurer implements WebMvcConfigurer {
    private final InterceptorHandlerInterceptorAdapter interceptorHandlerInterceptorAdapter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorHandlerInterceptorAdapter);
    }
}

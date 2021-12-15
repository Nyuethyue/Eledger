package bhutan.eledger.configuration.web;

import bhutan.eledger.common.interceptor.ExecutionDurationWebInterceptor;
import bhutan.eledger.common.interceptor.InterceptorHandlerInterceptorAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class EledgerWebMvcConfigurer implements WebMvcConfigurer {
    private final InterceptorHandlerInterceptorAdapter interceptorHandlerInterceptorAdapter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorHandlerInterceptorAdapter);
        registry.addWebRequestInterceptor(new ExecutionDurationWebInterceptor());
    }

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }
}

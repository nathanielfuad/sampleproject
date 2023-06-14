package com.obs.sampleproject.config.aspect;

import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect(new GsonBuilder().create());
    }

}

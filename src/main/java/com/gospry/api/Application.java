package com.gospry.api;

import com.gospry.api.domain.ErrorInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    // Used when deploying to a standalone servlet container
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class, AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorInfo unauthorized(HttpServletRequest req, AuthenticationException ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
}

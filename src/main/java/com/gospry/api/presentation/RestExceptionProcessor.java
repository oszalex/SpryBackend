package com.gospry.api.presentation;

import com.gospry.api.domain.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionProcessor {





    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo somethingSomewhereWentTerrribleWrong(HttpServletRequest req, RuntimeException ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo exception(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
}
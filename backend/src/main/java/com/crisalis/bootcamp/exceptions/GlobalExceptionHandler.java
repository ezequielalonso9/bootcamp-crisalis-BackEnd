package com.crisalis.bootcamp.exceptions;

import com.crisalis.bootcamp.exceptions.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<String> handleAuthenticationException(Exception ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("check username or password");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
            UserNotValidException.class,
            AccessDeniedException.class
    })
    @ResponseBody
    public ErrorMessage handleValidUserExceptions(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ValidationException.class,
            RolException.class,
            PrestacionException.class,
            PersonaException.class,
            EmpresaException.class,
            ClienteNotFoundException.class,
            ImpuestoException.class,
            PedidoException.class,
            LineaPedidoException.class
    })
    @ResponseBody
    public ErrorMessage handleValidationExceptions(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            PrestacionNotFoundException.class,
            ImpuestoNotFoundException.class,
            UsernameNotFoundException.class
    })
    @ResponseBody
    public ErrorMessage handleNotFoundExceptions(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception, request.getRequestURI());
    }

}

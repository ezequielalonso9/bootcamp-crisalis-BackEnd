package com.crisalis.bootcamp.exceptions;

import com.crisalis.bootcamp.exceptions.custom.ProductException;
import com.crisalis.bootcamp.exceptions.custom.RolException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ValidationException.class,
            RolException.class,
            ProductException.class
    })
    @ResponseBody
    public ErrorMessage handleValidationExceptions(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception, request.getRequestURI());
    }

}

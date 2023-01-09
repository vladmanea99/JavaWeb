package com.web.PetCare.exceptions;

import com.web.PetCare.exceptions.models.GeneralErrorResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.lang.model.type.ErrorType;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GeneralErrorResponse notFoundException(final Exception exception) {
        return new GeneralErrorResponse(exception.getMessage(), "NotFound");
    }

}

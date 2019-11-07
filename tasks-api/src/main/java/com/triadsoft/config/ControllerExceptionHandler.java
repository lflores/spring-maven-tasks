package com.triadsoft.config;

import com.triadsoft.exceptions.ErrorResponse;
import com.triadsoft.exceptions.FileStorageException;
import com.triadsoft.exceptions.NotFoundException;
import com.triadsoft.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;

import static org.springframework.http.HttpStatus.*;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 4/11/19 15:12
 */
@ControllerAdvice("com.triadsoft.api")
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleGenericExceptions(NotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return generateErrorResponse(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleGenericExceptions(ResourceNotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return generateErrorResponse(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleGenericExceptions(HttpMessageNotReadableException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return generateErrorResponse("Error in input message", BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleGenericExceptions(ValidationException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return generateErrorResponse(ex.getMessage(), UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<Object> handleGenericExceptions(FileStorageException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return generateErrorResponse("HTTP 500 Internal Error", INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericExceptions(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return generateErrorResponse("HTTP 500 Internal Error", INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> generateErrorResponse(String message, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(message);
        error.setStatus(httpStatus.value());
        error.setError(httpStatus.getReasonPhrase());
        return new ResponseEntity(error, httpStatus);
    }
}

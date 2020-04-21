package com.rest.exercise.web.handlers;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rest.exercise.model.Envelope;
import com.rest.exercise.web.CustomAuthenticationException;

/**
 * Catch exceptions thrown and generate accordingly the responses
 *
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * Code to handle exceptions and return an {@link ResponseEntity} per case
     *
     * @param request {@link HttpServletRequest} the http request
     * @param ex {@link CustomAuthenticationException} the custom exception
     * @return a {@link ResponseEntity} containing an {@link Envelope}
     */
    @ExceptionHandler(CustomAuthenticationException.class)
    @ResponseBody
    public ResponseEntity<Envelope> handleCustomAuthenticationException(HttpServletRequest request, CustomAuthenticationException ex) {

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        LOGGER.error("Exception thrown ", ex);

        Envelope response = new Envelope.EnvelopeBuilder()
                .withErrors(Arrays.asList("User is unauthorized to perform this operation"))
                .build();

        LOGGER.debug("Rest Service exceptional response: {} with status: {}", response, httpStatus);
        return new ResponseEntity(response, httpStatus);
    }

}

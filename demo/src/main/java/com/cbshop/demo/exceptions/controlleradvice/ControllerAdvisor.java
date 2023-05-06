package com.cbshop.demo.exceptions.controlleradvice;

import com.cbshop.demo.exceptions.InvalidDataException;
import com.cbshop.demo.exceptions.ItemNotFoundException;
import com.cbshop.demo.exceptions.ServerError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidDataException.class)
    protected ResponseEntity<?> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                Map.of("HTTP Status", HttpStatus.INTERNAL_SERVER_ERROR, "response body", Map.of("message", ex.getLocalizedMessage())),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = ItemNotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(ItemNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                Map.of("HTTP Status", HttpStatus.NOT_FOUND, "response body", Map.of("message", ex.getLocalizedMessage())),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = ServerError.class)
    protected ResponseEntity<?> handleServerExceptions(ServerError ex, WebRequest request) {
        return handleExceptionInternal(ex,
                Map.of("HTTP Status", HttpStatus.INTERNAL_SERVER_ERROR, "response body", Map.of("message", ex.getLocalizedMessage())),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<?> handleUserInvalidDataException(AccessDeniedException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                Map.of("HTTP Status", HttpStatus.FORBIDDEN, "response body", Map.of("message", ex.getLocalizedMessage())),
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<?> handleAuthenticationException(Exception ex) {
        return new ResponseEntity<>(Map.of("HTTP Status", HttpStatus.UNAUTHORIZED, "response body", Map.of("message", ex.getLocalizedMessage()))
                , HttpStatus.UNAUTHORIZED);
    }
}

package br.com.planejizze.exceptions.handler;

import br.com.planejizze.exceptions.EmailNotFoundException;
import br.com.planejizze.exceptions.auth.InvalidJwtAuthenticationException;
import br.com.planejizze.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler({NotFoundException.class, EmailNotFoundException.class})
    public ResponseEntity<StandardError> notFound(NotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
                System.currentTimeMillis(), "Não encontrado!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({InvalidJwtAuthenticationException.class})
    public ResponseEntity<StandardError> invalidAuth(NotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), e.getMessage(),
                System.currentTimeMillis(), "Não autorizado!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }
}

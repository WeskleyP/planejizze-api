package br.com.planejizze.exceptions.handler;

import br.com.planejizze.exceptions.ComprovanteException;
import br.com.planejizze.exceptions.EmailNotFoundException;
import br.com.planejizze.exceptions.FileStorageException;
import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.exceptions.auth.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler({NotFoundException.class, EmailNotFoundException.class})
    public ResponseEntity<StandardError> notFound(RuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
                System.currentTimeMillis(), "Não encontrado!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({ComprovanteException.class, DifferentPasswordException.class,
            EmailExistsException.class, EmailNotVerifiedException.class})
    public ResponseEntity<StandardError> badRequest(RuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                System.currentTimeMillis(), "Erro na requisição de dados!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler({InvalidJwtAuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<StandardError> invalidAuth(RuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), e.getMessage(),
                System.currentTimeMillis(), "Não autorizado!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler({Exception.class, FileStorageException.class})
    public ResponseEntity<StandardError> serverError(RuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                System.currentTimeMillis(), "Erro no servidor!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                System.currentTimeMillis(), "Erro de integridade de dados!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação!",
                System.currentTimeMillis(), "Data Integrity", request.getRequestURI());
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }
}

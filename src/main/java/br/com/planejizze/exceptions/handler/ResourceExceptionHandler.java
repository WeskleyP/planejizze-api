package br.com.planejizze.exceptions.handler;

import br.com.planejizze.exceptions.*;
import br.com.planejizze.exceptions.auth.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(1)
public class ResourceExceptionHandler {

    @ExceptionHandler({NotFoundException.class, EmailNotFoundException.class})
    public ResponseEntity<StandardError> notFound(RuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
                System.currentTimeMillis(), "Não encontrado!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({ComprovanteException.class, DifferentPasswordException.class, PlanejamentoInvalidDate.class,
            EmailExistsException.class, EmailNotVerifiedException.class, BadParamsException.class})
    public ResponseEntity<StandardError> badRequest(RuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                System.currentTimeMillis(), "Erro na requisição de dados!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler({InvalidJwtAuthenticationException.class, BadCredentialsException.class, ExpiredJwtException.class, MalformedJwtException.class, SignatureException.class})
    public ResponseEntity<StandardError> invalidAuth(RuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), e.getMessage(),
                System.currentTimeMillis(), "Não autorizado!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<StandardError> forbidden(HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), "Acesso Negado",
                System.currentTimeMillis(), "Sem permissão para acessar o recurso!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
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

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<StandardError> email(MessagingException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.REQUEST_TIMEOUT.value(), "Falha ao enviear o email",
                System.currentTimeMillis(), "Erro por falta de tempo!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(err);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<StandardError> jsonProcessing(JsonProcessingException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                System.currentTimeMillis(), "Erro ao converter valores para objetos!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}

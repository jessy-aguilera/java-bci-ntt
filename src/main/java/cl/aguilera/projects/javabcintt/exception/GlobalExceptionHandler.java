package cl.aguilera.projects.javabcintt.exception;

import cl.aguilera.projects.javabcintt.dto.ErrorDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.atomic.AtomicReference;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        AtomicReference<String> errores = new AtomicReference<>("");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errores.set(errores.get() + error.getDefaultMessage() + ". ");
        });
        errorDTO.setMensaje(errores.get());

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDTO> handleCustomException(UserException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMensaje(ex.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordEncryptionException.class)
    public ResponseEntity<ErrorDTO> handlePasswordEncryptionException(Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMensaje(ex.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMensaje("El correo ya está registrado");

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMensaje(ex.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
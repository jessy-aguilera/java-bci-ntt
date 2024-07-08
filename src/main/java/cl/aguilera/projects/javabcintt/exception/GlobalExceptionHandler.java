package cl.aguilera.projects.javabcintt.exception;

import cl.aguilera.projects.javabcintt.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorDTO.setMensaje(error.getDefaultMessage());
        });

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDTO> handleCustomException(UserException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMensaje(ex.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorDTO> handleMissingRequestHeaderException(Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMensaje("Debe enviar un token de usuario valido");

        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordEncryptionException.class)
    public ResponseEntity<ErrorDTO> handlePasswordEncryptionException(Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMensaje(ex.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMensaje(ex.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
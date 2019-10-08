package ua.com.smida.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.smida.exception.errormodel.ErrorEntity;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShareNotFoundException.class)
    public ResponseEntity<ErrorEntity> shareErrorHandler(ShareNotFoundException ex,
        WebRequest webRequest) {
        ErrorEntity details = ErrorEntity
            .builder()
            .localDateTime(LocalDateTime.now())
            .message(ex.getMessage())
            .details(webRequest.getDescription(false))
            .build();
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CompanyCodeNotFoundException.class)
    public ResponseEntity<ErrorEntity> codeCompanyErrorHandler(CompanyCodeNotFoundException ex,
        WebRequest webRequest) {
        ErrorEntity details = ErrorEntity
            .builder()
            .localDateTime(LocalDateTime.now())
            .message(ex.getMessage())
            .details(webRequest.getDescription(false))
            .build();
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

}

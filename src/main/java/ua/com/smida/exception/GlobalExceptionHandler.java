package ua.com.smida.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.smida.exception.errormodel.ErrorEntity;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShareNotFoundException.class)
    public ResponseEntity<ErrorEntity> shareErrorHandler(ShareNotFoundException ex,
        WebRequest webRequest) {
        ErrorEntity details = ErrorEntity
            .builder()
            .localDateTime(LocalDateTime.now())
            .messages(Collections.singletonMap("message: ",ex.getMessage()))
            .details(webRequest.getDescription(false))
            .build();
        log.info("GlobalExceptionHandler in shareErrorHandler {} ", details);
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CompanyCodeNotFoundException.class)
    public ResponseEntity<ErrorEntity> codeCompanyErrorHandler(CompanyCodeNotFoundException ex,
        WebRequest webRequest) {
        ErrorEntity details = ErrorEntity
            .builder()
            .localDateTime(LocalDateTime.now())
            .messages(Collections.singletonMap("message: ",ex.getMessage()))
            .details(webRequest.getDescription(false))
            .build();
        log.info("GlobalExceptionHandler in codeCompanyErrorHandler {} ", details);
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .forEach(fieldError -> {
                String errorField = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                errors.put(errorField, errorMessage);

            });

        ErrorEntity details = ErrorEntity
            .builder()
            .localDateTime(LocalDateTime.now())
            .messages(errors)
            .details(request.getDescription(false))
            .build();
        log.info("GlobalExceptionHandler in handleMethodArgumentNotValid {} ", details);
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);

    }


}

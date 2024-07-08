package com.ajeet.electronic.store.exceptions;

import com.ajeet.electronic.store.helpers.ApiResponse;
import com.ajeet.electronic.store.helpers.ImageApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException notFoundException) {
        ApiResponse apiResponse = ApiResponse.builder().message(notFoundException.getMessage()).isSuccess(true).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validationException(MethodArgumentNotValidException ex) {
        log.info("Validation Exception Method is invoke !!");
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();
        allErrors.forEach(error -> {
            String message = error.getDefaultMessage();
            String field = ((FieldError) error).getField();
            response.put(field, message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        ApiResponse apiResponse = ApiResponse.builder().message(ex.getMessage()).isSuccess(false).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ImageApiResponse> badResponse (BadApiRequest badApiRequest) {
        ImageApiResponse apiResponse = ImageApiResponse.builder().message(badApiRequest.getMessage()).isSuccess(false).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException methodNotSupportedException) {
        ApiResponse apiResponse = ApiResponse.builder().message(methodNotSupportedException.getMessage()).isSuccess(false).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}

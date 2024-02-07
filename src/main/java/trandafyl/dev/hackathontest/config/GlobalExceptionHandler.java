package trandafyl.dev.hackathontest.config;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.NoSuchElementException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(BidPlacingException.class)
    public ResponseEntity<String> handleException(BidPlacingException e) {
        Arrays.stream(e.getStackTrace()).forEach(log::fatal);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        Arrays.stream(e.getStackTrace()).forEach(log::fatal);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleException(ConstraintViolationException e) {
        Arrays.stream(e.getStackTrace()).forEach(log::fatal);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
package nl.codenomads.recipes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<String> handleIllegalArgumentException(RuntimeException ex, WebRequest request) {
        log.info("Error during session={}, description={}", request.getSessionId(), ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(RuntimeException ex, WebRequest request) {
        log.error("Error during session={}, description={}", request.getSessionId(), ex.getMessage(), ex);
        return ResponseEntity.internalServerError().build();
    }

}

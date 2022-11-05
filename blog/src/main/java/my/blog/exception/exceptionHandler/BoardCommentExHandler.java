package my.blog.exception.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(value = {"my.blog.board.controller", "my.blog.comments.controller"})
public class BoardCommentExHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> boardHandler(MethodArgumentNotValidException ex) {
        log.error("[Exception] ex", ex);

        Map<String, Object> errorResult = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(objectError -> errorResult.put(((FieldError) objectError).getField(), objectError.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

}

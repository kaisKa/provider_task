package spring.task.assess.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class HTTPCodeExceptionHandler {

    @ExceptionHandler(EmpNotFoundException.class)
    public ResponseEntity<Object> userNotFoundHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> argumentExceptionHandler(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(
                        e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" & ")));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( e.getMessage());
    }


    /**
     * fallback handler method
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<Object> handleExceptions(
            Exception e
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        e.printStackTrace();

        String response = e.getLocalizedMessage() ;
        return ResponseEntity.status(status).body(response);
    }
}

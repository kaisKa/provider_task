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




//    @ExceptionHandler(BadRequestException.class)
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<GeneralResponse<Map<Object, Object>>> BadRequestExceptionHandler(BadRequestException e) {
//        e.printStackTrace();
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status).body(new GeneralResponse<>(true, e.getMessage(), Map.of()));
//    }



//    @ExceptionHandler(NotValidLicenseException.class)
//    public ResponseEntity<GeneralResponse<Object>> notValidLicenseExceptionHandler(Exception e) {
//        return ResponseEntity.ok(new GeneralResponse<>(true, e.getMessage(), Map.of()));
//    }


//    @ExceptionHandler(UserAlreadyExistedException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ResponseEntity<GeneralResponse<Object>> handleNoUserExist(Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(new GeneralResponse<>(true, e.getMessage(), Map.of()));
//    }
//    @ExceptionHandler(ChangeSetPersister.NotFoundException.class) //NotFoundException
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<Object> NotFoundExceptionsHandler(
//            Exception e
//    ) {
//        HttpStatus status = HttpStatus.NOT_FOUND; // 404
//        // converting the stack trace to String
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(stringWriter);
//        e.printStackTrace(printWriter);
//        String stackTrace = stringWriter.toString();
//
//        var response = new GeneralResponse<>(true, e.getLocalizedMessage(), (Object) Map.of());
////        response.setStackTrace(stackTrace);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }

    //fallback handler method
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

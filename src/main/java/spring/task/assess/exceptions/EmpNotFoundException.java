package spring.task.assess.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmpNotFoundException extends RuntimeException{


    public EmpNotFoundException(String message) {
        super(message);
    }
}

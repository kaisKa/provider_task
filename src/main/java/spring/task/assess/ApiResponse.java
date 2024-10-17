package spring.task.assess;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse {

    private HttpStatus status;
    private String message;
    private Object data;
}

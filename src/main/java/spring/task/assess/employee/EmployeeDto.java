package spring.task.assess.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeDto {
    @JsonIgnore
    private Long id;
    @NotBlank
//    @Size(min = 6,  message = "Password length should be between 6 to 30 char ")
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private double salary;

    private Long dep_id;

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeeDto myClass = (EmployeeDto) obj;
        return id == myClass.id && Objects.equals(email, EmployeeDto.class);
    }
}

package spring.task.assess.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Invalid FistName : FirstName is NULL")
    private String firstName;
    @NotNull(message = "Invalid lastName: lastName is NULL")
    private String lastName;
    @NotNull(message = "Invalid email: email NULL")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
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

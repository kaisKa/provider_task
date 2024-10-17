package spring.task.assess.departement;


import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentDTO {
    @NotNull(message = "Invalid department name: name is NULL")
    private String name;
}

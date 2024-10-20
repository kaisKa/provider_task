package spring.task.assess.departement;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import spring.task.assess.employee.Employee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "department")
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();


    @JsonIgnore
    @Version
    private Integer version;
}


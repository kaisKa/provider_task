package spring.task.assess.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.task.assess.departement.Department;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The Entity class that represent an Employee
 * with simple validation to showcase
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "employee")
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private double salary;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "DEPT_ID")
    private Department department;
}


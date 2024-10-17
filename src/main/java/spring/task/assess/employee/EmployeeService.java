package spring.task.assess.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import spring.task.assess.departement.Department;
import spring.task.assess.departement.DepartmentRepository;
import spring.task.assess.departement.DepartmentService;
import spring.task.assess.exceptions.EmpNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;
    private final ObjectMapper objectMapper;

    public EmployeeService(EmployeeRepository repository, DepartmentService departmentService, DepartmentRepository departmentRepository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.objectMapper = objectMapper;
    }

    public List<Employee> getAll() {
        return this.repository.findAll();
    }

    public Page<Employee> getAllPaged(Pageable page) {
        return this.repository.findAll(page);
    }

    public Employee getById(Long id) {
        var op = this.repository.findById(id);
        if (op.isEmpty())
            throw new EmpNotFoundException("Employee Not found");
        else return op.get();
    }


    public EmployeeDto update(EmployeeDto emp) {
        Employee updateEmployee = objectMapper.convertValue(emp,Employee.class);
        Employee u = this.repository.save(updateEmployee);
        return objectMapper.convertValue(u,EmployeeDto.class);
    }

    public Boolean delete(Long id) {
        var opEmp = this.repository.findById(id);
        if(opEmp.isPresent()){
            this.repository.delete(opEmp.get());
            return true;
        }
        return false;

    }


    public EmployeeDto create(EmployeeDto emp) {

        Employee createEmployee = objectMapper.convertValue(emp, Employee.class);
        Optional<Department> dep = departmentRepository.findById(emp.getDep_id());
        if (dep.isPresent())
            createEmployee.setDepartment(dep.get());
        return  objectMapper.convertValue( this.repository.save(createEmployee) , EmployeeDto.class);
    }

    public Page<Employee> findBySearchCriteria
            (Specification<Employee> spec, Pageable page){
        Page<Employee> searchResult = repository.findAll(spec, page);
        return searchResult;
    }

    public List<Employee> saveAll(List<EmployeeDto> emps) {
        var employees = emps.stream().map(employeeDTO -> {
            Employee employee = objectMapper.convertValue(employeeDTO, Employee.class);

            // Set the department based on the departmentId in the DTO
            Department department = departmentRepository.findById(employeeDTO.getDep_id())
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            employee.setDepartment(department);

            return employee;
        }).collect(Collectors.toList());
        return this.repository.saveAll(employees);
    }
}

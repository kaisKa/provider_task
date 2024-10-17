package spring.task.assess.departement;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import spring.task.assess.employee.Employee;
import spring.task.assess.employee.EmployeeDto;
import spring.task.assess.employee.EmployeeRepository;
import spring.task.assess.exceptions.EmpNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService  {

    private final DepartmentRepository repository;

    private final ObjectMapper objectMapper;

    public DepartmentService(DepartmentRepository repository, DepartmentRepository departmentRepository, ObjectMapper objectMapper) {
        this.repository = repository;

        this.objectMapper = objectMapper;
    }

    public List<Department> getAll() {
        return this.repository.findAll();
    }

    public Page<Department> getAllPaged(Pageable page) {
        return this.repository.findAll(page);
    }

    public DepartmentDTO getById(Long id) {
        var op = this.repository.findById(id);
        if (op.isEmpty())
            throw new RuntimeException("Department Not found");
        else return objectMapper.convertValue(op.get(),DepartmentDTO.class);
    }


    public DepartmentDTO update(DepartmentDTO dep, Long id) {
        var opDep = this.repository.findById(id);
        if(opDep.isEmpty())
            throw new RuntimeException("Department "+ id+ " does not exist");
        Department department = opDep.get();
        department.setName(dep.getName()); //this could be handled ina better way cause like this null value will be set
        Department u = this.repository.save(department);
        return objectMapper.convertValue(u,DepartmentDTO.class);
    }

    public Boolean delete(Long id) {
        var opDep = this.repository.findById(id);
        if(opDep.isPresent()){
            this.repository.delete(opDep.get());
            return true;
        }
        return false;

    }


    public DepartmentDTO create(DepartmentDTO dep) {

        Department department = objectMapper.convertValue(dep, Department.class);

        return  objectMapper.convertValue( this.repository.save(department) , DepartmentDTO.class);
    }

    public Page<Department> findBySearchCriteria
            (Specification<Department> spec, Pageable page){
        Page<Department> searchResult = repository.findAll(spec, page);
        return searchResult;
    }


    public List<Department> saveAll(List<Department> deps) {
        return this.repository.saveAll(deps);
    }


}

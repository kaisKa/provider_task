package spring.task.assess.employee;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.task.assess.departement.DepartmentService;
import spring.task.assess.departement.Departments;

import java.io.IOException;
import java.io.InputStream;


/**
 * This is a loader class to load populate some data upon start up from a json file
 */
@Component
public class Employeesloader implements CommandLineRunner {
    private final ObjectMapper objectMapper;
    private final EmployeeService service;
    private final DepartmentService departmentService;

    public Employeesloader(ObjectMapper objectMapper, EmployeeService service, DepartmentService departmentService) {
        this.objectMapper = objectMapper;
        this.service = service;
        this.departmentService = departmentService;
    }


    @Override
    public void run(String... args) throws Exception {


        String empsPath = "/data/emps.json";
        String depsPath = "/data/deps.json";
        try(InputStream inputStream = TypeReference.class.getResourceAsStream(depsPath)){
            var response = objectMapper.readValue(inputStream, Departments.class);
            this.departmentService.saveAll(response.deps);
        }catch (IOException ex){
            ex.printStackTrace();
            throw  new RuntimeException("Failed to read json data");
        }


        try(InputStream inputStream = TypeReference.class.getResourceAsStream(empsPath)){
            var response = objectMapper.readValue(inputStream, Employees.class);
            this.service.saveAll(response.emps);
         }catch (IOException ex){
            ex.printStackTrace();
            throw  new RuntimeException("Failed to read json data");
        }
    }
}
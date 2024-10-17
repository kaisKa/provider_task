package spring.task.assess.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.task.assess.ApiResponse;
import spring.task.assess.model.SearchDto;
import spring.task.assess.model.SearchCriteria;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping()
    @Operation(description = "Endpoint to get all Employees ")
    public ResponseEntity<ApiResponse> listAll(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Pageable page = PageRequest.of(pageNum, pageSize);
        var emps = this.service.getAllPaged(page);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(emps.toList())
                .status(HttpStatus.OK)
                .message("Successfully retrieved employees record").build();

        return new ResponseEntity<>(apiResponse,
                apiResponse.getStatus());
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint to get a Employee by it id")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<Employee> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.service.getById(id));
    }

    @PostMapping()
    @Operation(description = "Endpoint to create a new Employee")
    public ResponseEntity<EmployeeDto> create(@RequestBody @Valid EmployeeDto emp) {
        return ResponseEntity.created(URI.create("/api/Employees")).body(this.service.create(emp));
    }


    @PutMapping()
    @Operation(description = "Endpoint to update existing Employee and create one in case does not exist")
    public ResponseEntity<EmployeeDto> update(@RequestBody EmployeeDto emp, @RequestParam Long id) {
        return ResponseEntity.ok().body(this.service.update(emp, id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint to delete a Employee from the db")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(ApiResponse.builder().status(HttpStatus.OK).message("Employee deleted - Employee ID:" + id).build(),HttpStatus.OK)
                :  new ResponseEntity<>(ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Employee " + id + " not found").build(),HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    @Operation(description = "search API that use JPA specification and criteria api 'configures for the firstName and department name' ")
    public ResponseEntity<ApiResponse> searchEmployees
            (@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
             @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
             @RequestParam String keyword){
        SearchDto sdto = SearchDto.builder().searchCriteriaList(
                List.of(
                        SearchCriteria.builder().filterKey("name").operation("cn").value(keyword).build(),
                        SearchCriteria.builder().filterKey("firstName").operation("cn").value(keyword).build()
//                        SearchCriteria.builder().filterKey("lastName").operation("cn").value(keyword).build()
                        )
        ).dataOption("").build();
        EmpSpecificationBuilder builder = new EmpSpecificationBuilder();
        List<SearchCriteria> criteriaList = sdto.getSearchCriteriaList();

        if(!criteriaList.isEmpty()){ criteriaList.forEach(x-> {
            x.setDataOption(sdto
                    .getDataOption());
                builder.with(x);
        });
        }

        Pageable page = PageRequest.of(pageNum, pageSize);

        Page<Employee> employeePage = service.findBySearchCriteria(builder.build(), page);
        ApiResponse apiResponse = ApiResponse.builder().data(employeePage.toList())
                .status(HttpStatus.OK)
                .message("Successfully retrieved employee record").build();

        return new ResponseEntity<>(apiResponse,
                apiResponse.getStatus());
    }
}

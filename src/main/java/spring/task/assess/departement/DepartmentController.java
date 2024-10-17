package spring.task.assess.departement;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.task.assess.ApiResponse;
import spring.task.assess.model.SearchDto;
import spring.task.assess.model.SearchCriteria;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping()
    @Operation(description = "Endpoint to get all departments ")
    public ResponseEntity<ApiResponse> listAll(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Pageable page = PageRequest.of(pageNum, pageSize);
        var deps = this.service.getAllPaged(page);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(deps.toList())
                .status(HttpStatus.OK)
                .message("Successfully retrieved departments record").build();

        return new ResponseEntity<>(apiResponse,
                apiResponse.getStatus());
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint to get a department by it id")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.service.getById(id));
    }

    @PostMapping()
    @Operation(description = "Endpoint to create a new department")
    public ResponseEntity<DepartmentDTO> create(@RequestBody @Valid DepartmentDTO dep) {
        return ResponseEntity.created(URI.create("/api/department")).body(this.service.create(dep));
    }


    @PutMapping()
    @Operation(description = "Endpoint to update existing department and create one in case does not exist")
    public ResponseEntity<DepartmentDTO> update(@RequestBody DepartmentDTO dep) {
        return ResponseEntity.ok().body(this.service.update(dep));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint to delete a department from the db")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(ApiResponse.builder().status(HttpStatus.OK).message("Department deleted - Department ID:" + id).build(),HttpStatus.OK)
                :  new ResponseEntity<>(ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Department " + id + " not found").build(),HttpStatus.NOT_FOUND);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse> searchDepartments
            (@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
             @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
             @RequestBody SearchDto searchDto){

        DepSpecificationBuilder builder = new DepSpecificationBuilder();
        List<SearchCriteria> criteriaList = searchDto.getSearchCriteriaList();

        if(criteriaList != null){
            criteriaList.forEach(x-> {
                x.setDataOption(searchDto.getDataOption());
                builder.with(x);
            });
        }

        Pageable page = PageRequest.of(pageNum, pageSize);

        Page<Department> depPag = service.findBySearchCriteria(builder.build(), page);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(depPag.toList())
                .status(HttpStatus.OK)
                .message("Successfully retrieved departments record").build();

        return new ResponseEntity<>(apiResponse,
                apiResponse.getStatus());
    }
}


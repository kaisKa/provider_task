package spring.task.assess.departement;

import org.springframework.data.jpa.domain.Specification;
import spring.task.assess.employee.Employee;
import spring.task.assess.employee.EmployeeSpecification;
import spring.task.assess.model.SearchCriteria;
import spring.task.assess.model.SearchOperation;

import java.util.ArrayList;
import java.util.List;

public class DepSpecificationBuilder {

    private final List<SearchCriteria> params;

    public DepSpecificationBuilder(){
        this.params = new ArrayList<>();
    }

    public final DepSpecificationBuilder with(String key,
                                              String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final DepSpecificationBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<Department> build(){
        if(params.size() == 0){
            return null;
        }

        Specification<Department> result =
                new DepartmentSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++){
            SearchCriteria criteria = params.get(idx);
            result =  SearchOperation.getDataOption(criteria
                    .getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new
                    DepartmentSpecification(criteria))
                    : Specification.where(result).or(
                    new DepartmentSpecification(criteria));
        }
        return result;
    }
}

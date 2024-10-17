package spring.task.assess.employee;

import org.springframework.data.jpa.domain.Specification;
import spring.task.assess.departement.Department;
import spring.task.assess.model.SearchCriteria;
import spring.task.assess.model.SearchOperation;

import javax.persistence.criteria.*;
import java.util.Objects;

public class EmployeeSpecification implements Specification<Employee> {

    private final SearchCriteria searchCriteria;

    public EmployeeSpecification(final SearchCriteria
                                         searchCriteria){
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root,
                                 CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue()
                .toString().toLowerCase();

        switch(Objects.requireNonNull(
                SearchOperation.getSimpleOperation
                        (searchCriteria.getOperation()))){
            case CONTAINS:
                if(searchCriteria.getFilterKey().equals("deptName"))
                {
                    return cb.like(cb.lower(departmentJoin(root).
                                    <String>get(searchCriteria.getFilterKey())),
                            "%" + strToSearch + "%");
                }
                return cb.like(cb.lower(root
                                .get(searchCriteria.getFilterKey())),
                        "%" + strToSearch + "%");

            case DOES_NOT_CONTAIN:
                if(searchCriteria.getFilterKey().equals("deptName"))
                {
                    return cb.notLike(cb.lower(departmentJoin(root).
                                    <String>get(searchCriteria.getFilterKey())),
                            "%" + strToSearch +"%");
                }
                return cb.notLike(cb.lower(root
                                .get(searchCriteria.getFilterKey())),
                        "%" + strToSearch + "%");

        }
        return null;
    }
    private Join<Employee, Department> departmentJoin(Root<Employee>
                                                             root){
        return root.join("department");
    }

}


package spring.task.assess.departement;

import org.springframework.data.jpa.domain.Specification;
import spring.task.assess.departement.Department;
import spring.task.assess.employee.Employee;
import spring.task.assess.model.SearchCriteria;
import spring.task.assess.model.SearchOperation;

import javax.persistence.criteria.*;
import java.util.Objects;

public class DepartmentSpecification implements Specification<Department> {

    private final SearchCriteria searchCriteria;

    public DepartmentSpecification(final SearchCriteria searchCriteria){
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue()
                .toString().toLowerCase();

        switch(Objects.requireNonNull(
                SearchOperation.getSimpleOperation
                        (searchCriteria.getOperation()))){
            case CONTAINS:

                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())),
                        "%" + strToSearch + "%");
            case DOES_NOT_CONTAIN:
                               return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())),
                        "%" + strToSearch + "%");

        }
        return null;
    }


}

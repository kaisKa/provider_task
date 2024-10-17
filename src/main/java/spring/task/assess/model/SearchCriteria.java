package spring.task.assess.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {

    private String filterKey;
    private Object value;
    private String operation;
    private String dataOption;

    public SearchCriteria(String filterKey, String operation,
                          Object value){
        super();
        this.filterKey = filterKey;
        this.operation = operation;
        this.value = value;
    }
}

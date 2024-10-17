package spring.task.assess.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {

    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;

}

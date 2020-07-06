package pattern.filter;

import java.util.List;
import java.util.stream.Collectors;

public class CriteriaYoung implements Criteria{
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        return persons.stream().filter(x-> x.getAge() <= 18).collect(Collectors.toList());
    }
}

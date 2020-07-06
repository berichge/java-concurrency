package pattern.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        Person Nami = new Person.PersonBuilder("Nami").assignAge(19).assignGender('w').build();
        Person Luffy = new Person.PersonBuilder("Luffy").assignAge(18).assignGender('m').build();
        Person Ace = new Person.PersonBuilder("Ace").assignAge(20).assignGender('m').build();
        Person Zoro = new Person.PersonBuilder("Zoro").assignAge(18).assignGender('m').build();
        Person Robin = new Person.PersonBuilder("Robin").assignAge(22).assignGender('w').build();

        List<Person> character = new ArrayList<>();
        Collections.addAll(character, Nami, Luffy, Ace, Zoro, Robin);

        CriteriaMale criteriaMale = new CriteriaMale();
        CriteriaYoung criteriaYoung = new CriteriaYoung();

        List<Person> young = criteriaMale.meetCriteria(character);
        List<Person> youngMale = criteriaYoung.meetCriteria(young);

        youngMale.forEach(x->{
            System.out.println(x.getName());
        });

    }
}

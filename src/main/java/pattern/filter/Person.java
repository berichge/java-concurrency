package pattern.filter;

public class Person {

    private String name;
    private char gender;
    private int age;

    public static class PersonBuilder {
        private String name;
        private char gender;
        private int age;

        public PersonBuilder(String name) {
            this.name = name;
        }

        public PersonBuilder assignGender(char g) {
            gender = g;
            return this;
        }

        public PersonBuilder assignAge(int age) {
            this.age = age;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.age = this.age;
            person.name = this.name;
            person.gender = this.gender;
            return person;
        }

    }

    private Person() {}// disable using construction

    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}

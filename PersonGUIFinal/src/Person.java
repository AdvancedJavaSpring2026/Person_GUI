import java.io.Serializable;

public class Person implements Serializable {

    private String firstName;
    private String lastName;
    private OCCCDate dateOfBirth;

    public Person(String firstName,
                  String lastName,
                  OCCCDate dateOfBirth) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public OCCCDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(OCCCDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

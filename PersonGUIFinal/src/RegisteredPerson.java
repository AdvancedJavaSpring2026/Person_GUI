public class RegisteredPerson extends Person {

    private String governmentID;

    public RegisteredPerson(String firstName,
                            String lastName,
                            OCCCDate dob,
                            String governmentID) {

        super(firstName, lastName, dob);

        this.governmentID = governmentID;
    }

    public String getGovernmentID() {
        return governmentID;
    }

    public void setGovernmentID(String governmentID) {
        this.governmentID = governmentID;
    }

    @Override
    public String toString() {
        return super.toString() +
                " (Registered)";
    }
}
public class OCCCPerson extends RegisteredPerson {

    private String studentID;

    public OCCCPerson(String firstName,
                      String lastName,
                      OCCCDate dob,
                      String governmentID,
                      String studentID) {

        super(firstName,
              lastName,
              dob,
              governmentID);

        this.studentID = studentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return super.toString() +
                " (OCCC)";
    }
}

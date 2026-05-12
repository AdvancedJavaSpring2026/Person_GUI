// Makayla Wood
// Advanced Java
// Spring 2026
// Subclass of RegisteredPerson meant to represent an OCCC student; now featuring Serializable

import java.io.Serializable;

public class OCCCPerson extends RegisteredPerson implements Serializable
{
    private String studentID;
    
    public OCCCPerson(RegisteredPerson p, String studentID)
    {
        super(p);
        this.studentID = new String(studentID);
    }
    
    public OCCCPerson(OCCCPerson p)
    {
        this((RegisteredPerson)p, p.getStudentID());
    }
    
    // Returns OCCCPerson's student ID
    public String getStudentID()
    {
        return studentID;
    }
    
    // Returns true if the first name, last name, government ID, and the student
    // ID of the current and provided instances of OCCCPerson match
    public boolean equals(OCCCPerson p)
    {
        return super.equals((RegisteredPerson)p) &&
                p.getStudentID().equals(this.studentID);
    }
    
    // Returns true if the first name, last name, and government ID of the
    // current instance of OCCCPerson and the provided instance of RegisteredPerson
    // match
    public boolean equals(RegisteredPerson p)
    {
        return super.equals(p);
    }
    
    // Returns true if the first name and last name of the current instance of
    // OCCCPerson and the provided instance of Person match
    public boolean equals(Person p)
    {
        return super.equals(p);
    }
    
    // Overrides toString to display the Person's name, government ID, and
    // student ID
    @Override
    public String toString()
    {
        return super.toString() + " {" + this.studentID + "}";
    }
}

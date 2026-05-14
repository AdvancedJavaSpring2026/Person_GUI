<<<<<<< HEAD
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
=======
// Makayla Wood
// Advanced Java
// Spring 2026
// Subclass of person meant to represent a Person with a government ID; now featuring Serializable

import java.io.Serializable;

public class RegisteredPerson extends Person implements Serializable
{
    private String govID;
    
    // Initializes RegisteredPerson with provided string values for first name, 
    // last name, and government ID
    public RegisteredPerson(String firstName, String lastName, OCCCDate dob, String govID) // ADD OCCCDate DOB HERE
    {
        super(firstName, lastName, dob);
        this.govID = new String(govID);
    }
    
    // Initializes RegisteredPerson with a provided Person instance and a 
    // government ID
    public RegisteredPerson(Person p, String govID)
    {
        super(p);
        this.govID = new String(govID);
    }
    
    // Initializes RegisteredPerson with the same name and government ID as
    // the provided RegisteredPerson instance
    public RegisteredPerson(RegisteredPerson p)
    {
        super(new Person(p.getFirstName(), p.getLastName(), p.getDOB()));
        this.govID = p.getGovernmentID();
    }
    
    // Returns RegisteredPerson's government ID
    public String getGovernmentID()
    {
        return govID;
    }
    
    // Returns true if first name, last name, and government ID are the same
    // between RegisteredPerson the current and provided Registered Person
    // instances
    public boolean equals(RegisteredPerson p)
    {
        return super.equals(p) &&
                p.getGovernmentID().equals(this.govID);
    }
    
    // Returns true if first name and last name are the same between the
    // current RegisteredPerson and provided Person instances
    public boolean equals(Person p)
    {
        return super.equals(p);
    }
    
    // Overrides toString to display the Person's name and government ID
    @Override
    public String toString()
    {
        return super.toString() + " [" + this.govID + "]";
    }
}
>>>>>>> 422380744cf5622e6b9032bd618835605c13dc76

// Makayla Wood
// Advanced Java
// Spring 2026
// Class to represent a person

import java.io.Serializable;

public class Person implements Serializable, Comparable<Person>
{
    private String firstName;
    private String lastName;
    private OCCCDate dob;
    
    // Initializes Person with a provided first name, last name, and date of birth
    public Person(String firstName, String lastName, OCCCDate dob)
    {
        this.firstName = new String(firstName);
        this.lastName = new String(lastName);
        this.dob = new OCCCDate(dob);
        this.dob.setDayName(OCCCDate.HIDE_DAY_NAME);
        this.dob.setStyleFormat(OCCCDate.STYLE_NUMBERS);
    }
    
    // INSERT CONSTRUCTOR WITH OCCCDATE HERE
    // public Person(String firstName, String lastName, OCCCDate dob)
    
    // Initializes person with the information from the provided Person object
    public Person(Person p)
    {
        this(p.getFirstName(), p.getLastName(), p.getDOB());
    }
    
    // Returns Person's first name
    public String getFirstName()
    {
        return firstName;
    }
    
    // Returns Person's last name
    public String getLastName()
    {
        return lastName;
    }
    
    // Sets first name for person as long as the provided firstName is not empty;
    // otherwise, no change is made
    public void setFirstName(String firstName)
    {
        if (!firstName.isEmpty())
            this.firstName = new String(firstName);
    }
    
    // Sets last name for person as long as the provided lastName is not empty;
    // otherwise, no change is made
    public void setLastName(String lastName)
    {
        if (!lastName.isEmpty())
            this.lastName = new String(lastName);
    }
    
    // Returns Person's date of birth
    public OCCCDate getDOB()
    {
        return this.dob;
    }
    
    // Overrides toString to display the Person's name
    @Override
    public String toString()
    {
        return lastName + ", " + firstName + " " + dob.toString();
    }
    
    // Compares Person objects by last name, then first name, and then date of birth
    @Override
    public int compareTo(Person p)
    {
        if (this.lastName.equals(p.getLastName()))
        {
            if (this.firstName.equals(p.getFirstName()))
            {
                if (this.dob.equals(p.getDOB()))
                    return 0;
                else
                    return this.dob.compareTo(p.getDOB());
            }
            else
                return this.firstName.compareTo(p.getFirstName());
        }
        else
            return this.lastName.compareTo(p.getLastName());
    }
    
    // Returns true if first name and last name are the same between the current
    // and provided Person instances
    public boolean equals(Person p)
    {
        return p.getFirstName().equalsIgnoreCase(this.firstName) &&
                p.getLastName().equalsIgnoreCase(this.lastName); // ADD DOB HERE
    }
    
    // Prints in the console to simulate Person eating
    public void eat()
    {
        System.out.println(this.firstName + " " + this.lastName + " " + " is eating...");
    }
    
    // Prints in the console to simulate Person sleeping
    public void sleep()
    {
        System.out.println(this.firstName + " " + this.lastName + " " + " is sleeping...");
    }
    
    // Prints in the console to simulate Person playing
    public void play()
    {
        System.out.println(this.firstName + " " + this.lastName + " " + " is playing...");
    }
    
    // Prints in the console to simulate Person running
    public void run()
    {
        System.out.println(this.firstName + " " + this.lastName + " " + " is running...");
    }
}


import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;


public class Controller {
    
    private boolean fileIsDirty; // Keeps track of whether there is unsaved data
    private ArrayList<Person> personList; // Keeps the list of all the People objects
    
    public Controller(){
        fileIsDirty = false;
        personList = new ArrayList();
    }
    
    // Returns true if the file gets saved correctly; returns false if the operation was cancelled or threw an exception
    public boolean saveAsNew(){
        String fileDirectory;
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(null); // Prompts the user to choose a file directory
        if (returnVal == JFileChooser.APPROVE_OPTION) 
            fileDirectory = chooser.getSelectedFile().getAbsolutePath(); // gets the directory from the user
        else
            return false; // Something went wrong or the user cancelled the operation
        return save(fileDirectory); // Moves on to actually saving the file
    }
    
    // Returns true if the file is saved correctly; returns false if an exception was thrown
    public boolean save(String currentFileDirectory){
        try{
            FileOutputStream fout = new FileOutputStream(currentFileDirectory);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            for (Object obj : personList)
                oout.writeObject(obj); // Writes each Person object to the file
            oout.close();
            fout.close();
            fileIsDirty = false; // Resets marker for unsaved data
            return true; // Signals that the file was saved correctly
        } 
        catch (Exception ex){
            return false; // Signals that something went wrong with saving the file
        }
    }
    
    public void loadPeopleFile(){
        // Prompt the user with a JFileChooser dialog, load the file, and then return the ArrayList of People objects
    }
    
    public void addPersonToList(String firstName, String lastName, OCCCDate dob){
        //Person p = new Person(firstName, lastName, dob);
        //personList.add(p);
        fileIsDirty = true;
    }
    
    public void addPersonToList(String firstName, String lastName, OCCCDate dob, String govID){
        //RegisteredPerson r = new RegisteredPerson(firstName, lastName, dob, govID);
        //personList.add(r);
        fileIsDirty = true;
    }
    
    public void addPersonToList(String firstName, String lastName, OCCCDate dob,
            String govID, String studentID){
        //OCCCPerson o = new OCCCPerson(firstName, lastName, dob, govID, studentID);
        //personList.add(o);
        fileIsDirty = true;
    }
    
    public void removePersonFromList(Person person) {
        personList.remove(person);
        fileIsDirty = true;
    }
    
    public void removePersonFromList(int index){
        personList.remove(index);
        fileIsDirty = true;
    }
    
    public ArrayList getPersonList(){
        return personList;
    }
    
    public static int getNumberOfDaysInMonth(int month, int year){
        
        int numDays;
        
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            numDays = 31;
            
        else if (month == 4 || month == 6 || month == 9 || month == 11)
            numDays = 30;
            
        else if (month == 2)
        {
            if (year % 4 > 0)
                numDays = 28;
            
            else if (year % 100 == 0)
                numDays = 29;
            
            else if (year % 400 > 0)
                numDays = 28;
            
            else
                numDays = 29;
            
        }
        else
            throw new IllegalArgumentException("int month must be between 1 and 12");
        
        return numDays;
    }
    
    public static int getNumberOfDaysInMonth(int month){
        return getNumberOfDaysInMonth(month, 1900); // Placeholder value if year is not available (Not a leap year)
    }
    
    
    
}

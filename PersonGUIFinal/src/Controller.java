
import java.util.ArrayList;

public class Controller {
    
    private boolean fileIsDirty; // Keeps track of whether there is unsaved data
    
    public Controller()
    {
        fileIsDirty = false;
    }
    
    
    public static void savePeopleFile(ArrayList<Person> peopleList, String currentFileDirectory)
    {
        // Saves the ArrayList of People objects to the provided directory
    }
    
    public static void saveAsNewPeopleFile(ArrayList<Person> peopleList)
    {
        // Prompts the user with a JFileChooser dialog and then calls savePeopleFile with the chosen directory
    }
    
    public static ArrayList loadPeopleFile()
    {
        // Prompt the user with a JFileChooser dialog, load the file, and then return the ArrayList of People objects
        return null;
    }
    
    public static int getNumberOfDaysInMonth(int month, int year)
    {
        // algorithm to determine how many days are supposed to be in the given month
        return -1;
    }
    
    public static int getNumberOfDaysInMonth(int month)
    {
        return getNumberOfDaysInMonth(month, 1900); // Placeholder value if year is not available (Not a leap year)
    }
    
    
    
}

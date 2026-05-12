// Makayla Wood
// Class to handle the logical operations of the Person GUI program (file management, variable management, date calculations, etc.)

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Controller {
    
    private boolean fileIsDirty; // Keeps track of whether there is unsaved data
    private boolean editingPerson; // Keeps track of if a person is in the middle of being edited
    private Person oldPerson; // Holder for Person object being edited
    private ArrayList<Person> personList; // Keeps the list of all the People objects
    private File currentFile; // Keeps track of the current working file
    
    public Controller(){
        fileIsDirty = false;
        personList = new ArrayList();
    }
    
    public int saveAsNew(){ // Returns 0 if the file was saved correctly, 1 if the operation was cancelled, and -1 if an exception was thrown by JFileChooser
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(null); // Prompts the user to choose a file directory
        if (returnVal == JFileChooser.APPROVE_OPTION) 
            currentFile = chooser.getSelectedFile(); // gets the directory from the user
        else
            return returnVal; // Something went wrong or the user cancelled the operation
        return save(); // Moves on to actually saving the file
    }
    
    public int save(){ // Returns 0 if the file was saved correctly, 1 if the operation was cancelled, and -1 if an exception was thrown
        if (currentFile != null) { // Checks that there is a current working file to save to
            try{
                FileOutputStream fout = new FileOutputStream(currentFile);
                ObjectOutputStream oout = new ObjectOutputStream(fout);
                for (Object obj : personList)
                    oout.writeObject(obj); // Writes each Person object to the file
                oout.close();
                fout.close();
                fileIsDirty = false; // Resets marker for unsaved data
                return 0; // Signals that the file was saved correctly
            } catch (Exception ex){
                return -1; // Signals that something went wrong with saving the file
            }
        }
        else
            return saveAsNew(); // Goes to save as new if no working file exists
    }
    
    public int loadPeopleFile(){ // Loads the file into personList. Returns 0 for successful loading, 1 for cancel and -1 for an error
        if (fileIsDirty) { // Prompts the user to save, not save, or cancel loading a new file if unsaved data exists
            int returnVal = JOptionPane.showConfirmDialog(null, "You have unsaved data. Would you like to save before continuing?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (returnVal == JOptionPane.YES_OPTION)
                save();
            else if (returnVal == JOptionPane.CANCEL_OPTION)
                return 0;
        }
        // Gets the file from the user and loads it into the program
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try{
                    File file = chooser.getSelectedFile();
                    FileInputStream fin = new FileInputStream(file.getAbsolutePath());
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    personList = new ArrayList<>(); // Resets the list of Person objects
                    while (true)
                    {
                        try
                        {
                            Person person = (Person)oin.readObject();
                            personList.add(person);
                        }
                        catch (EOFException eofEx)
                        {
                            break;
                        }
                    }
                    fileIsDirty = false; // Resets unsaved data marker
                    currentFile = file; // Sets the loaded file as the current working file
                } catch (Exception ex) {
                    return -1;
                }
        }
        return returnVal;
    }
    
    public int startNewFile() { // Checks for unsaved data and then resets fields to their default states
        if (checkUnsavedData()) {
            personList = new ArrayList<>();
            currentFile = null;
            fileIsDirty = false;
            return 0;
        }
        else
            return 1;
    }
    
    public boolean checkUnsavedData() { // Prompts the user for how to handle unsaved data if it exists; Returns true to continue operation or false to cancel
        if (fileIsDirty) { // Prompts the user to save, not save, or cancel loading a new file if unsaved data exists
            int returnVal = JOptionPane.showConfirmDialog(null, "You have unsaved data. Would you like to save before continuing?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (returnVal == JOptionPane.YES_OPTION) {
                switch (save()) {
                    case 0: return true;
                    
                    case 1: return false;
                    
                    case -1: return false;
                    
                    default: return false;
                }
            }
            else if (returnVal == JOptionPane.NO_OPTION) {
                return true;
            }
            else {
                return false;
            }
        }
        else
            return true;
    }
    
    // Each addPersonToList function creates a new Person object based on the provided information
    // and adds it to personList. If a Person object is currently being edited, then that Person object
    // is removed before being replaced with the new one.
    
    public void addPersonToList(String firstName, String lastName, OCCCDate dob){
        if (editingPerson && oldPerson != null) {
            personList.remove(oldPerson);
            stopEditingPerson();
        }
        Person p = new Person(firstName, lastName, dob);
        personList.add(p);
        fileIsDirty = true;
    }
    
    public void addPersonToList(String firstName, String lastName, OCCCDate dob, String govID){
        if (editingPerson && oldPerson != null) {
            personList.remove(oldPerson);
            stopEditingPerson();
        }
        RegisteredPerson r = new RegisteredPerson(firstName, lastName, dob, govID);
        personList.add(r);
        fileIsDirty = true;
    }
    
    public void addPersonToList(String firstName, String lastName, OCCCDate dob,
            String govID, String studentID){
        if (editingPerson && oldPerson != null) {
            personList.remove(oldPerson);
            stopEditingPerson();
        }
        OCCCPerson o = new OCCCPerson(new RegisteredPerson(firstName, lastName, dob, govID), studentID);
        personList.add(o);
        fileIsDirty = true;
    }
    
    public void removePersonFromList(Person person) {
        personList.remove(person);
        fileIsDirty = true;
    }
    
    public ArrayList getPersonList(){
        return personList;
    }
    
    public boolean currentFileExists() {
        return currentFile != null;
    }
    
    public void startEditingPerson(Person p) {
        editingPerson = true;
        oldPerson = p;
    }
    
    public void stopEditingPerson() {
        editingPerson = false;
        oldPerson = null;
    }
    
    public static int getNumberOfDaysInMonth(int month, int year){
        
        int numDays;
        
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            numDays = 31;
            
        else if (month == 4 || month == 6 || month == 9 || month == 11)
            numDays = 30;
            
        else if (month == 2)
        {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                numDays = 29;
            else
                numDays = 28;
        }
        else
            throw new IllegalArgumentException("int month must be between 1 and 12");
        
        return numDays;
    }
    
}

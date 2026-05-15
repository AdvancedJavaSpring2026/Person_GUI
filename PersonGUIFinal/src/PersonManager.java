import java.io.*;
import java.util.ArrayList;

public class PersonManager {

    private ArrayList<Person> people;

    private boolean changed;

    private File currentFile;

    public PersonManager() {

        people = new ArrayList<>();

        changed = false;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void addPerson(Person p) {

        people.add(p);

        changed = true;
    }

    public void deletePerson(Person p) {

        people.remove(p);

        changed = true;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void save(File file)
            throws IOException {

        ObjectOutputStream oos =
                new ObjectOutputStream(
                        new FileOutputStream(file)
                );

        oos.writeObject(people);

        oos.close();

        currentFile = file;

        changed = false;
    }

    @SuppressWarnings("unchecked")
    public void load(File file)
            throws IOException,
                   ClassNotFoundException {

        ObjectInputStream ois =
                new ObjectInputStream(
                        new FileInputStream(file)
                );

        people =
                (ArrayList<Person>)
                        ois.readObject();

        ois.close();

        currentFile = file;

        changed = false;
    }

    public void clear() {

        people.clear();

        currentFile = null;

        changed = false;
    }
}
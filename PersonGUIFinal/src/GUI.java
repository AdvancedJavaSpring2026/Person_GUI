import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener{

    //NOTE FROM JAMIE - WE CAN MOVE ANY OR ALL ACTIONLISTENER THINGS OUT OF HERE IF NEEDED


    private static final int WIDTH = 1024;
    private static final int HEIGHT = 1024;

    //Custom Colors
    Color colorPurpleDark = new Color(132, 94, 194);
    Color colorPurpleLight = new Color(214, 93, 177);
    Color colorRedGentle = new Color(255, 111, 145);
    Color colorOrangeDark = new Color(255, 150, 113);
    Color colorOrangeLight = new Color(255, 199, 95);
    Color colorYellow = new Color(249, 248, 113);

    //Menu components
    JMenuItem fileMenu_new;
    JMenuItem fileMenu_open;
    JMenuItem fileMenu_save;
    JMenuItem fileMenu_saveAs;
    JMenuItem fileMenu_exit;

    JMenuItem helpMenu_help;
    JMenuItem helpMenu_about;

    //Layout components
    JPanel fullScreenPanel, topPanel, middlePanelTop, middlePanelBottom, bottomPanel, personDropdownPanel,
            personButtonPanel, personTextFieldsPanel, selectedPersonPanel, personInfoPanel, dateDropdownsPanel;
    GridBagLayout gridBagLayout;
    GridBagConstraints topPanConstraints, bottomPanConstraints;

    //Components that do things
    JButton newPersonButton, editPersonButton, deletePersonButton, storePersonButton;
    JComboBox<Person> personDropdown;
    JComboBox<String> monthDropdown;
    JComboBox<Integer> dayDropdown;
    JComboBox<Integer> yearDropdown;

    //Data (this may wind up being in Victoria's work, and deleted here)
    String firstName, lastName, studentID, govID;
    String selectedPersonsText = " SELECTED PERSON INFORMATION";
    JLabel selectedPersonLabel = new JLabel(selectedPersonsText);
    JLabel firstNameLabel, lastNameLabel, govIDLabel, studentIDLabel, dobLabel;
    JTextField firstNameField, lastNameField, govIDField, studentIDField;
    String[] months = {"January", "February", "March", "April", "May", "June", 
        "July", "August", "September", "October", "November", "December"};
    
    //Controller that handles logic operations
    Controller controller;


    public GUI(){
        super("Temporary Title");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        controller = new Controller();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                //Popup
                //Check if needing to Save,
                // if yes, then Save,
                // if No then close,
                // if Cancel then get rid of popup and do nothing
            }
        });

        setUpMenu();

        setGUILayout();
        setUpDateBoxes();
        
        addActionListeners();
        this.pack();

        setSize(WIDTH, HEIGHT);

        setResizable(false);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private void addActionListeners(){
        newPersonButton.addActionListener(e -> openInputFields(true));
        editPersonButton.addActionListener(e -> openInputFields(false));
        deletePersonButton.addActionListener(e -> deletePersonFromList());
        storePersonButton.addActionListener(e -> addPersonToList());
        personDropdown.addActionListener(e -> personDropdownAction());
        monthDropdown.addActionListener(e -> refreshDayComboBox());
        dayDropdown.addActionListener(this);
        yearDropdown.addActionListener(e -> refreshDayComboBox());
        
        fileMenu_new.addActionListener(this);
        fileMenu_open.addActionListener(e -> loadFile());
        fileMenu_save.addActionListener(e -> saveFile(false));
        fileMenu_saveAs.addActionListener(e -> saveFile(true));
        fileMenu_exit.addActionListener(this);
        helpMenu_help.addActionListener(this);
        helpMenu_about.addActionListener(this);
    }

    private void setGUILayout(){
        setLayout(new GridLayout(1, 1));

        fullScreenPanel = new JPanel();
        topPanel = new JPanel(new GridLayout(1,2));
        middlePanelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanelBottom = new JPanel(new GridLayout(1,2));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        personDropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        personButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectedPersonPanel = new JPanel(new BorderLayout());
        personInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));//new GridLayout(5,1)
        personTextFieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));//new GridLayout(4,1)
        dateDropdownsPanel = new JPanel(new BorderLayout());

        fullScreenPanel.setBackground(colorOrangeLight);
        topPanel.setBackground(colorRedGentle);
        middlePanelTop.setBackground(colorOrangeDark);
        middlePanelBottom.setBackground(colorYellow);
        bottomPanel.setBackground(colorPurpleDark);

        personButtonPanel.setBackground(colorRedGentle);
        personDropdownPanel.setBackground(colorRedGentle);

        topPanel.setPreferredSize(new Dimension(WIDTH, 50));
        middlePanelTop.setPreferredSize(new Dimension(WIDTH, 50));
        middlePanelBottom.setPreferredSize(new Dimension(WIDTH, 700));
        bottomPanel.setPreferredSize(new Dimension(WIDTH, 100));

        personDropdownPanel.setPreferredSize(new Dimension(100, 50));
        personButtonPanel.setPreferredSize(new Dimension(100, 40));
        selectedPersonPanel.setPreferredSize(new Dimension(WIDTH, 50));
        personInfoPanel.setPreferredSize(new Dimension(200, 200));
        personTextFieldsPanel.setPreferredSize(new Dimension(50, 50));
        dateDropdownsPanel.setPreferredSize(new Dimension(100, 100));

        //Top left Panel holding Person Dropdown Box
        personDropdown = new JComboBox<>();

        //Top right Panel holding Person buttons
        newPersonButton = new JButton("New Person");
        editPersonButton = new JButton("Edit Person");
        deletePersonButton = new JButton("Delete Person");

        personDropdownPanel.add(personDropdown);

        personButtonPanel.add(newPersonButton);
        personButtonPanel.add(editPersonButton);
        personButtonPanel.add(deletePersonButton);

        topPanel.add(personDropdownPanel);
        topPanel.add(personButtonPanel);

        selectedPersonPanel.add(selectedPersonLabel, BorderLayout.CENTER);
        middlePanelTop.add(selectedPersonPanel);

        //Bottom left Panel holding Person info text ("First Name: ", etc.)

        firstNameLabel = new JLabel("First Name: ");
        lastNameLabel = new JLabel("Last Name: ");
        govIDLabel = new JLabel("Government ID: ");
        studentIDLabel = new JLabel("Student ID: ");
        dobLabel = new JLabel("Date of Birth: ");
        personInfoPanel.add(firstNameLabel);
        personInfoPanel.add(lastNameLabel);
        personInfoPanel.add(govIDLabel);
        personInfoPanel.add(studentIDLabel);
        personInfoPanel.add(dobLabel);


        //Bottom right Panel holding Person fields


        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        govIDField = new JTextField(15);
        studentIDField = new JTextField(15);

        personTextFieldsPanel.add(firstNameField);
        personTextFieldsPanel.add(lastNameField);
        personTextFieldsPanel.add(govIDField);
        personTextFieldsPanel.add(studentIDField);

        //Bottom right Panel holding date dropdowns
        monthDropdown = new JComboBox<>();
        dayDropdown = new JComboBox<>();
        yearDropdown = new JComboBox<>();

        dateDropdownsPanel.add(dayDropdown);
        dateDropdownsPanel.add(monthDropdown);
        dateDropdownsPanel.add(yearDropdown);

        //Bottom panel for Storing Person
        storePersonButton = new JButton("Store Person");

        topPanel.add(personDropdownPanel);
        topPanel.add(personButtonPanel);

        middlePanelTop.add(selectedPersonPanel);

        middlePanelBottom.add(personInfoPanel);
        middlePanelBottom.add(personTextFieldsPanel);
        middlePanelBottom.add(dateDropdownsPanel);



        bottomPanel.add(storePersonButton);

        fullScreenPanel.setLayout(new BorderLayout());

        fullScreenPanel.add(topPanel, BorderLayout.PAGE_START);
        fullScreenPanel.add(middlePanelTop, BorderLayout.LINE_START);
        fullScreenPanel.add(middlePanelBottom, BorderLayout.LINE_END);
        fullScreenPanel.add(bottomPanel, BorderLayout.PAGE_END);

        add(fullScreenPanel);

    }

    private void setUpMenu(){
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu helpMenu = new JMenu("Help");
        fileMenu.setMnemonic(KeyEvent.VK_H);

        fileMenu_new = new JMenuItem("New");
        fileMenu_new.setMnemonic(KeyEvent.VK_N);

        fileMenu_open = new JMenuItem("Open...");
        fileMenu_open.setMnemonic(KeyEvent.VK_O);

        fileMenu_save = new JMenuItem("Save");
        fileMenu_save.setMnemonic(KeyEvent.VK_V);

        fileMenu_saveAs = new JMenuItem("Save As...");
        fileMenu_saveAs.setMnemonic(KeyEvent.VK_A);

        fileMenu_exit = new JMenuItem("Exit");
        fileMenu_exit.setMnemonic(KeyEvent.VK_X);

        helpMenu_help = new JMenuItem("Help");
        helpMenu_help.setMnemonic(KeyEvent.VK_P);

        helpMenu_about = new JMenuItem("About");
        helpMenu_about.setMnemonic(KeyEvent.VK_B);

        fileMenu.add(fileMenu_new);
        fileMenu.add(fileMenu_open);
        fileMenu.add(fileMenu_save);
        fileMenu.add(fileMenu_saveAs);
        fileMenu.addSeparator();
        fileMenu.add(fileMenu_exit);

        helpMenu.add(helpMenu_help);
        helpMenu.add(helpMenu_about);

        bar.add(fileMenu);
        bar.add(Box.createHorizontalGlue());
        bar.add(helpMenu);
        setJMenuBar(bar);
    }
    
    
    private void setUpDateBoxes() { // Gives the date combo boxes their initial values
        for (int i = 1900; i < java.time.Year.now().getValue(); i++) // Adds every year from 1900 to now for yearDropdown
            yearDropdown.addItem(i);
        for (String month : months)
            monthDropdown.addItem(month);
        yearDropdown.setSelectedIndex(yearDropdown.getItemCount() - 1);
        monthDropdown.setSelectedIndex(0);
        refreshDayComboBox();
    }
    
    private void addPersonToList() { // Takes the information from the current fields, creates a new person object and adds it to the list
        // Checks to make sure that required fields are filled out
        if (firstNameField.getText().isBlank())
            JOptionPane.showMessageDialog(this, "First name is a required field!", "", JOptionPane.INFORMATION_MESSAGE);
        else if (lastNameField.getText().isBlank())
            JOptionPane.showMessageDialog(this, "Last name is a required field!", "", JOptionPane.INFORMATION_MESSAGE);
        else if (!studentIDField.getText().isBlank() && govIDField.getText().isBlank())
            JOptionPane.showMessageDialog(this, "Government ID is required for persons with student ID", "", JOptionPane.INFORMATION_MESSAGE);
        
        // Determines what kind of Person object to make and calls the controller to add it to the list with the data provided in each field
        else {
            try {
                if (!govIDField.getText().isBlank()) {
                    if (!studentIDField.getText().isBlank()) {
                        controller.addPersonToList(firstNameField.getText(), lastNameField.getText(), 
                                new OCCCDate((int)dayDropdown.getSelectedItem(), monthDropdown.getSelectedIndex() + 1, (int)yearDropdown.getSelectedItem()), 
                                govIDField.getText(), studentIDField.getText());
                    }
                    else {
                        controller.addPersonToList(firstNameField.getText(), lastNameField.getText(), 
                                new OCCCDate((int)dayDropdown.getSelectedItem(), monthDropdown.getSelectedIndex() + 1, (int)yearDropdown.getSelectedItem()), 
                                govIDField.getText());
                    }
                }
                else {
                    controller.addPersonToList(firstNameField.getText(), lastNameField.getText(), 
                                new OCCCDate((int)dayDropdown.getSelectedItem(), monthDropdown.getSelectedIndex() + 1, (int)yearDropdown.getSelectedItem()));
                }
                refreshPersonComboBox(); // Ensures that the newly added Person is displayed to the user
            } catch (InvalidOCCCDateException ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE); // Throws error message if OCCCDate throws and exception
            }
            closeInputFields(); // After Person object is added, the program gets out of person editing/creation mode
        }
        
        
    }
    
    private void deletePersonFromList() { // Deletes the selected Person object
        if (personDropdown.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "No person selected for deletion", "", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            Person p = (Person)personDropdown.getSelectedItem();
            String name = p.getFirstName() + " " + p.getLastName();
            int returnVal = JOptionPane.showConfirmDialog(this, "Are you sure that you would like to delete " + name + "?", 
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (returnVal == JOptionPane.YES_OPTION) {
                controller.removePersonFromList(p);
                refreshPersonComboBox();
            }
        }
    }
    
    private void refreshPersonComboBox() { // Gets the latest list of People objects and puts it into the combo box
        personDropdown.removeAllItems();
        ArrayList<Person> pl = controller.getPersonList();
        pl.sort(Comparator.comparing(Person::getLastName));
        for (Person p : pl)
            personDropdown.addItem(p);
    }
    
    private void personDropdownAction() { // When the personDropdown index changes, this runs to make all the fields match the user's current selection
        if (personDropdown.getSelectedIndex() == -1) {
            selectedPersonLabel.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            govIDField.setText("");
            studentIDField.setText("");
            yearDropdown.setSelectedIndex(0);
            monthDropdown.setSelectedIndex(0);
        }
        else {
            if (personDropdown.getSelectedItem().getClass().equals(Person.class)) {
                Person p = (Person)personDropdown.getSelectedItem();
                OCCCDate dob = p.getDOB();
                firstNameField.setText(p.getFirstName());
                lastNameField.setText(p.getLastName());
                govIDField.setText("");
                studentIDField.setText("");
                yearDropdown.setSelectedItem(dob.getYear());
                monthDropdown.setSelectedIndex(dob.getMonthNumber() - 1);
                dayDropdown.setSelectedIndex(dob.getDayofMonth() - 1);
            }
            else if (personDropdown.getSelectedItem().getClass().equals(RegisteredPerson.class)) {
                RegisteredPerson p = (RegisteredPerson)personDropdown.getSelectedItem();
                OCCCDate dob = p.getDOB();
                firstNameField.setText(p.getFirstName());
                lastNameField.setText(p.getLastName());
                govIDField.setText(p.getGovernmentID());
                studentIDField.setText("");
                yearDropdown.setSelectedItem(dob.getYear());
                monthDropdown.setSelectedIndex(dob.getMonthNumber() - 1);
                dayDropdown.setSelectedIndex(dob.getDayofMonth() - 1);
            }
            else if (personDropdown.getSelectedItem().getClass().equals(OCCCPerson.class)) {
                OCCCPerson p = (OCCCPerson)personDropdown.getSelectedItem();
                OCCCDate dob = p.getDOB();
                firstNameField.setText(p.getFirstName());
                lastNameField.setText(p.getLastName());
                govIDField.setText(p.getGovernmentID());
                studentIDField.setText(p.getStudentID());
                yearDropdown.setSelectedItem(dob.getYear());
                monthDropdown.setSelectedIndex(dob.getMonthNumber() - 1);
                dayDropdown.setSelectedIndex(dob.getDayofMonth() - 1);
            }
        }
    }
    
    private void refreshDayComboBox() { // Gets the current number of days in the month and refreshs the day combo box
        int numDays = Controller.getNumberOfDaysInMonth(monthDropdown.getSelectedIndex() + 1, (int)yearDropdown.getSelectedItem());
        dayDropdown.removeAllItems();
        for (int i = 1; i <= numDays; i++)
            dayDropdown.addItem(i);
        dayDropdown.setSelectedIndex(0);
    }
    
    private void openInputFields(boolean forNewPerson) { // Opens all the input fields to create a new Person object
        // If this is for a new person, the combo box is set to have no selection
        if (forNewPerson) {
            personDropdown.setSelectedIndex(-1);
            selectedPersonLabel.setText("NEW PERSON");
        }
        
        personDropdown.setEnabled(false);
        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        govIDField.setEnabled(true);
        studentIDField.setEnabled(true);
        dayDropdown.setEnabled(true);
        monthDropdown.setEnabled(true);
        yearDropdown.setEnabled(true);
    }
    
    private void closeInputFields() { // Closes all the input fields when Person object creation is finished
        personDropdown.setEnabled(true);
        firstNameField.setEnabled(false);
        lastNameField.setEnabled(false);
        govIDField.setEnabled(false);
        studentIDField.setEnabled(false);
        dayDropdown.setEnabled(false);
        monthDropdown.setEnabled(false);
        yearDropdown.setEnabled(false);
    }

    private void saveFile(boolean saveAsNewFile) { // Saves the user's current work
        int result = 2; // Default value that won't trigger a message
        
        if (saveAsNewFile || !controller.currentFileExists())
            result = controller.saveAsNew();
        else 
            result = controller.save();
        
        if (result == -1)
            JOptionPane.showMessageDialog(this, "An error has occurred saving your file. Please try again", "Saving Error", JOptionPane.ERROR_MESSAGE);
        else if (result == 0)
            JOptionPane.showMessageDialog(this, "Your file saved successfully!", "File Saved", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void loadFile() { // Calls the controller to load a file
        int result = controller.loadPeopleFile();
        if (result == 0) {
            refreshPersonComboBox(); // Loads the new Person list into the dropdown 
            personDropdown.setSelectedIndex(-1);
        }
        else if (result == -1) 
            JOptionPane.showMessageDialog(this, "An error has occurred loading your file. Please try again", "Loading Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void startNewFile() { // Calls the controller to start a new file
        int result = controller.startNewFile();
    }

}

// Jamie Whitmarsh and Makayla Wood
// Class to display and operate the program's GUI

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener{

    //NOTE FROM JAMIE - WE CAN MOVE ANY OR ALL ACTIONLISTENER THINGS OUT OF HERE IF NEEDED


    private static final int WIDTH = 720;
    private static final int HEIGHT = 720;
    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", 
        "July", "August", "September", "October", "November", "December"};

    //Custom Colors
    Color colorRosewood = new Color(99, 0, 3);
    Color colorCream = new Color(249, 221, 177);

    

    //Fonts
    Font mainFontBold = new Font("Times New Roman", Font.BOLD, 16);
    Font mainFontLight = new Font("Times New Roman", Font.PLAIN, 16);

    //Menu components
    JMenuItem fileMenu_new;
    JMenuItem fileMenu_open;
    JMenuItem fileMenu_save;
    JMenuItem fileMenu_saveAs;
    JMenuItem fileMenu_exit;

    JMenuItem helpMenu_help;
    JMenuItem helpMenu_about;

    //Layout components
    JPanel fullScreenPanel, topPanel, middlePanelTop, middlePanelBottom, middlePanelContainer, bottomPanel, personDropdownPanel,
            personButtonPanel, personTextFieldsPanel, selectedPersonPanel, personInfoPanel, dateDropdownsPanel,
            field1, field2, field3, field4, label1, label2, label3, label4, label5, date1, date2, date3;

    //Components that do things
    JButton newPersonButton, editPersonButton, deletePersonButton, storePersonButton;
    JComboBox<Person> personDropdown;
    JComboBox<String> monthDropdown;
    JComboBox<Integer> dayDropdown;
    JComboBox<Integer> yearDropdown;

    //Data (this may wind up being in Victoria's work, and deleted here)
    String selectedPersonsText = " SELECTED PERSON INFORMATION";
    JLabel selectedPersonLabel;
    JLabel firstNameLabel, lastNameLabel, govIDLabel, studentIDLabel, dobLabel, dropdownLabel;
    JTextField firstNameField, lastNameField, govIDField, studentIDField;
    
    //Controller that handles logic operations
    Controller controller;


    public GUI(){
        super("Person GUI App - Makayla, Victoria, Jamie");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        controller = new Controller();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                exitProgram();
            }
        });

        setUpMenu();

        setGUILayout();
        setUpDateBoxes();
        closeInputFields();
        
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
        yearDropdown.addActionListener(e -> refreshDayComboBox());
        
        fileMenu_new.addActionListener(e -> startNewFile());
        fileMenu_open.addActionListener(e -> loadFile());
        fileMenu_save.addActionListener(e -> saveFile(false));
        fileMenu_saveAs.addActionListener(e -> saveFile(true));
        fileMenu_exit.addActionListener(e -> exitProgram());
        helpMenu_help.addActionListener(e -> showHelpDialog());
        helpMenu_about.addActionListener(e -> showAboutDialog());
    }

    private void setGUILayout(){
        setLayout(new GridLayout(1, 1));

        fullScreenPanel = new JPanel();
        topPanel = new JPanel(new GridLayout(1,2));
        middlePanelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanelBottom = new JPanel(new GridLayout(1,2));
        middlePanelContainer = new JPanel(new BorderLayout());
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        personDropdownPanel = new JPanel(new BorderLayout());
        personButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectedPersonPanel = new JPanel(new BorderLayout());
        personInfoPanel = new JPanel(new GridLayout(5, 1, 0, 15));//new GridLayout(5,1)
        personTextFieldsPanel = new JPanel(new GridLayout(5, 1, 0, 20));//new GridLayout(4,1)
        dateDropdownsPanel = new JPanel(new BorderLayout());

        topPanel.setPreferredSize(new Dimension(WIDTH, 70));
        middlePanelTop.setPreferredSize(new Dimension(WIDTH, 100));
        middlePanelBottom.setPreferredSize(new Dimension(WIDTH, 400));
        middlePanelContainer.setPreferredSize(new Dimension(WIDTH, 750));
        bottomPanel.setPreferredSize(new Dimension(WIDTH, 100));

        personDropdownPanel.setPreferredSize(new Dimension(100, 30));
        personButtonPanel.setPreferredSize(new Dimension(100, 50));
        selectedPersonPanel.setPreferredSize(new Dimension(300, 50));
        personInfoPanel.setPreferredSize(new Dimension(200, 200));
        personTextFieldsPanel.setPreferredSize(new Dimension(100, 50));
        dateDropdownsPanel.setPreferredSize(new Dimension(200, 100));

        fullScreenPanel.setBackground(colorCream);
        topPanel.setBackground(colorRosewood);
        middlePanelTop.setBackground(colorRosewood);
        middlePanelBottom.setBackground(colorCream);
        bottomPanel.setBackground(colorRosewood);

        personButtonPanel.setBackground(colorCream);
        personDropdownPanel.setBackground(colorCream);
        personInfoPanel.setBackground(colorCream);
        personTextFieldsPanel.setBackground(colorCream);

        // Forces the JComboBoxes to look normal even when disabled
        UIManager.put("ComboBox.disabledForeground", UIManager.getColor("ComboBox.foreground"));
        UIManager.put("ComboBox.disabledBackground", UIManager.getColor("ComboBox.background"));


        //Top left Panel holding Person Dropdown Box
        personDropdown = new JComboBox<>();
        dropdownLabel = new JLabel("List of available Persons", SwingConstants.CENTER);
        dropdownLabel.setFont(mainFontBold);
        Dimension comboBoxSize = new Dimension(personDropdown.getPreferredSize());
        comboBoxSize.height = 20;
        comboBoxSize.width = 200;
        personDropdown.setPreferredSize(comboBoxSize);

        //Top right Panel holding Person buttons
        newPersonButton = new JButton("New Person");
        editPersonButton = new JButton("Edit Person");
        editPersonButton.setEnabled(false); // Edit button isn't available until a Person object is chosen from personDropdown
        deletePersonButton = new JButton("Delete Person");

        personDropdownPanel.setBorder(BorderFactory.createEmptyBorder(5,5,15,5));
        personDropdownPanel.add(dropdownLabel, BorderLayout.CENTER);
        personDropdownPanel.add(personDropdown, BorderLayout.SOUTH);

        personButtonPanel.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));

        personButtonPanel.add(newPersonButton);
        personButtonPanel.add(editPersonButton);
        personButtonPanel.add(deletePersonButton);

        topPanel.add(personDropdownPanel);
        topPanel.add(personButtonPanel);

        selectedPersonLabel = new JLabel(selectedPersonsText, SwingConstants.CENTER);
        selectedPersonPanel.add(selectedPersonLabel, BorderLayout.CENTER);
        middlePanelTop.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        middlePanelTop.add(selectedPersonPanel);

        //Middle left Panel holding Person info text ("First Name: ", etc.)

        firstNameLabel = new JLabel("First Name: ");
        lastNameLabel = new JLabel("Last Name: ");
        govIDLabel = new JLabel("Government ID: ");
        studentIDLabel = new JLabel("Student ID: ");
        dobLabel = new JLabel("Date of Birth: ");

        label1 = new JPanel();
        label2 = new JPanel();
        label3 = new JPanel();
        label4 = new JPanel();
        label5 = new JPanel();

        label1.add(firstNameLabel);
        label2.add(lastNameLabel);
        label3.add(govIDLabel);
        label4.add(studentIDLabel);
        label5.add(dobLabel);

        label1.setBackground(colorCream);
        label2.setBackground(colorCream);
        label3.setBackground(colorCream);
        label4.setBackground(colorCream);
        label5.setBackground(colorCream);

        label1.setPreferredSize(new Dimension(50, 10));
        label2.setPreferredSize(new Dimension(50, 10));
        label3.setPreferredSize(new Dimension(50, 10));
        label4.setPreferredSize(new Dimension(50, 10));
        label5.setPreferredSize(new Dimension(50, 10));

        personInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        personInfoPanel.add(label1);
        personInfoPanel.add(label2);
        personInfoPanel.add(label3);
        personInfoPanel.add(label4);
        personInfoPanel.add(label5);


        //Middle right Panel holding Person fields

        firstNameField = new JTextField(30);
        lastNameField = new JTextField(30);
        govIDField = new JTextField(30);
        studentIDField = new JTextField(30);

        field1 = new JPanel();
        field2 = new JPanel();
        field3 = new JPanel();
        field4 = new JPanel();

        field1.add(firstNameField);
        field2.add(lastNameField);
        field3.add(govIDField);
        field4.add(studentIDField);

        field1.setBackground(colorCream);
        field2.setBackground(colorCream);
        field3.setBackground(colorCream);
        field4.setBackground(colorCream);

        field1.setPreferredSize(new Dimension(50, 10));
        field2.setPreferredSize(new Dimension(50, 10));
        field3.setPreferredSize(new Dimension(50, 10));
        field4.setPreferredSize(new Dimension(50, 10));

        personTextFieldsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        personTextFieldsPanel.add(field1);
        personTextFieldsPanel.add(field2);
        personTextFieldsPanel.add(field3);
        personTextFieldsPanel.add(field4);

        //Bottom right Panel holding date dropdowns
        monthDropdown = new JComboBox<>(MONTHS);
        dayDropdown = new JComboBox<>();
        yearDropdown = new JComboBox<>();

        date1 = new JPanel();
        date2 = new JPanel();
        date3 = new JPanel();

        date1.setBackground(colorCream);
        date2.setBackground(colorCream);
        date3.setBackground(colorCream);

        date1.setPreferredSize(new Dimension(50, 10));
        date2.setPreferredSize(new Dimension(250, 10));
        date3.setPreferredSize(new Dimension(125, 10));

        date2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        date1.add(dayDropdown);
        date2.add(monthDropdown);
        date3.add(yearDropdown);

        dateDropdownsPanel.add(date1, BorderLayout.LINE_START);
        dateDropdownsPanel.add(date2, BorderLayout.CENTER);
        dateDropdownsPanel.add(date3, BorderLayout.LINE_END);

        personTextFieldsPanel.add(dateDropdownsPanel);

        //Bottom panel for Storing Person
        storePersonButton = new JButton("Store Person");
        storePersonButton.setEnabled(false); // Store button not enabled until user enters editing mode

        newPersonButton.setFont(mainFontLight);
        editPersonButton.setFont(mainFontLight);
        deletePersonButton.setFont(mainFontLight);

        selectedPersonLabel.setFont(mainFontBold);

        firstNameLabel.setFont(mainFontBold);
        lastNameLabel.setFont(mainFontBold);
        govIDLabel.setFont(mainFontBold);
        studentIDLabel.setFont(mainFontBold);
        dobLabel.setFont(mainFontBold);

        monthDropdown.setFont(mainFontBold);
        dayDropdown.setFont(mainFontBold);
        yearDropdown.setFont(mainFontBold);

        storePersonButton.setFont(mainFontBold);

        middlePanelBottom.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        middlePanelBottom.add(personInfoPanel);
        middlePanelBottom.add(personTextFieldsPanel);

        middlePanelContainer.add(middlePanelTop, BorderLayout.NORTH);
        middlePanelContainer.add(middlePanelBottom, BorderLayout.CENTER);

        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        bottomPanel.add(storePersonButton);

        fullScreenPanel.setLayout(new BorderLayout());

        fullScreenPanel.add(topPanel, BorderLayout.PAGE_START);
        fullScreenPanel.add(middlePanelContainer, BorderLayout.CENTER);
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
        for (int i = java.time.Year.now().getValue() - 1; i > 1899; i--) // Adds every year from 2025 to 1900 for yearDropdown
            yearDropdown.addItem(i);
        for (String month : MONTHS)
            monthDropdown.addItem(month);
        yearDropdown.setSelectedIndex(0);
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
    
    private void cancelEditing() { // Exits editing mode discarding any changes made
        controller.stopEditingPerson();
        closeInputFields();
        personDropdownAction();
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
            editPersonButton.setEnabled(false);
        }
        else {
            if (personDropdown.getSelectedItem().getClass().equals(Person.class)) {
                Person p = (Person)personDropdown.getSelectedItem();
                OCCCDate dob = p.getDateOfBirth();
                selectedPersonLabel.setText(p.getFirstName().toUpperCase() + " " + p.getLastName().toUpperCase());
                firstNameField.setText(p.getFirstName());
                lastNameField.setText(p.getLastName());
                govIDField.setText("");
                studentIDField.setText("");
                yearDropdown.setSelectedItem(dob.getYear());
                monthDropdown.setSelectedIndex(dob.getMonthNumber() - 1);
                dayDropdown.setSelectedIndex(dob.getDayofMonth() - 1);
                editPersonButton.setEnabled(true);
            }
            else if (personDropdown.getSelectedItem().getClass().equals(RegisteredPerson.class)) {
                RegisteredPerson p = (RegisteredPerson)personDropdown.getSelectedItem();
                OCCCDate dob = p.getDateOfBirth();
                selectedPersonLabel.setText(p.getFirstName().toUpperCase() + " " + p.getLastName().toUpperCase());
                firstNameField.setText(p.getFirstName());
                lastNameField.setText(p.getLastName());
                govIDField.setText(p.getGovernmentID());
                studentIDField.setText("");
                yearDropdown.setSelectedItem(dob.getYear());
                monthDropdown.setSelectedIndex(dob.getMonthNumber() - 1);
                dayDropdown.setSelectedIndex(dob.getDayofMonth() - 1);
                editPersonButton.setEnabled(true);
            }
            else if (personDropdown.getSelectedItem().getClass().equals(OCCCPerson.class)) {
                OCCCPerson p = (OCCCPerson)personDropdown.getSelectedItem();
                OCCCDate dob = p.getDateOfBirth();
                selectedPersonLabel.setText(p.getFirstName().toUpperCase() + " " + p.getLastName().toUpperCase());
                firstNameField.setText(p.getFirstName());
                lastNameField.setText(p.getLastName());
                govIDField.setText(p.getGovernmentID());
                studentIDField.setText(p.getStudentID());
                yearDropdown.setSelectedItem(dob.getYear());
                monthDropdown.setSelectedIndex(dob.getMonthNumber() - 1);
                dayDropdown.setSelectedIndex(dob.getDayofMonth() - 1);
                editPersonButton.setEnabled(true);
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
        else {
            controller.startEditingPerson((Person)personDropdown.getSelectedItem());
        }
        
        personDropdown.setEditable(false);
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        govIDField.setEditable(true);
        studentIDField.setEditable(true);
        dayDropdown.setEnabled(true);
        monthDropdown.setEnabled(true);
        yearDropdown.setEnabled(true);
        
        fileMenu_new.setEnabled(false);
        fileMenu_open.setEnabled(false);
        fileMenu_save.setEnabled(false);
        fileMenu_saveAs.setEnabled(false);
        
        newPersonButton.setEnabled(false);
        editPersonButton.setEnabled(false);
        deletePersonButton.setEnabled(false);
        storePersonButton.setEnabled(true);
    }
    
    private void closeInputFields() { // Closes all the input fields when Person object creation is finished
        personDropdown.setEditable(true);
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        govIDField.setEditable(false);
        studentIDField.setEditable(false);
        dayDropdown.setEnabled(false);
        monthDropdown.setEnabled(false);
        yearDropdown.setEnabled(false);
        
        fileMenu_new.setEnabled(true);
        fileMenu_open.setEnabled(true);
        fileMenu_save.setEnabled(true);
        fileMenu_saveAs.setEnabled(true);
        
        newPersonButton.setEnabled(true);
        deletePersonButton.setEnabled(true);
        storePersonButton.setEnabled(false);
        if (personDropdown.getSelectedIndex() != -1)
            editPersonButton.setEnabled(true);
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
        if (result == 0) {
            refreshPersonComboBox();
            personDropdown.setSelectedIndex(-1);
        }
    }
    
    private void exitProgram() { // Checks for unsaved data and then closes the frame and the program
        if (controller.checkUnsavedData()) {
            this.dispose();
            System.exit(0);
        }
    }
    
    private void showHelpDialog() { // Shows the user a JOptionPane to explain how to operate the program
        String helpText = "<html><head><title>Help</title></head><body>"
                + "<h3>Viewing Records</h3><ul><li>The combo box at the top of the window displays all currently loaded records.</li>"
                + "<li>Select a person from the list to view their information in the fields below.</li></ul>"
                + "<h3>Creating a New Person</h3><ol><li>Click <b>New Person</b>.</li>"
                + "<li>Enter the person's information in the editable fields.</li>"
                + "<li>Click <b>Store Person</b> to save the new record.</li>"
                + "<li>Click <b>Cancel Editing</b> to discard changes.</li></ol>"
                + "<h3>Editing an Existing Person</h3><ol><li>Select a person from the combo box.</li>"
                + "<li>Click <b>Edit Person</b>.</li>"
                + "<li>Modify the desired information.</li>"
                + "<li>Click <b>Store Person</b> to save the changes.</li>"
                + "<li>Click <b>Cancel Editing</b> to cancel without saving.</li></ol>"
                + "<h3>Deleting a Person</h3><ol><li>Select the person you want to remove.</li>"
                + "<li>Click <b>Delete Person</b>.</li>"
                + "<li>Confirm the deletion if prompted.</li></ol>"
                + "<h3>File Operations</h3><p>Use the <b>File</b> menu to:</p>"
                + "<ul><li>Create a new file</li><li>Open an existing file</li><li>Save the current list of people</li>"
                + "<li>Save data under a new file name</li><li>Exit the program</li></ul></body></html>";
        JOptionPane.showMessageDialog(this, helpText, "Help", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void showAboutDialog() { // Shows the user a JOptionPane to tell the user about the progam
        String aboutText = "<html><head><title>About Person GUI</title></head><body><h2>About Person GUI</h2><p>"
                + "Person GUI is a program designed to manage objects within the Person<br>"
                + "class hierarchy. Its implementation includes the use of Person,<br>"
                + "RegisteredPerson, and OCCCPerson objects that utilize OCCCDate<br>"
                + "objects as well as OCCCDateExceptions. Users can manage files<br>"
                + "holding a list of Person objects to demonstrate the class hierarchy's<br>"
                + "functionality.</p><h3>Credits</h3><ul>"
                + "<li><b>Jamie Whitmarsh</b> - GUI design and implementation</li>"
                + "<li><b>Veronica Edwards</b> - Person and OCCCDate hierarchy implementation</li>"
                + "<li><b>Makayla Wood</b> - Program logic and operations</li>"
                + "</ul></body></html>";
        JOptionPane.showMessageDialog(this, aboutText, "About", JOptionPane.PLAIN_MESSAGE);
    }

}

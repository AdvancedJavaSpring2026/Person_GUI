import java.awt.*;
import java.awt.event.*;
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




    public GUI(){
        super("Temporary Title");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

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
        //addActionListeners();
        this.pack();
        setSize(WIDTH, HEIGHT);

        setResizable(false);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void addActionListeners(){
        newPersonButton.addActionListener(this);
        editPersonButton.addActionListener(this);
        deletePersonButton.addActionListener(this);
        storePersonButton.addActionListener(this);
        personDropdown.addActionListener(this);
        monthDropdown.addActionListener(this);
        dayDropdown.addActionListener(this);
        yearDropdown.addActionListener(this);

    }



    private void setGUILayout(){
        setLayout(new GridLayout(1, 1));

        fullScreenPanel = new JPanel();
        topPanel = new JPanel();
        middlePanelTop = new JPanel();
        middlePanelBottom = new JPanel();
        bottomPanel = new JPanel();

        personDropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        personButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectedPersonPanel = new JPanel(new BorderLayout());
        personInfoPanel = new JPanel(new GridLayout(5,1));
        personTextFieldsPanel = new JPanel(new GridLayout(4,1));
        dateDropdownsPanel = new JPanel(new BorderLayout());

        fullScreenPanel.setBackground(colorOrangeLight);
        topPanel.setBackground(colorRedGentle);
        middlePanelTop.setBackground(colorOrangeDark);
        middlePanelBottom.setBackground(colorYellow);
        bottomPanel.setBackground(colorPurpleDark);

        topPanel.setPreferredSize(new Dimension(WIDTH, 200));
        middlePanelTop.setPreferredSize(new Dimension(WIDTH, 200));
        middlePanelBottom.setPreferredSize(new Dimension(WIDTH, 700));
        bottomPanel.setPreferredSize(new Dimension(WIDTH, 200));

        personDropdownPanel.setPreferredSize(new Dimension(100, 50));
        personButtonPanel.setPreferredSize(new Dimension(100, 100));
        selectedPersonPanel.setPreferredSize(new Dimension(200, 200));
        personInfoPanel.setPreferredSize(new Dimension(200, 200));
        personTextFieldsPanel.setPreferredSize(new Dimension(200, 200));
        dateDropdownsPanel.setPreferredSize(new Dimension(100, 100));

        topPanel.setLayout(new GridLayout(1,2));


        //Top left Panel holding Person Dropdown Box
        personDropdown = new JComboBox<>();


        //Top right Panel holding Person buttons
        personButtonPanel.setLayout(new GridLayout(1, 3));

        newPersonButton = new JButton("New Person");
        editPersonButton = new JButton("Edit Person");
        deletePersonButton = new JButton("Delete Person");

        personDropdownPanel.add(personDropdown);

        personButtonPanel.add(newPersonButton);
        personButtonPanel.add(editPersonButton);
        personButtonPanel.add(deletePersonButton);

        topPanel.add(personDropdownPanel);
        topPanel.add(personButtonPanel);

        middlePanelTop.setLayout(new FlowLayout(FlowLayout.CENTER));

        selectedPersonPanel.add(selectedPersonLabel);

        middlePanelTop.add(selectedPersonPanel);


        middlePanelBottom.setLayout(new GridLayout(1,2));

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



        topPanel.setLayout(new GridLayout(1,2));
        topPanel.add(personDropdownPanel);
        topPanel.add(personButtonPanel);

        middlePanelTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        middlePanelTop.add(selectedPersonPanel);

        middlePanelBottom.setLayout(new GridLayout(1,2));
        middlePanelBottom.add(personInfoPanel);
        middlePanelBottom.add(personTextFieldsPanel);
        middlePanelBottom.add(dateDropdownsPanel);


        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
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
        fileMenu_new.addActionListener(this);
        fileMenu_new.setMnemonic(KeyEvent.VK_N);

        fileMenu_open = new JMenuItem("Open...");
        fileMenu_open.addActionListener(this);
        fileMenu_open.setMnemonic(KeyEvent.VK_O);

        fileMenu_save = new JMenuItem("Save");
        fileMenu_save.addActionListener(this);
        fileMenu_save.setMnemonic(KeyEvent.VK_V);

        fileMenu_saveAs = new JMenuItem("Save As...");
        fileMenu_saveAs.addActionListener(this);
        fileMenu_saveAs.setMnemonic(KeyEvent.VK_A);

        fileMenu_exit = new JMenuItem("Exit");
        fileMenu_exit.addActionListener(this);
        fileMenu_exit.setMnemonic(KeyEvent.VK_X);

        helpMenu_help = new JMenuItem("Help");
        helpMenu_help.addActionListener(this);
        helpMenu_help.setMnemonic(KeyEvent.VK_P);

        helpMenu_about = new JMenuItem("About");
        helpMenu_about.addActionListener(this);
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



}

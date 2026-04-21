import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener{

    //NOTE FROM JAMIE - WE CAN MOVE ANY OR ALL ACTIONLISTENER THINGS OUT OF HERE IF NEEDED


    private static final int WIDTH = 1024;
    private static final int HEIGHT = 1024;

    //Menu components
    JMenuItem fileMenu_new;
    JMenuItem fileMenu_open;
    JMenuItem fileMenu_save;
    JMenuItem fileMenu_saveAs;
    JMenuItem fileMenu_exit;

    JMenuItem helpMenu_help;
    JMenuItem helpMenu_about;

    //Layout components
    JPanel jPanFullApp, jPanPersonListArea, jPanPersonButtonArea, jPanTextFields, jPanBottomArea, jPanDropdowns, jPanTopArea;
    GridBagLayout gridBagLayout;
    GridBagConstraints topPanConstraints, bottomPanConstraints;

    //Components that do things
    JButton newPersonButton, editPersonButton, deletePersonButton, storePersonButton;
    //JComboBox<Person> personDropdown;
    JComboBox<String> monthDropdown;
    JComboBox<Integer> dayDropdown;
    JComboBox<Integer> yearDropdown;

    //Data (this may wind up being in Victoria's work, and deleted here)
    String firstName, lastName, studentID, govID;

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
        addActionListeners();
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void addActionListeners(){
        newPersonButton.addActionListener(this);
        newPersonButton.addActionListener(this);
        newPersonButton.addActionListener(this);
        newPersonButton.addActionListener(this);

    }
    private void setGUILayout(){

        jPanFullApp = new JPanel();

        jPanPersonListArea = new JPanel();


        jPanPersonButtonArea = new JPanel();
        jPanPersonButtonArea.setLayout(new GridLayout(1, 3));
        newPersonButton = new JButton("New Person");
        editPersonButton = new JButton("Edit Person");
        deletePersonButton = new JButton("Delete Person");

        jPanPersonButtonArea.add(newPersonButton);
        jPanPersonButtonArea.add(newPersonButton);
        jPanPersonButtonArea.add(newPersonButton);


        jPanTextFields = new JPanel();


        jPanDropdowns = new JPanel();

        jPanTopArea = new JPanel();
        jPanBottomArea = new JPanel();

        storePersonButton = new JButton("Store Person");

        jPanBottomArea.add(storePersonButton);

        topPanConstraints = new GridBagConstraints();
        bottomPanConstraints = new GridBagConstraints();

        topPanConstraints.gridx = 0;
        topPanConstraints.gridy = 0;
        topPanConstraints.fill = GridBagConstraints.HORIZONTAL;
        topPanConstraints.anchor = GridBagConstraints.NORTH;

        bottomPanConstraints.gridx = 0;
        bottomPanConstraints.gridy = 0;
        bottomPanConstraints.fill = GridBagConstraints.HORIZONTAL;
        bottomPanConstraints.anchor = GridBagConstraints.CENTER;

        jPanTopArea.add(jPanPersonButtonArea);

        jPanFullApp.setLayout(gridBagLayout);



        jPanFullApp.add(jPanTopArea, topPanConstraints);
        jPanFullApp.add(jPanBottomArea, bottomPanConstraints);
        add(jPanFullApp);


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

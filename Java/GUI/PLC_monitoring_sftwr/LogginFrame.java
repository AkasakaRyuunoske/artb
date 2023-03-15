package Prototype;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class LogginFrame extends JFrame implements ActionListener {
    //Global parts of the main screen, must be global to be seen from different parts of the program.
    JFrame            logginFrame;        //is main window of the program.
    JButton connectButton;   //this button is needed to send the data from textfield. It sends Database url, username and password using ActionListener.
    // Labels is needed to specify what data to put into textfields.
    JLabel            userLabel;
    JLabel            passwordLabel;
    //textfield is used to gather input data from user.
    JTextField        userField;
    JTextField        passwordField;

    ImageIcon imageIcon = new ImageIcon("C:\\robochiy stol\\Logo-ETM1.jpg");

    public LogginFrame() {
        //Create Table Button settings.
        connectButton = new JButton("Connettersi");
        connectButton.setBounds(220,240,180,80);
        connectButton.setFont(new Font("MV Bolly",Font.BOLD,20));
        connectButton.setFocusable(false);
        connectButton.addActionListener(this);
        //First line asking to put the user name.
        userLabel = new JLabel();
        userLabel.setText("User Name");
        userLabel.setFont(new Font("MV Bolly",Font.BOLD,20));
        userLabel.setBounds(210,10,150,100);
        //textfield to gather user name.
        userField = new JTextField(user);
        userField.setFont(new Font("MV Bolly",Font.BOLD,20));
        userField.setBounds(210,70,200,45);

        //Second line asking to put the password of chosen user.
        passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        passwordLabel.setFont(new Font("MV Bolly",Font.BOLD,20));
        passwordLabel.setBounds(210,100,150,100);
        //textfield to gather password.
        passwordField = new JPasswordField(password);
        passwordField.setFont(new Font("MV Bolly",Font.BOLD,20));
        passwordField.setBounds(210,160,200,45);


        //Main window configuration.
        logginFrame = new JFrame();                                           //Creating the frame object.
        logginFrame.setTitle("ETM Data Base Connector");                      //Setting title.
        logginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  //Setting what to do when the X is pressed. Is set to close everything that belongs to this frame.
        logginFrame.setSize(620,700);                             //Setting size of main window.
        logginFrame.setResizable(true);                                       //Leaving the opportunity to window to be resizable.
        logginFrame.setFocusable(true);                                       //Leaving the opportunity to window to be focusable.
        logginFrame.setLocationRelativeTo(null);                              //Setting starting location of the window in the middle of the screen.
        logginFrame.setLayout(null);                                          //Setting Null Layout to configure components manually.
        logginFrame.setIconImage(imageIcon.getImage());
        //Adding all the components.
        logginFrame.add(userLabel);
        logginFrame.add(passwordLabel);
        logginFrame.add(connectButton);
        logginFrame.add(userField);
        logginFrame.add(passwordField);
        logginFrame.setVisible(true);                                         //Always must be last otherwise the components will not be shown correctly.
    }

    String url      = "---------";                     //Database specific url.
    String user     = "---------";                                            //User name.
    String password = "---------";                                    //Password of chosen user.


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton) {
            //Action listener from button, at moment when is clicked send all data from textfields to Data Base.
            user = userField.getText();
            password = passwordField.getText();
           connectButton.setVisible(false);

            try (Connection connection = DriverManager.getConnection(url, user, password)) {                             //DriverManager tries to connect to Data Base using data inputted by user.
                System.out.println("Connected successfully");                                                            //If is connected prints this to console.
                try (Statement statement = connection.createStatement()) {                                              //When connected creates SQL request.
                    String sql = "Use Cosma " + "select TOP 1 * from PS4 ";                                                              //SQL request that will be used.
                    try (ResultSet ignored = statement.executeQuery(sql)) {                                              //Tries to execute created before sql request.
                        new MainFrame();
                        connection.close();
                        logginFrame.setVisible(false);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                connectButton.setVisible(true);
                JOptionPane.showMessageDialog(null, "Il password o User Name sono sbagliati, riprova.", "Alert", JOptionPane.WARNING_MESSAGE);  //if something goes wrong this message will appear.
            }

        }
    }
}

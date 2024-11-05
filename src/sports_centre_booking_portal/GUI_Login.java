/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author yuantsai
 */
public class GUI_Login extends JFrame {

    private Page_Login pageLogin; // link a Page_Login instance upon initialisation

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
    private JButton registerButton;
    private JButton quitButton;

    public GUI_Login(Page_Login pageLogin) {
        this.pageLogin = pageLogin;
        setUpGUI();
    }

    private void setUpGUI() {

        setTitle("Sports Centre Booking Portal - Login Page");
        setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // GUI components set up
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        //username text field
        panel.add(new JLabel("Username"));
        usernameField = new JTextField();
        panel.add(usernameField);

        //password text field
        panel.add(new JLabel("Password"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        //buttons
        loginButton = new JButton("Login");
        panel.add(loginButton);
        resetButton = new JButton("Cancel");
        panel.add(resetButton);
        registerButton = new JButton("Register");
        panel.add(registerButton);
        quitButton = new JButton("Quit");
        panel.add(quitButton);

        add(panel);

        // Action listeners set up
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReset();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleRegister();
                } catch (IOException ex) {
                    Logger.getLogger(GUI_Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleQuit();
            }
        });

    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean loginSuccess = pageLogin.Login(username, password);

        if (!loginSuccess) {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        } else {
            pageLogin.transitionHome();
            handleReset();
        }

    }
    
    private void handleTransitionToHome(){
        pageLogin.transitionHome();
    }

    private void handleQuit() {
        pageLogin.Quit();
    }

    private void handleReset() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private void handleRegister() throws IOException {
        JTextField newUsernameField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();

        Object[] registrationFields = {
            "New Username:", newUsernameField,
            "First Name:", firstNameField,
            "Last Name:", lastNameField,
            "New Password:", newPasswordField
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                registrationFields,
                "Register New Account",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            String newUsername = newUsernameField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String newPassword = new String(newPasswordField.getPassword());

            if (pageLogin.Register(newUsername, firstName, lastName, newPassword)) {
                JOptionPane.showMessageDialog(this, "Registration Successful");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists");
            }

        }
    }
}

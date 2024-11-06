/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.io.IOException;

/**
 *
 * @author yuant
 */
public class Page_Login extends Page {

    private GUI_Login gui;
    private User userToLogin;
    //private boolean transitionToHome;
    private PageNavigator pageNav;

    public Page_Login(PageNavigator pageNav) {
        super();
        this.pageNav = pageNav;
        this.gui = new GUI_Login(this);
    }

    public void main() throws IOException {
        //transitionToHome = false;
        userToLogin = null;

        // show GUI upon login page activation.
        gui.setVisible(true);

        /*
        while (gui.isVisible()) {
            if (userToLogin != null) {
                //gui.setVisible(false);
                return userToLogin;
            }
        }

        if (transitionToHome) {
            return userToLogin;
        }

        // if GUI closed without loggin in
        return null;
        */
    }

    public void transitionHome() {
        //gui.setVisible(false);
        //transitionToHome = true;
        gui.dispose(); // close the login GUI
        pageNav.showHomePage(userToLogin);
    }

    public void Quit() {
        // gui.setVisible(false);

        gui.dispose();
        System.exit(0);
    }
    
    public User getUserToLogin(){
        return userToLogin;
    }

    /*
    public boolean getExitFlag(){
        return exit;
    }
     */
    public boolean Login(String username, String password) {

        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                userToLogin = user;
                return true;
            }
        }

        return false;
    }

    public boolean Register(String newUsername, String firstName, String lastName, String newPassword) throws IOException {

        // check if the username already exists
        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(newUsername)) {
                return false;
            }
        }

        // if username does not already exist, register new user
        User newUser = new User(newUsername, firstName, lastName, newPassword);
        usersList.add(newUser);
        dbManager.saveUsers(usersList); //add new user into users.txt

        return true;
    }
}

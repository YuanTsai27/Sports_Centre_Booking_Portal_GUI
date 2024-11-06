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

    private final GUI_Login gui;
    //private boolean transitionToHome;
    private final PageNavigator pageNav;

    public Page_Login(PageNavigator pageNav) {
        super();
        this.pageNav = pageNav;
        this.gui = new GUI_Login(this);
    }

    public void main() throws IOException {
        gui.setVisible(true);
    }

    public void transitionHome(User userToLogin) {
        gui.dispose(); // close the login GUI
        pageNav.showHomePage(userToLogin);
    }

    public void Quit() {
        gui.dispose();
        AppQuitter.exitApplication();
    }
    
     public User Login(String username, String password) {

        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
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

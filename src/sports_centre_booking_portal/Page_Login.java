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

    private CUI_Login cui;

    public Page_Login() {
        super();
        this.cui = new CUI_Login();
    }

    public User main() throws IOException {
        cui.pageWelcome();
        String response;

        // loop to keep prompting the user
        while (true) {
            response = cui.askLogin();

            if (response.equalsIgnoreCase("x")) {
                cui.exitApp(); // display final message before exiting
                return null;
            } else if (response.equalsIgnoreCase("register")) {
                Register();
            } else {
                User user = Login(response);
                if (user != null) {
                    return user;
                }
            }

        }
    }

    private User Login(String username) {
        String password = cui.askPassword(username);

        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                cui.loginSuccess();
                return user;
            }
        }

        cui.loginFailure();
        return null;
    }

    private void Register() throws IOException {

        boolean usernameExists = false;

        while (true) {  // loop to prompt continuously for a new unique username
            String newUsername = cui.registerUsername();

            if (newUsername.equalsIgnoreCase("x")) {
                break;  // exit back to login page
            }

            for (User user : usersList) {
                if (user.getUsername().equalsIgnoreCase(newUsername)) {
                    usernameExists = true;
                    break;
                }
            }

            if (usernameExists) {
                cui.registrationFailure();
            } else {
                String firstName = cui.registerFirstName();
                String lastName = cui.registerLastName();
                String newPassword = cui.registerPassword();
                
                User newUser = new User(newUsername, firstName, lastName, newPassword);
                usersList.add(newUser);
                fileManager.saveUsers(usersList); //add new user into users.txt
                cui.registrationSuccess();
                break; // exit register loop after successful registration
            }
        }
    }
}

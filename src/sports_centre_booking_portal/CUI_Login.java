/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.util.Scanner;

/**
 *
 * @author yuant
 */
public class CUI_Login extends CUI {

    @Override
    public void pageWelcome() {

        System.out.println("***************************************************");
        System.out.println("*****   Sports Centre Booking Portal Login    *****");
        System.out.println("***************************************************");
        System.out.println(
                "To exit the portal app, type 'X'");
    }

    public String askLogin() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Please enter a username to login or type 'register' to create a new account:");
        return scan.nextLine();
    }

    public String askPassword(String username) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Please enter the password for " + username + ":");
        return scan.nextLine();
    }

    public String registerUsername() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Type a username to register with (case insensitive) or type 'x' to return to the Login page:");
        return scan.nextLine();
    }

    public String registerPassword() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Please enter a password for your new account:");
        return scan.nextLine();
    }

    public String registerFirstName() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Please enter your first name:");
        return scan.nextLine();
    }

    public String registerLastName() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Please enter your last name:");
        return scan.nextLine();
    }

    public void invalidInput() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Invalid input, please try again.");
    }

    public void registrationSuccess() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Registration successful! You can now log in with your new credentials.");
    }

    public void registrationFailure() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Registration failed. Username already exists. Please choose a different username or type 'x' to return to the Login page");
    }

    public void loginSuccess() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Login successful!");
    }

    public void loginFailure() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Login failed. Incorrect username or password. Please try again.");
    }

    public void exitApp() {
        System.out.println("**********************************************************");
        System.out.println("*                                                        *");
        System.out.println("*  Thank you for using the Sports Centre Booking Portal! *");
        System.out.println("*                                                        *");
        System.out.println("**********************************************************");

    }

}

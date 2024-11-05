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
public class PageNavigator {
    // class that exclusively handles transitioning the user's interface from one page to another (login page, home page, booking page)

    private User currentUser;
    private Page_Booking bookingPage;
    private Page_Home homePage;
    private Page_Login loginPage;
    private boolean exit;

    public PageNavigator() {
        this.bookingPage = new Page_Booking(this);
        this.homePage = new Page_Home(this);
        this.loginPage = new Page_Login(this);
        currentUser = null;
        this.exit = false;
    }

    public void start() throws IOException {

        /*
        User currentUser = null;

        while (!exit) {
            if (currentUser == null) {
                // always on login page when there's no logged in user.
                currentUser = loginPage.main();
                if (currentUser == null) {
                    exit = true; // user exits the login page.
                }

            } else {
                // always on home page when the user is logged in
                String response = homePage.main(currentUser);

                if (response.equalsIgnoreCase("x")) {
                    currentUser = null; // logs user out
                } else if (response.equalsIgnoreCase("book")) {
                    bookingPage.main(currentUser);
                }
            }
        }
*/
        showLoginPage();
        

    }
    
    public void showLoginPage() throws IOException{
        currentUser = null;
        loginPage.main();
    }
    
    public void showHomePage(User user){
        currentUser = user;
        homePage.main(currentUser);
    }
    
    public void showBookingPage(User user){
        currentUser = user;
        bookingPage.main(currentUser);
    }

}

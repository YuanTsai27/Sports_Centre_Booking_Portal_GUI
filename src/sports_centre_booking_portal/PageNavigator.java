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
public class PageNavigator extends Page {
    // class that exclusively handles transitioning the user's interface from one page to another (login page, home page, booking page)

    private static PageNavigator instance; // Singleton instance
    
    private Page_Booking bookingPage;
    private Page_Home homePage;
    private Page_Login loginPage;

    private PageNavigator() {
        super();
        this.bookingPage = new Page_Booking(this);
        this.homePage = new Page_Home(this);
        this.loginPage = new Page_Login(this);
    }
    
    // Static method to get the single instance of PageNavigator (Singleton Factory)
    public static synchronized PageNavigator getInstance() {
        if (instance == null) {
            instance = new PageNavigator();
        }
        return instance;
    }

    public void start() throws IOException {
        showLoginPage();

    }

    public void showLoginPage() throws IOException {
        currentUser = null;
        loginPage.main();
    }

    public void showHomePage(User user) {
        currentUser = user;
        homePage.main(currentUser);
    }

    public void showBookingPage(User user) {
        currentUser = user;
        bookingPage.main(currentUser);
    }
}

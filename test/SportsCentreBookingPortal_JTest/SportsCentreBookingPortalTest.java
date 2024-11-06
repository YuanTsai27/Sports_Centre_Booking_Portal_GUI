/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SportsCentreBookingPortal_JTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import sports_centre_booking_portal.Booking;
import sports_centre_booking_portal.Court;
import sports_centre_booking_portal.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import sports_centre_booking_portal.DBManager;

import sports_centre_booking_portal.Page;
import sports_centre_booking_portal.PageNavigator;
import sports_centre_booking_portal.Page_Booking;
import sports_centre_booking_portal.Page_Login;
import sports_centre_booking_portal.UserAdmin;
import sports_centre_booking_portal.UserStaff;

/**
 *
 * @author yuantsai
 */
public class SportsCentreBookingPortalTest {
    private User user;
    private Court court;
    private Booking booking;
    private ArrayList<User> usersList;
    private ArrayList<Court> courtsList;
    private ArrayList<Booking> bookingsList;
    
    @Before
    public void setUp() {

        usersList = new ArrayList<>();
        courtsList = new ArrayList<>();
        bookingsList = new ArrayList<>();

        // creating sample user
        user = new User("testuser", "Test", "User", "password");
        usersList.add(user);

        // creating sample court
        court = new Court(1, 50.0);
        courtsList.add(court);

        // creating sample booking
        HashSet<Court> courtsBooked = new HashSet<>();
        courtsBooked.add(court);
        booking = new Booking(user.getId(), user, 900, 1100, 100.0, courtsBooked);
        bookingsList.add(booking);
        user.addBooking(booking);

        // setting static lists in Page class
        Page.setArrayLists(usersList, courtsList, bookingsList);
        
        // initialize dbManager and set it in the Page class
        DBManager dbManager = DBManager.getInstance();
        Page.setDBManager(dbManager);
    }
    
    @After
    public void tearDown() {
        // Clean up after each test
        usersList.clear();
        courtsList.clear();
        bookingsList.clear();
    }
    
    @Test
    public void testUserRegistration() {
        User newUser = new User("newuser", "New", "User", "newpassword");
        Assert.assertNotNull(newUser);
        Assert.assertEquals("newuser", newUser.getUsername());
        Assert.assertEquals("New", newUser.getFirstName());
        Assert.assertEquals("User", newUser.getLastName());
        Assert.assertEquals("newpassword", newUser.getPassword());
        Assert.assertEquals(0.0, newUser.getAccBalance(), 0.0);
        Assert.assertEquals(User.UserType.MEMBER, newUser.getType());
    }
    

    @Test
    public void testUserTopUpBalance() {
        user.topUpBalance(100.0);
        Assert.assertEquals(100.0, user.getAccBalance(), 0.0);
    }

    @Test
    public void testUserBookCourt() {
        double bookingCost = 50.0;
        user.topUpBalance(100.0);
        boolean success = user.bookCourt(bookingCost);
        Assert.assertTrue(success);
        Assert.assertEquals(50.0, user.getAccBalance(), 0.0);
    }

    @Test
    public void testUserBookCourtInsufficientFunds() {
        double bookingCost = 150.0;
        user.topUpBalance(100.0);
        boolean success = user.bookCourt(bookingCost);
        Assert.assertFalse(success);
        Assert.assertEquals(100.0, user.getAccBalance(), 0.0);
    }

    @Test
    public void testUserAddBooking() {
        Assert.assertEquals(1, user.getOwnBookingsList().size());
        Booking newBooking = new Booking(user.getId(), user, 1300, 1500, 100.0, new HashSet<>(courtsList));
        user.addBooking(newBooking);
        Assert.assertEquals(2, user.getOwnBookingsList().size());
    }

    @Test
    public void testCourtCreation() {
        Court newCourt = new Court(2, 75.0);
        Assert.assertNotNull(newCourt);
        Assert.assertEquals(2, newCourt.getCourtNum());
        Assert.assertEquals(75.0, newCourt.getBasePrice(), 0.0);
    }

    @Test
    public void testSetBasePrice() {
        court.setBasePrice(60.0);
        Assert.assertEquals(60.0, court.getBasePrice(), 0.0);
    }

    @Test
    public void testBookingCreation() {
        HashSet<Court> courtsBooked = new HashSet<>();
        courtsBooked.add(court);
        Booking newBooking = new Booking(user.getId(), user, 1200, 1400, 100.0, courtsBooked);
        Assert.assertNotNull(newBooking);
        Assert.assertEquals(user.getId(), newBooking.getUserID());
        Assert.assertEquals(user, newBooking.getUser());
        Assert.assertEquals(1200, newBooking.getStartTime());
        Assert.assertEquals(1400, newBooking.getEndTime());
        Assert.assertEquals(100.0, newBooking.getPrice(), 0.0);
    }

    @Test
    public void testBookingDurationCalculation() {
        Assert.assertEquals(120, booking.getDuration());
    }

    @Test
    public void testBookingAddCourt() {
        Court newCourt = new Court(2, 75.0);
        booking.addCourt(newCourt);
        Assert.assertTrue(booking.getCourtsBookedSet().contains(newCourt));
        Assert.assertEquals(2, booking.getCourtsBookedSet().size());
    }


    @Test
    public void testStaffBookingDiscount() {
        User staff = new UserStaff("staffuser", 5, "Staff", "User", "pass", 100.0);
        double bookingCost = 50.0;
        boolean success = staff.bookCourt(bookingCost);
        Assert.assertTrue(success);
        Assert.assertEquals(75.0, staff.getAccBalance(), 0.0);
    }

    @Test
    public void testAdminBookingFree() {
        User admin = new UserAdmin("adminuser", 6, "Admin", "User", "pass", 100.0);
        double bookingCost = 50.0;
        boolean success = admin.bookCourt(bookingCost);
        Assert.assertTrue(success);
        Assert.assertEquals(100.0, admin.getAccBalance(), 0.0);
    }

    @Test
    public void testCourtAvailability() {
        Page_Booking pageBooking = new Page_Booking(PageNavigator.getInstance());
        pageBooking.setCurrentUser(user);

        // Booking already exists from 900 to 1100
        // Try to book the same court from 1000 to 1200
        HashSet<Court> selectedCourts = new HashSet<>();
        selectedCourts.add(court);

        boolean isAvailable = pageBooking.isCourtAvailable(court, 1000, 1200);
        Assert.assertFalse(isAvailable);

        // Try to book the same court from 1100 to 1300
        isAvailable = pageBooking.isCourtAvailable(court, 1100, 1300);
        Assert.assertTrue(isAvailable);
    }

    @Test
    public void testValidTime() {
        Page_Booking pageBooking = new Page_Booking(PageNavigator.getInstance());
        Assert.assertTrue(pageBooking.isValidTime(600, 2200));
        Assert.assertFalse(pageBooking.isValidTime(500, 2300));
    }

    @Test
    public void testValidDuration() {
        Page_Booking pageBooking = new Page_Booking(PageNavigator.getInstance());
        Assert.assertTrue(pageBooking.isValidDuration(900, 1000));
        Assert.assertFalse(pageBooking.isValidDuration(900, 950));
        Assert.assertFalse(pageBooking.isValidDuration(1000, 900));
    }

    @Test
    public void testUserLoginSuccess() throws IOException {
        Page_Login pageLogin = new Page_Login(PageNavigator.getInstance());
        User loggedInUser = pageLogin.Login("testuser", "password");
        Assert.assertNotNull(loggedInUser);
        Assert.assertEquals("testuser", loggedInUser.getUsername());
    }

    @Test
    public void testUserLoginFailure() throws IOException {
        Page_Login pageLogin = new Page_Login(PageNavigator.getInstance());
        User loggedInUser = pageLogin.Login("testuser", "wrongpassword");
        Assert.assertNull(loggedInUser);
    }

    @Test
    public void testUserRegistrationSuccess() throws IOException{
        Page_Login pageLogin = new Page_Login(PageNavigator.getInstance());
        boolean success = pageLogin.Register("newuser", "New", "User", "newpassword");
        Assert.assertTrue(success);
        Assert.assertEquals(2, usersList.size());
    }

    @Test
    public void testUserRegistrationDuplicateUsername() throws IOException {
        Page_Login pageLogin = new Page_Login(PageNavigator.getInstance());
        boolean success = pageLogin.Register("testuser", "Test", "User", "password");
        Assert.assertFalse(success);
        Assert.assertEquals(1, usersList.size());
    }
}

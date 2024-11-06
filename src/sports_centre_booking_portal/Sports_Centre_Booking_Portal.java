/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sports_centre_booking_portal;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author yuantsai
 */
public class Sports_Centre_Booking_Portal {

    private DBManager dbManager;
    private PageNavigator pageNavigator;

    private ArrayList<User> usersList; // using arraylist for its ordered characteristic
    private ArrayList<Court> courtsList;
    private ArrayList<Booking> bookingsList;

    private boolean exit;

    public Sports_Centre_Booking_Portal() {
        this.dbManager = new DBManager();
        this.pageNavigator = new PageNavigator();

        this.usersList = new ArrayList<>();
        this.courtsList = new ArrayList<>();
        this.bookingsList = new ArrayList<>();

        exit = false;

    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Sports_Centre_Booking_Portal app = new Sports_Centre_Booking_Portal();
                    app.start();
                } catch (IOException e) {
                }
            }
        });
    }

    public void start() throws IOException {
        // load all saved users, booked courts and bookings into the app
        dbManager.loadUsers(usersList);
        dbManager.loadCourts(courtsList);
        dbManager.loadBookings(bookingsList, courtsList, usersList);

        // set static instances for Page class.
        Page.setArrayLists(usersList, courtsList, bookingsList);
        Page.setDBManager(dbManager);

        // start the app's page navigator.
        pageNavigator.start();

        /*
        // save users, booked courts, and bookings before exiting app.
        dbManager.saveBookings(bookingsList);
        dbManager.saveCourts(courtsList);
        dbManager.saveUsers(usersList);
*/

    }

}

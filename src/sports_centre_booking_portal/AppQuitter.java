/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.io.IOException;
/**
 *
 * @author yuantsai
 */
public class AppQuitter {
    //class that exclusively handles the quitting of the application smoothly
    
    private AppQuitter(){
    
    }
    
    public static void exitApplication() {
        try {
            //singleton instance of dbManager
            DBManager dbManager = DBManager.getInstance();
            
            // save data before exiting
            dbManager.saveBookings(Page.getBookingsList());
            dbManager.saveCourts(Page.getCourtsList());
            dbManager.saveUsers(Page.getUsersList());
            
            dbManager.closeConnection();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    
}

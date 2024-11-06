/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.util.ArrayList;

/**
 *
 * @author yuant
 */
public class Page {
    
    protected static ArrayList<User> usersList; 
    protected static ArrayList<Court> courtsList;
    protected static ArrayList<Booking> bookingsList;
            
    protected User currentUser = null;
    protected static DBManager dbManager;
    
    // times in 24 hour format
    protected static int openTime; 
    protected static int closeTime;
    protected static int minDuration; 
    protected static int minTimeIncrement; 
    
    public Page (){   
        this.currentUser = null;
        Page.openTime = 600; // 6:00am
        Page.closeTime = 2200; // 10pm
        Page.minDuration = 100; // 1 hr
        Page.minTimeIncrement = 100; //1 hour min increment, e.g cannot book from 1pm to 2:30pm
        
        
    }
    
    public void setCurrentUser(User user){
        this.currentUser = user;
    }
    
    public static void setArrayLists(ArrayList<User> usersList, ArrayList<Court> courtsList, ArrayList<Booking> bookingsList){
        Page.usersList = usersList;
        Page.courtsList = courtsList;
        Page.bookingsList = bookingsList;
    }
    
    public static ArrayList<User> getUsersList() {
        return usersList;
    }

    public static ArrayList<Court> getCourtsList() {
        return courtsList;
    }

    public static ArrayList<Booking> getBookingsList() {
        return bookingsList;
    }
    
    public static void setDBManager(DBManager dbManager){
        Page.dbManager = dbManager;
    }
   
}

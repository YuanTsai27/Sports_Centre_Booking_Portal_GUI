/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author yuant
 */
public class Booking {
    private static int nextBookingID = 1;
    
    private int bookingID;
    private int userID;
    private User user;
    private HashSet<Court> courtsBookedSet;
    private int startTime; // 900 = 9am, 2250 = 10:30pm
    private int duration; // in minutes
    private int endTime;
    private double price;
    
    // constructor for existing bookings with id
    public Booking(int bookingID, int userID,User user, int startTime, int endTime, double price) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.user = user;
        this.courtsBookedSet = new HashSet<>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.bookingID = getNextBookingID();
        this.setDuration();
    }
    
    public static void setStaticNextBookingID(int latestID){
        Booking.nextBookingID = latestID + 1;
    }
    
    // constructor for new bookings
    public Booking(int userID, User user, int startTime, int endTime, double price, HashSet<Court> selectedCourts) {
        this.userID = userID;
        this.user = user;
        this.courtsBookedSet = selectedCourts;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.bookingID = getNextBookingID();
        this.setDuration();
    }

    //in the make booking method, only add the booking to the booking lists once it's confirmed, ie price deducted from booker. 
    // otherwise the booking instance can be automatically deleted if not added to list. 
    
    public static int getNextBookingID() {
        int next = nextBookingID;
        nextBookingID++;
        return next;
    }
    
    public static void resetBookingID() {
        nextBookingID = 1;
    }
   

public int getUserID() {
        return userID;
    }

public User getUser(){
    return this.user;
}

    public HashSet<Court> getCourtsBookedSet() {
        return courtsBookedSet;
    }
    
    public void addCourt(Court court){
        this.courtsBookedSet.add(court);
    }


    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration() {
        // This method is written only for time increments of 30 minutes.
        // Sets duration in minutes

        int startHour = startTime / 100;
        int startMinute = startTime % 100;
        int endHour = endTime / 100;
        int endMinute = endTime % 100;
        int minuteDiff = 0;
        int hourDiff = 0;

        // check for 30 minutes diff. Else minuteDiff remains 0
        if (endMinute > startMinute) {
            minuteDiff = 30;
        } else if (endMinute < startMinute) {
            minuteDiff = -30;
        }

        // calculate hour difference in minutes of 60
        hourDiff = (endHour - startHour) * 60;

        // Adjust if endMinute is less than startMinute
        if (minuteDiff < 0) {
            hourDiff -= 60; // Move back one hour
            minuteDiff += 60; // Adjust minute difference
        }

        this.duration = hourDiff + minuteDiff;

    }

    public int getEndTime() {
        return endTime;
    }

    public double getPrice() {
        return price;
    }

    public void SetPrice(double price) {
        this.price = price;
    }

    public int getBookingID() {
        return bookingID;
    }

      @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Booking)) {
            return false;
        }

        Booking other = (Booking) o;
        
        return Objects.equals(this.bookingID, other.bookingID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingID);
    }
    
    
}


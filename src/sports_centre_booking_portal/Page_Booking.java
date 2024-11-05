/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.util.HashSet;
import javax.swing.JOptionPane;

/**
 *
 * @author yuant
 */
public class Page_Booking extends Page {

    private GUI_Booking gui;
    private User currentUser;

    public Page_Booking() {
        super();
        this.gui = new GUI_Booking(this);
        currentUser = null;
    }
    
    

    public void main(User currentUser) {
        this.currentUser = currentUser;
        
        // show GUI upon login page activation.
        gui.setVisible(true);
        
        while(gui.isVisible()){
        }
        
        // quit if GUI closed.
        return;
        
    }
    
    public void processBooking(String courts, int startTime, int endTime){
        // validate court numbers and availability
        HashSet<Court> selectedCourts = validateCourts(courts);

        if (selectedCourts != null && !selectedCourts.isEmpty()) {
            // Calculate price and update GUI
            double totalPrice = calculatePrice(selectedCourts, startTime, endTime);
            gui.updateTotalPrice(totalPrice);

            // Confirm booking
            boolean confirmed = confirmBooking(selectedCourts, startTime, endTime, totalPrice);
            if (confirmed) {
                JOptionPane.showMessageDialog(gui, "Booking Confirmed!");
            } else {
                JOptionPane.showMessageDialog(gui, "Booking Failed.");
            }
        } else {
            JOptionPane.showMessageDialog(gui, "Invalid court selection.");
        }
    }

    private HashSet<Court> validateCourts(String courts) {
        // Convert the input court string to a HashSet of Court objects (pseudo-code)
        HashSet<Court> courtSet = new HashSet<>();
        // Parse courts and validate each court, adding to courtSet if valid
        return courtSet; // Return validated set or null if invalid
    }

    private double calculatePrice(HashSet<Court> courts, int startTime, int endTime) {
        // Implement price calculation logic
        return courts.size() * (endTime - startTime) * 10; // Sample pricing logic
    }

    private boolean confirmBooking(HashSet<Court> courts, int startTime, int endTime, double price) {
        // Confirm booking and update system records
        return true; // Placeholder confirmation
    }
    
/*
    private Court isValidCourt(String courtNum) {

        try {
            int courtNumInt = Integer.parseInt(courtNum);
            for (Court court : courtsList) {

                if (courtNumInt == court.getCourtNum()) {
                    return court;
                }
            }

            return null;

        } catch (NumberFormatException e) {
            return null;

        }
    }

    private boolean isValidTime(int startTime, int endTime) {
        return startTime >= openTime && endTime <= closeTime;
    }

    private boolean isValidDuration(int startTime, int endTime) {
        return (endTime - startTime) >= minDuration && (endTime - startTime) % minTimeIncrement == 0;
    }

    private boolean isCourtAvailable(Court court, int startTime, int endTime) {
        for (Booking booking : bookingsList) {
            HashSet<Court> courtsSet = booking.getCourtsBookedSet();
            int bookingStartTime = booking.getStartTime();
            int bookingEndTime = booking.getEndTime();
            for (Court c : courtsSet) {
                if (c.getCourtNum() == court.getCourtNum()) {
                    if ((startTime >= bookingStartTime && startTime < bookingEndTime) || (endTime >= bookingStartTime && endTime <= bookingEndTime)) {
                        return false;
                    }
                }
            }
        }
        return true;

    }
*/
}

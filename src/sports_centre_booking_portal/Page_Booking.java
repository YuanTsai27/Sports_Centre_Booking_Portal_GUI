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
    private PageNavigator pageNav;

    public Page_Booking(PageNavigator pageNav) {
        super();
        this.pageNav = pageNav;
        this.gui = new GUI_Booking(this);
        currentUser = null;
    }

    public void main(User currentUser) {
        this.currentUser = currentUser;

        // show GUI upon login page activation.
        gui.setVisible(true);

        /*
        while(gui.isVisible()){
        }
         */
        // quit if GUI closed.
        return;

    }

    public boolean processBooking(String courts, int startTime, int endTime) {
        // validate court numbers and availability
        HashSet<Court> selectedCourts = validateCourts(courts);
        boolean courtsAvailable = isCourtsAvailable(selectedCourts, startTime, endTime);
        boolean validTime = isValidTime(startTime, endTime);
        boolean validDuration = isValidDuration(startTime, endTime);

        if (selectedCourts != null && !selectedCourts.isEmpty() && courtsAvailable && validTime && validDuration) {
            // Calculate price and update GUI
            double totalPrice = calculatePrice(selectedCourts, startTime, endTime);
            gui.updateTotalPrice(totalPrice);

            // Confirm booking
            boolean confirmed = confirmBooking(selectedCourts, startTime, endTime, totalPrice);

            if (confirmed) {
                return true;
            } else {
                JOptionPane.showMessageDialog(gui, "Insufficient funds. Please top up first");
            }
        }
        return false;
    }

    private boolean isValidTime(int startTime, int endTime) {

        if (startTime >= openTime && endTime <= closeTime) {
            return true;
        }

        JOptionPane.showMessageDialog(gui, "Both time inputs has to be within the opening hours , please try again.");
        return false;
    }

    private boolean isValidDuration(int startTime, int endTime) {

        if ((endTime - startTime) >= minDuration && (endTime - startTime) % minTimeIncrement == 0) {
            return true;
        }

        if (startTime >= endTime) {
            JOptionPane.showMessageDialog(gui, "End time must be greater than start time, please try again.");
            return false;
        }

        JOptionPane.showMessageDialog(gui, "Times can only be booked on an hourly basis, please try again.");
        return false;
    }

    private HashSet<Court> validateCourts(String courts) {
        
        // Convert the input court string to a HashSet of Court objects (pseudo-code)
        HashSet<Court> selectedCourts = new HashSet<>();
        String[] courtsNumbers = courts.split("\\s+");

        // check if selected courts exists
        for (String courtNumStr : courtsNumbers) {
            Court court = isValidCourt(courtNumStr);
            if (court != null) {
                selectedCourts.add(court);
            } else {
                JOptionPane.showMessageDialog(gui, "Invalid court number: " + courtNumStr);
                return null;
            }

        }

        return selectedCourts; // Return validated set or null if invalid
    }

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

    private boolean isCourtsAvailable(HashSet<Court> selectedCourts, int startTime, int endTime) {
        for (Court court : selectedCourts) {
            if (!isCourtAvailable(court, startTime, endTime)) {
                JOptionPane.showMessageDialog(gui, "This court is not available within your chosen timeframe " + court.getCourtNum() + "Please view court availabilities on the home page before trying again.");
                break;
            }
        }
        return true;
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

    private double calculatePrice(HashSet<Court> selectedCourts, int startTime, int endTime) {
        // calculate total price based on selected courts and duration
        double totalPrice = 0;
        int duration = (endTime - startTime) / 100; // in hours
        for (Court c : selectedCourts) {
            totalPrice += c.getBasePrice() * duration;

        }

        return totalPrice;
    }

    private boolean confirmBooking(HashSet<Court> selectedCourts, int startTime, int endTime, double totalPrice) {
        if (!currentUser.bookCourt(totalPrice)) {
            return false;
        }

        Booking booking = new Booking(currentUser.getId(), currentUser, startTime, endTime, currentUser.convertPrice(totalPrice), selectedCourts);
        currentUser.addBooking(booking);
        bookingsList.add(booking);

        return true;

    }

    public void returnHome() {
        gui.dispose();
        pageNav.showHomePage(currentUser);
    }

    public User getCurrentUser(){
        return currentUser;
    }
    
    public double calculateTotalPriceForDisplay(String courts, int startTime, int endTime) {
    // Validate court numbers and availability
    HashSet<Court> selectedCourts = validateCourts(courts);
    boolean validTime = isValidTime(startTime, endTime);
    boolean validDuration = isValidDuration(startTime, endTime);

    if (selectedCourts != null && !selectedCourts.isEmpty() && validTime && validDuration) {
        return calculatePrice(selectedCourts, startTime, endTime);
    } else {
        return 0.0;
    }
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

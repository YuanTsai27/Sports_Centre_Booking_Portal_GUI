/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author yuant
 */
public class Page_Home extends Page {

    private final GUI_Home gui;
    private User currentUser;
    private final PageNavigator pageNav;

    public Page_Home(PageNavigator pageNav) {
        super();
        this.gui = new GUI_Home(this);
        this.pageNav = pageNav;
        currentUser = null;
    }

    public void main(User currentUser) {
        this.currentUser = currentUser;

        // update GUI with user's info
        gui.displayWelcomeMessage(currentUser.getFirstName() + " " + currentUser.getLastName(), currentUser.getAccBalance());

        // show GUI upon login page activation.
        gui.setVisible(true);

    }

    public void logOut() throws IOException {
       
        gui.dispose(); // close the home GUI
        pageNav.showLoginPage();
    }

    public void book() {

        gui.dispose(); // close the home GUI
        pageNav.showBookingPage(currentUser);
    }

    public void handleTopUp() {
        String amountString = JOptionPane.showInputDialog(gui, "Enter top-up amount ($):");
        if (amountString != null) {
            try {
                double amount = Double.parseDouble(amountString);
                currentUser.topUpBalance(amount);
                JOptionPane.showMessageDialog(gui, "Successfully topped up $" + amount);
                gui.displayWelcomeMessage(currentUser.getFirstName() + " " + currentUser.getLastName(), currentUser.getAccBalance());
                // Verify the update
            System.out.println("Updated balance in usersList:");
            for (User user : usersList) {
                if (user.getUsername().equals(currentUser.getUsername())) {
                    System.out.println("Username: " + user.getUsername() + ", Balance: " + user.getAccBalance());
                }
            }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(gui, "Invalid input. Please enter a valid amount.");
            }
        }
    }

    public void displayBookings() {
        StringBuilder bookingsInfo = new StringBuilder();
        ArrayList<Booking> bookings = currentUser.getOwnBookingsList();

        if (bookings.isEmpty()) {
            bookingsInfo.append("You currently have no upcoming bookings.");
        } else {
            bookingsInfo.append("You have ").append(bookings.size()).append(" upcoming booking(s):\n\n");
            for (Booking booking : bookings) {
                bookingsInfo.append("Booking ID: ").append(booking.getBookingID()).append("\n");
                bookingsInfo.append("Courts Booked: ");
                for (Court court : booking.getCourtsBookedSet()) {
                    bookingsInfo.append(court.getCourtNum()).append(" ");
                }
                bookingsInfo.append("\nStart Time: ").append(timeConvertTo12h(booking.getStartTime()));
                bookingsInfo.append("\nEnd Time: ").append(timeConvertTo12h(booking.getEndTime()));
                bookingsInfo.append("\nDuration: ").append(booking.getDuration()).append(" minutes");
                bookingsInfo.append("\nPrice Paid: $").append(booking.getPrice()).append("\n\n");
            }
        }

        JOptionPane.showMessageDialog(gui, bookingsInfo.toString(), "My Bookings", JOptionPane.INFORMATION_MESSAGE);
    }

    public String timeConvertTo12h(int time) {
        // function only works for time increments of 30 mins. 4:30pm = 1650

        int hour = time / 100;
        int minutes = time % 100;
        String period;

        // Set AM or PM
        if (hour < 12) {
            period = "AM";
        } else {
            period = "PM";

        }
        // Convert to 12-hour format
        hour = (hour == 0) ? 12 : (hour > 12) ? hour - 12 : hour;

        if (minutes != 0) {
            minutes = 30;
        }
        // Format minutes as two digits
        String minutesStr = String.format("%02d", minutes);

        // Format string and return
        return hour + ":" + minutesStr + " " + period;

    }

    public void checkCourtAvailability() {
        String courtNumberInput = JOptionPane.showInputDialog(gui, "Enter court number to check availability:");
        if (courtNumberInput != null) {
            try {
                int courtNumber = Integer.parseInt(courtNumberInput);
                Court court = findCourtByNumber(courtNumber);

                if (court != null) {
                    ArrayList<Booking> courtBookings = getCourtBookings(court);
                    displayCourtAvailability(court, courtBookings);
                } else {
                    JOptionPane.showMessageDialog(gui, "Invalid court number. Please try again.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(gui, "Invalid input. Please enter a valid court number.");
            }
        }
    }

    private Court findCourtByNumber(int courtNumber) {
        for (Court c : courtsList) {
            if (c.getCourtNum() == courtNumber) {
                return c;
            }
        }
        return null;
    }

    private ArrayList<Booking> getCourtBookings(Court court) {
        ArrayList<Booking> courtBookings = new ArrayList<>();
        for (Booking booking : bookingsList) {
            if (booking.getCourtsBookedSet().contains(court)) {
                courtBookings.add(booking);
            }
        }
        return courtBookings;
    }

    private void displayCourtAvailability(Court court, ArrayList<Booking> courtBookings) {
        StringBuilder availability = new StringBuilder("Court " + court.getCourtNum() + " Availability:\n");

        for (int time = openTime; time < closeTime; time += minTimeIncrement) {
            String timeStr = timeConvertTo12h(time);
            String status = "FREE";
            for (Booking b : courtBookings) {
                if (time >= b.getStartTime() && time < b.getEndTime()) {
                    status = "BOOKED";
                    break;
                }
            }
            availability.append(timeStr).append(" : ").append(status).append("\n");
        }

        JOptionPane.showMessageDialog(gui, availability.toString(), "Court Availability", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void Quit() {
        gui.dispose();
        AppQuitter.exitApplication();
    }
}

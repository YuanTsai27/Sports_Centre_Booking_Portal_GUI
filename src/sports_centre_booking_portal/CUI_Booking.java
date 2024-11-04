/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author yuant
 */
public class CUI_Booking extends CUI {

    @Override
    public void pageWelcome() {
        System.out.println("***************************************************");
        System.out.println("*********           Booking Page          *********");
        System.out.println("***************************************************");
        System.out.println(
                "To cancel making a booking, type 'X' at anytime");
    }

    public String askCourtNum() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Enter the court numbers(s) you would like to book separated by spaces, e.g 1 2 3");
        return scan.nextLine();
    }

    public String askStartTime() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Opening hours: 0600 (6 AM) until 2200 (10PM)");
        System.out.println("Enter start time for booking (e.g 1300 for 1 PM): ");
        return scan.nextLine();
    }

    public String askEndTime() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Opening hours: 0600 (6 AM) until 2200 (10PM)");
        System.out.println("Enter end time for booking (e.g 1300 for 1 PM): ");

        return scan.nextLine();
    }

    public String displayPrice(double price) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Total price for this booking is $" + price + ". Confirm?");
        System.out.println("Type 'yes' to confirm or 'no' to negate");
        return scan.nextLine();
    }

    public void bookingNegated() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Booking negated, returning to start of booking page.");
    }

    public void bookingUnsuccessful() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Insufficient funds. Please top up first.");
    }

    public void bookingSuccessful(User currentUser, Booking booking) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Booking confirmed for " + currentUser.getFirstName() + " " + currentUser.lastName + ":");
        System.out.print("Court(s): ");
        HashSet<Court> courts = booking.getCourtsBookedSet();

        Iterator<Court> iterator = courts.iterator();
        while (iterator.hasNext()) {
            Court c = iterator.next();
            System.out.print(c.getCourtNum());

            // print comma if there is another element
            if (iterator.hasNext()) {
                System.out.print(", ");
            }
        }
        System.out.println(); // Move to the next line after printing all courts

        System.out.println("Start time: " + timeConvertTo12h(booking.getStartTime()));
        System.out.println("End time: " + timeConvertTo12h(booking.getEndTime()));
        System.out.println("Duration: " + booking.getDuration() + " minutes");
        System.out.println("Price: $" + booking.getPrice());
    }

    public String askAnotherBooking() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Would you like to make another booking? Type 'yes' or 'no'");
        return scan.nextLine();
    }

    public void promptTryAgainCourts() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("One or more court number inputs are invalid, please try again.");
    }

    public void promptTryAgainTime1() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Both time inputs has to be within the opening hours , please try again.");
    }

    public void promptTryAgainTime2(int startTime, int endTime) {

        if (startTime >= endTime) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("End time must be greater than start time, please try again.");
            return;
        }
        System.out.println("----------------------------------------------------------------");
        System.out.println("Times can only be booked on an hourly basis, please try again.");
    }

    public void promptTryAgainCourtsUnavailable() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Court(s) for your chosen timeframes are not available. Please view court availabilities on the home page before trying again.");
    }

}

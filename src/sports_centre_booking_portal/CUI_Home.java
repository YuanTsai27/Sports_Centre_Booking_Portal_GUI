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
public class CUI_Home extends CUI {

    @Override
    public void pageWelcome() {

        System.out.println("***************************************************");
        System.out.println("***    Sports Centre Booking Portal Homepage    ***");
        System.out.println("***************************************************");
    }

    public String askAction(User user) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Current balance: $" + user.getAccBalance());
        System.out.println("----------------------------------------------------------------");
        System.out.println("What would you like to do?");
        System.out.println("Type 'book' to make a court booking");
        System.out.println("Type 'top_up' to top up your account balance");
        System.out.println("Type 'view_bookings' to view your upcoming bookings");
        System.out.println("Type 'view [court number here]' to view the court's availability schedule");
        System.out.println("Type 'x' to log out of your account");

        return scan.nextLine();
    }

    public String promptTryAgain() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Invalid option, please try again with a valid action input.");
        return scan.nextLine();
    }

    public String askTopUp() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Enter the amount ($) that you would like to top up. Type x to cancel top up.");
        return scan.nextLine();

    }

    public void cancelledTopUp(User currentUser) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Cancelled top up. Your balance remains unchanged at $" + currentUser.getAccBalance() + ".");

    }

    public void successfulTopUp(double amount, User currentUser) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Topped up $" + amount + " successfully. Your new account balance is now $" + currentUser.getAccBalance() + ".");
    }

    public void displayBookings(User currentUser) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("You currently have " + currentUser.getOwnBookingsList().size() + " upcoming court booking(s)");

        for (Booking booking : currentUser.getOwnBookingsList()) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Booking ID: " + booking.getBookingID());

            System.out.print("Courts Booked: ");
            for (Court court : booking.getCourtsBookedSet()) {
                System.out.print(court.getCourtNum() + " ");
            }

            System.out.println();
            System.out.println("Start Time: " + timeConvertTo12h(booking.getStartTime()));
            System.out.println("End Time: " + timeConvertTo12h(booking.getEndTime()));
            System.out.println("Duration: " + booking.getDuration() + " minutes");
            System.out.println("Price Paid: $" + booking.getPrice());
        }
    }

    public void invalidCourtNumber(int courtNum) {
        System.out.println("Court " + courtNum + " does not exist. Please try again");
    }

    public void displayCourtAvailability(Court court, ArrayList<Booking> courtBookings, int openTime, int closeTime, int minTimeIncrement) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Availability for Court " + court.getCourtNum());
        System.out.println("----------------------------------------------------------------");

        String timeStr = " ";
        String status = "FREE";
        int bookingStart;
        int bookingEnd;

        for (int time = openTime; time < closeTime; time += 100) {
            timeStr = timeConvertTo12h(time);
            status = "FREE";

            // check if the current time slot is booked.
            for (Booking b : courtBookings) {
                bookingStart = b.getStartTime();
                bookingEnd = b.getEndTime();

                if (time >= bookingStart && time < bookingEnd) {
                    status = "BOOKED";
                    break;
                }

            }

            // print time slot and its status
            System.out.println(timeStr + " : " + status);

        }

    }
}

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
public class Page_Home extends Page {

    private CUI_Home cui;

    public Page_Home() {
        super();
        this.cui = new CUI_Home();
    }

    public String main(User currentUser) {

        cui.pageWelcome();

        String response = null;
        boolean validInput = true;

        while (true) {

            if (validInput) {
                response = (cui.askAction(currentUser)).toLowerCase(); // do not display again if user entered invalid input previously.
                validInput = true;
            }

            if (response.equals("book")) {
                return "book";
            } else if (response.equals("x")) {
                return "x";
            } else if (response.equals("top_up")) {
                TopUp(currentUser);
                validInput = true;
            } else if (response.equals("view_bookings")) {
                cui.displayBookings(currentUser);
                validInput = true;
            } else if (response.startsWith("view ")) {
                String[] parts = response.split(" ");
                if (parts.length == 2) {
                    try {
                        int courtNumber = Integer.parseInt(parts[1]);
                        viewCourtAvailability(courtNumber);
                        validInput = true;
                    } catch (NumberFormatException e) {
                        cui.invalidInput(); // invalid court number
                        validInput = false;
                    }
                } else {
                    cui.invalidInput(); // invalid format
                    validInput = false;
                }
            } else {
                validInput = false;
                response = cui.promptTryAgain(); // invalid option, prompt a new response to try again
            }
        }
    }

    public void viewCourtAvailability(int courtNumInput) {
        Court court = null;

        // check that a court with the number input exists. 
        for (Court c : courtsList) {
            if (courtNumInput == c.getCourtNum()) {
                court = c;
                break;
            }
        }
        if (court == null){
            cui.invalidCourtNumber(courtNumInput);
            return;
        }
        
        // temporary array list to store all bookings for the respective court
        ArrayList<Booking> courtBookings = new ArrayList<>();
        for (Booking booking : bookingsList) {
            if (booking.getCourtsBookedSet().contains(court)) {
                courtBookings.add(booking);
            }
        }
        
        cui.displayCourtAvailability(court, courtBookings, openTime, closeTime, minTimeIncrement);
        
    }

    public void TopUp(User currentUser) {

        while (true) {

            String amountString = cui.askTopUp();

            if (amountString.equalsIgnoreCase("x")) {
                cui.cancelledTopUp(currentUser); // inform user of cancelled top up
                return;// return without topping up
            }

            try {
                double amount = Double.parseDouble(amountString);

                currentUser.topUpBalance(amount);
                cui.successfulTopUp(amount, currentUser);
                return;

            } catch (NumberFormatException e) {
                cui.invalidInput(); // inform user of invalid input when parsing to double fails
                // then return to start of while loop
            }

        }
    }

}

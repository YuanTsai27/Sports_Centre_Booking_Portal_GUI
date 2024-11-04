/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.util.HashSet;

/**
 *
 * @author yuant
 */
public class Page_Booking extends Page {

    private CUI_Booking cui;

    public Page_Booking() {
        super();
        this.cui = new CUI_Booking();
    }

    public void main(User currentUser) {

        cui.pageWelcome();

        boolean exit = false;

        while (!exit) {
            HashSet<Court> selectedCourts = new HashSet<>();
            boolean courtsValid = true;
            boolean userConfirmed = false;
            boolean courtsAvailable = true;
            int startTime = 0;
            int endTime = 0;
            double totalPrice = 0;

            while (!exit) {

                // ask court number(s)
                String courtInput = cui.askCourtNum();
                if (courtInput.equalsIgnoreCase("X")) {
                    exit = true;
                    break;
                }

                // validate court numbers response
                String[] courtNumbers = courtInput.split("\\s+");
                for (String courtNumStr : courtNumbers) {
                    Court court = isValidCourt(courtNumStr);
                    if (court != null) {
                        selectedCourts.add(court);
                        courtsValid = true;
                    } else {
                        cui.promptTryAgainCourts();
                        courtsValid = false;
                        break;
                    }
                }

                if (courtsValid) {
                    break;
                }
            }

            while (!exit) {
                boolean numValid1 = false;
                boolean numValid2 = false;
                // Ask for start time
                String startTimeStr = cui.askStartTime();
                if (startTimeStr.equalsIgnoreCase("X")) {
                    exit = true;
                    break;
                }

                // validate input 
                try {
                    startTime = Integer.parseInt(startTimeStr);
                    numValid1 = true;

                } catch (NumberFormatException e) {
                    cui.invalidInput();
                }

                if (numValid1) {

                    // Ask for end time
                    String endTimeStr = cui.askEndTime();
                    if (endTimeStr.equalsIgnoreCase("X")) {
                        exit = true;
                        break;
                    }

                    // validate input 
                    try {
                        endTime = Integer.parseInt(endTimeStr);
                        numValid2 = true;

                    } catch (NumberFormatException e) {
                        cui.invalidInput();
                    }

                }

                if (numValid1 && numValid2) {
                    // Check if the time and duration are valid
                    if (!isValidTime(startTime, endTime)) {
                        cui.promptTryAgainTime1();
                    } else if (!isValidDuration(startTime, endTime)) {
                        cui.promptTryAgainTime2(startTime, endTime);;
                    } else {
                        break;
                    }

                }

            }
            if (!exit) {
                // check court availability 
                for (Court court : selectedCourts) {
                    if (!isCourtAvailable(court, startTime, endTime)) {
                        courtsAvailable = false;
                        break;
                    }
                }

                if (!courtsAvailable) {
                    cui.promptTryAgainCourtsUnavailable();
                } else {
                    // calculate total price based on selected courts and duration

                    int duration = (endTime - startTime) / 100; // in hours
                    for (Court court : selectedCourts) {
                        totalPrice += court.getBasePrice() * duration;
                    }
                }

                if (!exit && courtsAvailable) {

                    while (!exit) {
                        // display price and ask for confirmation
                        String confirmation = cui.displayPrice(totalPrice);
                        if (confirmation.equalsIgnoreCase("X")) {
                            exit = true;
                            break;
                        } else if (confirmation.equalsIgnoreCase("no")) {
                            cui.bookingNegated();
                            break;

                        } else if (confirmation.equalsIgnoreCase("yes")) {
                            userConfirmed = true;
                            break;

                        } else {
                            cui.invalidInput();
                        }

                    }
                }

                if (!exit && courtsAvailable && userConfirmed) {
                    // check user balance then proceed with booking

                    if (currentUser.bookCourt(totalPrice)) {
                        Booking booking = new Booking(currentUser.getId(), currentUser, startTime, endTime, currentUser.convertPrice(totalPrice), selectedCourts);
                        currentUser.addBooking(booking);
                        bookingsList.add(booking);
                        cui.bookingSuccessful(currentUser, booking);

                        // ask if user wants to make another booking
                        while (!exit) {
                            String anotherBooking = cui.askAnotherBooking();
                            if (anotherBooking.equalsIgnoreCase("no")) {
                                exit = true;
                                break;
                            } else if (anotherBooking.equalsIgnoreCase("yes")) {
                                break;
                            } else {
                                cui.invalidInput();
                            }
                        }

                    } else {
                        cui.bookingUnsuccessful();
                    }

                }

            }
        }

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
}

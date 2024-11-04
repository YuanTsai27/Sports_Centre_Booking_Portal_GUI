/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.util.Scanner;

/**
 *
 * @author yuant
 */
public abstract class CUI {
    // responsible for scanning user inputs and printing lines to user

    protected Scanner scan = new Scanner(System.in);
    
    public abstract void pageWelcome();
    

    public void invalidInput() {
        System.out.println("Invalid input, please try again.");
    }

    public String timeConvertTo12h(int time) {
        // function only works for time increments of 30 mins. 4:30pm = 1650
        
        int hour = time / 100;
        int minutes = time % 100;
        String period = " ";

        // Set AM or PM
        if (hour < 12) {
            period = "AM";
        } else {
            period = "PM";

        }
        // Convert to 12-hour format
        hour = (hour == 0) ? 12 : (hour > 12) ? hour - 12 : hour;

        if (minutes != 0){
            minutes = 30;
        }
        // Format minutes as two digits
        String minutesStr = String.format("%02d", minutes);

        // Format string and return
        return hour + ":" + minutesStr + " " + period;

    }

   
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * @author yuant
 */
public class FileManager {
    // class that exclusively handles reading and loading data from/to files.

    public void loadUsers(ArrayList<User> usersList) throws IOException {

        File file = new File("users.txt");
        try ( BufferedReader br = new BufferedReader(new FileReader(file))) {

            usersList.clear();

            String line;
            String username = "";
            int id = -1;
            String firstName = " ";
            String lastName = " ";
            String password = " ";
            Double accBalance = 0.0;
            String userType = " ";

            while ((line = br.readLine()) != null) {

                // using string tokenizer to separate name and score into different string variables
                StringTokenizer tokenizer = new StringTokenizer(line, " ");

                User user = null;

                // Extract the tokens
                if (tokenizer.hasMoreTokens()) {
                    username = tokenizer.nextToken();

                    if (tokenizer.hasMoreTokens()) {
                        id = Integer.parseInt(tokenizer.nextToken());

                        if (tokenizer.hasMoreTokens()) {
                            firstName = tokenizer.nextToken();

                            if (tokenizer.hasMoreTokens()) {
                                lastName = tokenizer.nextToken();

                                if (tokenizer.hasMoreTokens()) {
                                    password = tokenizer.nextToken();

                                    if (tokenizer.hasMoreTokens()) {
                                        accBalance = Double.parseDouble(tokenizer.nextToken());

                                        if (tokenizer.hasMoreTokens()) {
                                            userType = tokenizer.nextToken();
                                        }

                                        if (userType.equalsIgnoreCase("STAFF")) {
                                            user = new UserStaff(username, id, firstName, lastName, password, accBalance);
                                        } else if (userType.equalsIgnoreCase("ADMIN")) {
                                            user = new UserAdmin(username, id, firstName, lastName, password, accBalance);
                                        } else {
                                            user = new User(username, id, firstName, lastName, password, accBalance);
                                        }

                                        usersList.add(user);

                                    }
                                }
                            }
                        }
                    }
                }
            }

            // set appropriate static nextId to 1 after the latest saved user's id added.
            User.setStaticNextID(id);

    

            br.close();
        }

    }

    public void loadCourts(ArrayList<Court> courtsList) throws IOException {

        File file = new File("courts.txt");
        try ( BufferedReader br = new BufferedReader(new FileReader(file))) {

            courtsList.clear();
            String line;

            while ((line = br.readLine()) != null) {

                // using string tokenizer to separate name and score into different string variables
                StringTokenizer tokenizer = new StringTokenizer(line, " ");

                int courtNum = 0;
                int[] position = new int[2];
                double basePrice = 0.0;

                // Extract the tokens
                if (tokenizer.hasMoreTokens()) {
                    courtNum = Integer.parseInt(tokenizer.nextToken());

                    if (tokenizer.hasMoreTokens()) {
                        basePrice = Double.parseDouble(tokenizer.nextToken());

                        if (tokenizer.hasMoreTokens()) {
                            String positionStr = tokenizer.nextToken(); // Third token: position (e.g., "1,1")

                            // Split the position string using comma as a delimiter
                            String[] positionTokens = positionStr.split(",");

                            // Parse the tokens into integers and assign to the position array
                            if (positionTokens.length == 2) {
                                position[0] = Integer.parseInt(positionTokens[0]); // x-coordinate
                                position[1] = Integer.parseInt(positionTokens[1]); // y-coordinate
                            }

                            Court court = new Court(courtNum, basePrice, position);
                            courtsList.add(court);

                        }
                    }
                }
            }


            br.close();
        }
    }

    public void loadBookings(ArrayList<Booking> bookingsList, ArrayList<Court> courtsList, ArrayList<User> usersList) throws IOException {

        File file = new File("bookings.txt");
        try ( BufferedReader br = new BufferedReader(new FileReader(file))) {

            bookingsList.clear();
            String line;

            int bookingID = 0;
            int userID = 0;
            int startTime = 0;
            int endTime = 0;
            double price = 0.0;
            int courtNum = 0;

            while ((line = br.readLine()) != null) {

                // using string tokenizer to separate name and score into different string variables
                StringTokenizer tokenizer = new StringTokenizer(line, " ");

                Booking booking = null;
                Court court = null;
                User user = null;

                // Extract the tokens
                if (tokenizer.hasMoreTokens()) {
                    bookingID = Integer.parseInt(tokenizer.nextToken());

                    if (tokenizer.hasMoreTokens()) {
                        userID = Integer.parseInt(tokenizer.nextToken());

                        for (User u : usersList) {
                            // loop to get user object for the particular booking
                            if (u.getId() == userID) {
                                user = u;
                                break;
                            }
                        }

                        if (tokenizer.hasMoreTokens()) {
                            startTime = Integer.parseInt(tokenizer.nextToken());

                            if (tokenizer.hasMoreTokens()) {
                                endTime = Integer.parseInt(tokenizer.nextToken());

                                if (tokenizer.hasMoreTokens()) {
                                    price = Double.parseDouble(tokenizer.nextToken());

                                    if (tokenizer.hasMoreTokens()) {

                                        booking = new Booking(bookingID, userID, user, startTime, endTime, price);

                                        String courtsStr = tokenizer.nextToken(); // Third token: position (e.g., "1,1")

                                        // Split the position string using comma as a delimiter
                                        String[] courtsTokens = courtsStr.split(",");

                                        // Parse the tokens into integers and assign to the position array
                                        for (int i = 0; i < courtsTokens.length; i++) {
                                            courtNum = Integer.parseInt(courtsTokens[i]);

                                            for (int j = 0; j < courtsList.size(); j++) {
                                                court = courtsList.get(j);
                                                if (courtNum == court.getCourtNum()) {
                                                    booking.addCourt(court);
                                                }

                                            }

                                        }

                                        bookingsList.add(booking);
                                        user.addBooking(booking);

                                    }

                                }
                            }
                        }
                    }
                }
            }

            // set appropriate static nextId to 1 after the latest saved booking's id added.
            Booking.setStaticNextBookingID(bookingID);



            br.close();
        }

    }

    public void saveUsers(ArrayList<User> userList) throws IOException {
        // to call each time an user logs out of homepage.

        File file = new File("users.txt");
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (User u : userList) {

                // rewrite all user data.
                bw.write(u.getUsername() + " " + u.getId() + " " + u.getFirstName() + " " + u.getLastName() + " " + u.getPassword() + " " + u.getAccBalance() + " " + u.getUserTypeAsString());
                bw.newLine();
            }

            bw.close();
        }

    }

    public void saveCourts(ArrayList<Court> courtsList) throws IOException {
        //To call each time an admin changes a court's price
        File file = new File("courts.txt");
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (Court c : courtsList) {

                int[] positionVector = c.getPosition();

                bw.write(c.getCourtNum() + " " + c.getBasePrice() + " " + positionVector[0] + "," + positionVector[1]);

                bw.newLine();

            }

            bw.close();
        }

    }

    public void saveBookings(ArrayList<Booking> bookingsList) throws IOException {
        // to call each time a new booking is made, or an existing booking is modified.

        File file = new File("bookings.txt");
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            int courtNum;
            for (Booking b : bookingsList) {

                // rewrite all current bookings data.
                bw.write(b.getBookingID() + " " + b.getUserID() + " " + b.getStartTime() + " " + b.getEndTime() + " " + b.getPrice() + " ");

                HashSet<Court> courts = b.getCourtsBookedSet();

                Iterator<Court> iterator = courts.iterator();
                while (iterator.hasNext()) {
                    Court c = iterator.next();
                     courtNum = c.getCourtNum();
                    
                    bw.write(String.valueOf(courtNum));

                    // print comma if there is another element
                    if (iterator.hasNext()) {
                        bw.write(",");
                    }
                }
                
                bw.newLine();
            }

            bw.close();
        }

    }

}

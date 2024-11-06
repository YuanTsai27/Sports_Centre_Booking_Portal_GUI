/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author yuantsai
 */
public class DBManager {
    // exclusively handles reading and loading data from/to the database.

    private Connection conn;
    private static final String url = "jdbc:derby://localhost:1527/SportsCentreBookingPortal_DB";
    private static final String username = "YuanTsai";
    private static final String password = "YuanTsai";

    public DBManager() {
        establishConnection();
        
        //check if tables already exist firesultSett, if not, create tables.
        CreateTables();
    }

    public Connection getConnection() {
        return this.conn;
    }

    private void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(url, username, password);

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());

            }
        }
    
    }

    private void CreateTables() {
        try {
            DatabaseMetaData dbm = conn.getMetaData();

            // check firesultSett if useresultSet table exists, if not create it
            ResultSet tables = dbm.getTables(null, null, "USERS", null);
            if (!tables.next()) {
                Statement useresultSetStatement = conn.createStatement();
                String createUseresultSetTable = "CREATE TABLE USERS ("
                        + "USERNAME VARCHAR(255) PRIMARY KEY, "
                        + "ID INT, "
                        + "FIRSTNAME VARCHAR(255), "
                        + "LASTNAME VARCHAR(255), "
                        + "PASSWORD VARCHAR(255), "
                        + "ACCBALANCE DOUBLE, "
                        + "USERTYPE VARCHAR(50))";
                useresultSetStatement.executeUpdate(createUseresultSetTable);
                useresultSetStatement.close();
            }

            // check firesultSett if courts table exists, if not create it
            tables = dbm.getTables(null, null, "COURTS", null);
            if (!tables.next()) {
                Statement courtsStatement = conn.createStatement();
                String createCourtsTable = "CREATE TABLE COURTS ("
                        + "COURTNUM INT PRIMARY KEY, "
                        + "BASEPRICE DOUBLE, "
                        ;
                courtsStatement.executeUpdate(createCourtsTable);
                courtsStatement.close();
            } else {
            }

            // check firesultSett if bookings table exists, if not create it
            tables = dbm.getTables(null, null, "BOOKINGS", null);
            if (!tables.next()) {
                Statement bookingsStatement = conn.createStatement();
                String createBookingsTable = "CREATE TABLE BOOKINGS ("
                        + "BOOKINGID INT PRIMARY KEY, "
                        + "USERID INT, "
                        + "USERNAME VARCHAR(255), "
                        + "STARTTIME INT, "
                        + "ENDTIME INT, "
                        + "PRICE DOUBLE)";
                bookingsStatement.executeUpdate(createBookingsTable);
                bookingsStatement.close();
            }

            // Similarly for BOOKINGCOURTS table
            tables = dbm.getTables(null, null, "BOOKINGCOURTS", null);
            if (!tables.next()) {
                Statement statement = conn.createStatement();
                String createBookingCourtsTable = "CREATE TABLE BOOKINGCOURTS ("
                        + "BOOKINGID INT, "
                        + "COURTNUM INT)";
                statement.executeUpdate(createBookingCourtsTable);
                statement.close();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void loadUsers(ArrayList<User> users) throws IOException {

        try {
            users.clear();

            String query = "SELECT * FROM USERS";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int nextID = 1; 

            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                int id = resultSet.getInt("ID");
                String firesultSettName = resultSet.getString("FIRSTNAME");
                String lastName = resultSet.getString("LASTNAME");
                String password = resultSet.getString("PASSWORD");
                double accBalance = resultSet.getDouble("ACCBALANCE");
                String userType = resultSet.getString("USERTYPE");

                // Factory pattern
                User user = UserFactory.getUser(userType, username, id, firesultSettName, lastName, password, accBalance);

                users.add(user);

                // Update maxID
                if (id > nextID) {
                    nextID = id;
                }
            }

            // Set appropriate static nextId after the latest saved user's id
            User.setStaticNextID(nextID + 1);

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void loadCourts(ArrayList<Court> courtsList) throws IOException {

        try {
            courtsList.clear();

            String query = "SELECT * FROM COURTS";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int courtNum = resultSet.getInt("COURTNUM");
                double basePrice = resultSet.getDouble("BASEPRICE");

                Court court = new Court(courtNum, basePrice);
                courtsList.add(court);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void loadBookings(ArrayList<Booking> bookingsList, ArrayList<Court> courtsList, ArrayList<User> usersList) throws IOException {

        try {
            bookingsList.clear();

            String query = "SELECT * FROM BOOKINGS";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int nextBookingID = -1;
            User user = null;

            while (resultSet.next()) {
                int bookingID = resultSet.getInt("BOOKINGID");
                int userID = resultSet.getInt("USERID");
                String username = resultSet.getString("USERNAME");
                int startTime = resultSet.getInt("STARTTIME");
                int endTime = resultSet.getInt("ENDTIME");
                double price = resultSet.getDouble("PRICE");

                // Find the User object
                for (User u : usersList) {
                    if (u.getId() == userID) {
                        user = u;
                        break;
                    }
                }

                Booking booking = new Booking(bookingID, userID, user, startTime, endTime, price);

                // Now, get the courts booked for this booking
                String courtsQuery = "SELECT COURTNUM FROM BOOKINGCOURTS WHERE BOOKINGID = ?";
                PreparedStatement pstatement = conn.prepareStatement(courtsQuery);
                pstatement.setInt(1, bookingID);
                ResultSet courtsRs = pstatement.executeQuery();

                while (courtsRs.next()) {
                    int courtNum = courtsRs.getInt("COURTNUM");

                    // Find the Court object
                    Court court = null;
                    for (Court c : courtsList) {
                        if (c.getCourtNum() == courtNum) {
                            court = c;
                            break;
                        }
                    }

                    if (court != null) {
                        booking.addCourt(court);
                    }
                }

                courtsRs.close();
                pstatement.close();

                bookingsList.add(booking);
                if (user != null) {
                    user.addBooking(booking);
                }

                if (bookingID > nextBookingID) {
                    nextBookingID = bookingID;
                }
            }

            // Set appropriate static nextId after the latest saved booking's id
            Booking.setStaticNextBookingID(nextBookingID + 1);

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void saveUsers(ArrayList<User> userList) throws IOException {
        try {
            // Delete all existing useresultSet
            String deleteQuery = "DELETE FROM USERS";
            Statement statement = conn.createStatement();
            statement.executeUpdate(deleteQuery);
            statement.close();

            // insert all users into database
            String insertQuery = "INSERT INTO USERS (USERNAME, ID, FIRSTNAME, LASTNAME, PASSWORD, ACCBALANCE, USERTYPE) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstatement = conn.prepareStatement(insertQuery);

            for (User u : userList) {
                pstatement.setString(1, u.getUsername());
                pstatement.setInt(2, u.getId());
                pstatement.setString(3, u.getFirstName());
                pstatement.setString(4, u.getLastName());
                pstatement.setString(5, u.getPassword());
                pstatement.setDouble(6, u.getAccBalance());
                pstatement.setString(7, u.getUserTypeAsString());
                pstatement.executeUpdate();
            }

            pstatement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void saveCourts(ArrayList<Court> courtsList) throws IOException {
        try {
            // Delete existing courts
            String deleteQuery = "DELETE FROM COURTS";
            Statement statement = conn.createStatement();
            statement.executeUpdate(deleteQuery);
            statement.close();

            // Insert courts
            String insertQuery = "INSERT INTO COURTS (COURTNUM, BASEPRICE) VALUES (?, ?)";
            PreparedStatement pstatement = conn.prepareStatement(insertQuery);

            for (Court c : courtsList) {
                pstatement.setInt(1, c.getCourtNum());
                pstatement.setDouble(2, c.getBasePrice());
                pstatement.executeUpdate();
            }

            pstatement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void saveBookings(ArrayList<Booking> bookingsList) throws IOException {
        try {
            // Delete existing bookings and booking courts
            String deleteBookingsQuery = "DELETE FROM BOOKINGS";
            Statement statement = conn.createStatement();
            statement.executeUpdate(deleteBookingsQuery);
            statement.close();

            String deleteBookingCourtsQuery = "DELETE FROM BOOKINGCOURTS";
            statement = conn.createStatement();
            statement.executeUpdate(deleteBookingCourtsQuery);
            statement.close();

            // Insert bookings
            String insertBookingQuery = "INSERT INTO BOOKINGS (BOOKINGID, USERID, USERNAME, STARTTIME, ENDTIME, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement bookingPstatement = conn.prepareStatement(insertBookingQuery);

            String insertBookingCourtQuery = "INSERT INTO BOOKINGCOURTS (BOOKINGID, COURTNUM) VALUES (?, ?)";
            PreparedStatement bookingCourtPstatement = conn.prepareStatement(insertBookingCourtQuery);

            for (Booking b : bookingsList) {
                bookingPstatement.setInt(1, b.getBookingID());
                bookingPstatement.setInt(2, b.getUserID());
                bookingPstatement.setString(3, b.getUser().getUsername());
                bookingPstatement.setInt(4, b.getStartTime());
                bookingPstatement.setInt(5, b.getEndTime());
                bookingPstatement.setDouble(6, b.getPrice());
                bookingPstatement.executeUpdate();

                // Insert into BOOKINGCOURTS table
                HashSet<Court> courts = b.getCourtsBookedSet();
                for (Court c : courts) {
                    bookingCourtPstatement.setInt(1, b.getBookingID());
                    bookingCourtPstatement.setInt(2, c.getCourtNum());
                    bookingCourtPstatement.executeUpdate();
                }
            }

            bookingPstatement.close();
            bookingCourtPstatement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}



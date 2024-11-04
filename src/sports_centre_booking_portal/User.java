/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author yuant
 */
public class User {
    protected static int nextID = 1;
    protected final int id;
    protected UserType type;
    protected final String username;
    protected final String firstName;
    protected final String lastName;
    protected String password;
    protected double accBalance = 0;
    protected ArrayList<Booking> ownBookingsList;
    
      public enum UserType {
        MEMBER, STAFF, ADMIN
    }
    
    public User(){
        this.username = "Unknown";
        this.firstName = "Unknown";
        this.lastName = "Unknown";
        this.password = "Unknown";
        this.accBalance = -1;
        this.type = UserType.MEMBER;
        this.id = -1;
    }
    
    // constructor for existing users with id
    public User(String username, int id, String firstName, String lastName, String password, double accBalance) {
        this.username = username;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.accBalance = accBalance;
        this.type = UserType.MEMBER;
        this.ownBookingsList = new ArrayList<>();
       
    }
    
    // constructor for new registering users.
    public User(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.accBalance = 0;
        this.type = UserType.MEMBER;
        this.id = getNextID();
        this.ownBookingsList = new ArrayList<>();
        
    }

    public static int getNextID() {
        int next = nextID;
        nextID++;
        return next;
    }
    
    public static void setStaticNextID(int latestID){
        User.nextID = latestID + 1;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAccBalance() {
        return accBalance;
    }

    public void topUpBalance(double topUp) {
        this.accBalance += topUp;
    }
    
    public double convertPrice(double bookingsCost){
        // no discounts for normal members
        return bookingsCost;
    }
    
    public boolean bookCourt(double bookingsCost){
        
        if (this.accBalance - bookingsCost < 0){
            return false;
        }
        
        this.accBalance -= bookingsCost;
        return true;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public UserType getType() {
        return type;
    }
    // Method to get UserType as a string
    public String getUserTypeAsString() {
        return type.toString(); // Convert the UserType enum to a string
    }
    
    public void staffPromote(){ // method only invokable by admin user.
        this.type = UserType.STAFF;
    }
    
     @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }

        User other = (User) o;
        
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    public void addBooking(Booking booking){
        this.ownBookingsList.add(booking);
        
    }
    
    public void removeBooking(Booking booking){
        this.ownBookingsList.remove(booking);
        
    }

    public ArrayList<Booking> getOwnBookingsList() {
        return ownBookingsList;
    }
    
    
}

    
    
 
    
  
    


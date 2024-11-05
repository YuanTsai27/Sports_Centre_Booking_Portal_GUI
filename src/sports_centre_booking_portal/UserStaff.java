/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

/**
 *
 * @author yuant
 */
public class UserStaff extends User {

    public UserStaff(String username, int id, String firstName, String lastName, String password, double accBalance) {
        super(username, id, firstName, lastName, password, accBalance);
        this.type = UserType.STAFF;
    }
    
    @Override
    public double convertPrice(double bookingsCost){
        // Staff gets 50% discount benefits.
        return 0.5 * bookingsCost;
    }
    
     @Override 
    public boolean bookCourt(double bookingsCost){
        
        // staff users have 50% booking discounts benefits 
        bookingsCost = 0.5 * bookingsCost;
        
        if (this.accBalance - bookingsCost < 0){
            return false;
        }
        
        this.accBalance -= bookingsCost;
        return true;
    }
    
   
}

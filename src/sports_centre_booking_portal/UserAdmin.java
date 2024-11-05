/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

/**
 *
 * @author yuant
 */
public class UserAdmin extends User {
    
    public UserAdmin(String username, int id, String firstName, String lastName, String password, double accBalance){
        super(username, id, firstName, lastName, password,accBalance);
        this.type = UserType.ADMIN;
       
    }
    
    @Override
    public double convertPrice(double bookingsCost){
        // Admin users does not pay to book courts.
        return 0;
    }
    
   @Override
    public boolean bookCourt(double bookingsCost) {
        // Admin users does not pay to book courts.
        return true;
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

/**
 *
 * @author yuantsai
 */
public class UserFactory {
    // Factory pattern
    
    public static User getUser(String userType, String username, int id, String firstName, String lastName, String password, double accBalance){
        User u = null;
       if ("member".equalsIgnoreCase(userType)){
           u = new User(username, id, firstName, lastName, password, accBalance);
       } else if ("staff".equalsIgnoreCase(userType)){
           u = new UserStaff(username, id, firstName, lastName, password, accBalance);
       } else if ("admin".equalsIgnoreCase(userType)){
           u = new UserAdmin(username, id, firstName, lastName, password, accBalance);
       }
       
       return u;
    }
    
}

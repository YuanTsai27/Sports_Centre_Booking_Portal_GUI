/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

/**
 *
 * @author yuant
 */
public class Court {
    private final int courtNum;
    private double basePrice;

    public Court(int courtNum, double basePrice) {
        this.courtNum = courtNum;
        this.basePrice = basePrice;
    }

    public int getCourtNum() {
        return courtNum;
    }


    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author yuantsai
 */
public class GUI_Booking extends JFrame {
    
    private Page_Booking pageBooking; // link a Page_Booking instance upon initialisation
    
    private JTextField courtField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JLabel priceLabel;
    
    public GUI_Booking(Page_Booking pageBooking){
        this.pageBooking = pageBooking;
        setUpGUI();
    }
    
    private void setUpGUI(){
        setTitle("Sports Centre Booking - Make a Booking");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(6,2, 10, 10));
        
        add(new JLabel("Enter court numbers (e.g 1 2 3:"));
        courtField = new JTextField();
        add(courtField);
        
        add(new JLabel("Start Time:"));
        startTimeField = new JTextField();
        add(startTimeField);

        add(new JLabel("End Time:"));
        endTimeField = new JTextField();
        add(endTimeField);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });
        add(confirmButton);
        
        cancelButton = new JButton("Confirm Booking");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBooking();
            }
        });
        add(cancelButton);
        
        priceLabel = new JLabel("Total Price: ");
        add(priceLabel);
    }
    
    private void handleBooking() {
        String courts = courtField.getText();
        int startTime = Integer.parseInt(startTimeField.getText());
        int endTime = Integer.parseInt(endTimeField.getText());

        // Page_Booking class to process data
        pageBooking.processBooking(courts, startTime, endTime);
    }
    
    private void cancelBooking() {
        courtField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        priceLabel.setText("Total Price: $0.0");
        JOptionPane.showMessageDialog(this, "Booking reset.");
    }

    public void updateTotalPrice(double price) {
        priceLabel.setText("Total Price: $" + price);
    }

 
    
}

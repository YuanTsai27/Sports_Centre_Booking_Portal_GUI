/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sports_centre_booking_portal;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private JButton resetButton;
    private JButton returnHomeButton;
    private JButton checkPriceButton;
    private JLabel priceLabel;

    public GUI_Booking(Page_Booking pageBooking) {
        this.pageBooking = pageBooking;
        setUpGUI();
    }

    private void setUpGUI() {
        setTitle("Sports Centre Booking - Make a Booking");
        setSize(600, 400);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        setLayout(new GridLayout(10, 2, 10, 10));

        add(new JLabel("Opening hours: "));
        priceLabel = new JLabel("0600 (6 AM) until 2200 (10PM)");
        add(priceLabel);

        add(new JLabel("Enter court numbers (e.g 1 2 3:)"));
        courtField = new JTextField();
        add(courtField);

        add(new JLabel("Start Time (e.g 1300 for 1PM):"));
        startTimeField = new JTextField();
        add(startTimeField);

        add(new JLabel("End Time:"));
        endTimeField = new JTextField();
        add(endTimeField);

        add(new JLabel("Total Price: "));
        priceLabel = new JLabel("$0.0");
        add(priceLabel);

        checkPriceButton = new JButton("Check Total Price");
        checkPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkTotalPrice();
            }
        });
        add(checkPriceButton);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });
        add(confirmButton);

        resetButton = new JButton("Reset Booking");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBooking();
            }
        });
        add(resetButton);

        returnHomeButton = new JButton("Return to Home");
        returnHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageBooking.returnHome();
                resetBooking();
            }
        });
        add(returnHomeButton);
        
        // Set default close operation and add window listener
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                    GUI_Booking.this,
                    "Are you sure you want to exit the application?",
                    "Exit Application",
                    JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    pageBooking.Quit();
                }
            }
        });

    }

    private void handleBooking() {

        try {
            String courts = courtField.getText();
            if ("".equals(courts)) {
                JOptionPane.showMessageDialog(this, "Please enter court numbers");
                return;
            }
            int startTime = Integer.parseInt(startTimeField.getText());
            int endTime = Integer.parseInt(endTimeField.getText());

            // Page_Booking class to process data
            boolean isSuccessful = pageBooking.processBooking(courts, startTime, endTime);
            User currentUser = pageBooking.getCurrentUser();

            if (isSuccessful) {
                int option = JOptionPane.showOptionDialog(
                        this,
                        "Booking Confirmed! Your balance is now $" + currentUser.getAccBalance() + "\nWould you like to make another booking?",
                        "Booking Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"Make Another Booking", "Return to Home"},
                        "Make Another Booking"
                );

                if (option == JOptionPane.YES_OPTION) {
                    resetBooking(); // Reset the booking GUI for a new booking
                } else {
                    dispose(); // Close the booking GUI
                    pageBooking.returnHome(); // Navigate back to home page
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid values for start and end times.");
        }

    }

    private void resetBooking() {
        courtField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        priceLabel.setText("Total Price: $0.0");
    }

    public void updateTotalPrice(double price) {
        priceLabel.setText("$" + price);
    }

    private void checkTotalPrice() {
        try {

            String courts = courtField.getText();
            if ("".equals(courts)) {
                JOptionPane.showMessageDialog(this, "Please enter court numbers");
                return;
            }
            int startTime = Integer.parseInt(startTimeField.getText());
            int endTime = Integer.parseInt(endTimeField.getText());

            // Page_Booking method to calculate price
            double price = pageBooking.calculateTotalPriceForDisplay(courts, startTime, endTime);
            updateTotalPrice(price);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid values for start and end times.");
        }
    }

}

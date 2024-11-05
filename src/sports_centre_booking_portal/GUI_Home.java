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
import javax.swing.SwingConstants;

/**
 *
 * @author yuantsai
 */
public class GUI_Home extends JFrame {
    
    private Page_Home pageHome; //link a Page_Booking instance upon initialisation
    
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private JButton bookButton;
    private JButton topUpButton;
    private JButton viewBookingsButton;
    private JButton viewCourtButton;
    private JButton logoutButton;

    public GUI_Home(Page_Home pageHome) {
        this.pageHome = pageHome;
        setupUI();
    }

    private void setupUI() {
        setTitle("Sports Centre Booking Portal - Home");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  

        setLayout(new GridLayout(6, 1, 10, 10));

        welcomeLabel = new JLabel("Welcome to the Sports Centre Booking Portal", SwingConstants.CENTER);
        add(welcomeLabel);

        balanceLabel = new JLabel("Current Balance: $0.00", SwingConstants.CENTER);
        add(balanceLabel);

        bookButton = new JButton("Make a Booking");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageHome.book();
            }
        });
        add(bookButton);

        topUpButton = new JButton("Top Up Account");
        topUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageHome.handleTopUp();
            }
        });
        add(topUpButton);

        viewBookingsButton = new JButton("View My Bookings");
        viewBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageHome.displayBookings();
            }
        });
        add(viewBookingsButton);

        viewCourtButton = new JButton("Check Court Availability");
        viewCourtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageHome.checkCourtAvailability();
            }
        });
        add(viewCourtButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageHome.logOut();
            }
        });
        add(logoutButton);
    }

    public void displayWelcomeMessage(String userName, double balance) {
        welcomeLabel.setText("Welcome, " + userName + "!");
        balanceLabel.setText("Current Balance: $" + String.format("%.2f", balance));
    }
}
    


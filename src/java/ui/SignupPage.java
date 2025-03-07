package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class SignupPage extends JFrame {
    private JTextField nameField, phoneField, otpField;
    private JPasswordField passwordField;
    private JButton generateOtpButton, signupButton, backButton;
    private String generatedOtp;

    public SignupPage() {
        setTitle("Signup");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 100, 180), getWidth(), getHeight(), new Color(200, 50, 120));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        JLabel titleLabel = new JLabel("Create a New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
        titleLabel.setBounds(400, 30, 800, 60);
        backgroundPanel.add(titleLabel);

        JLabel nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        nameLabel.setBounds(350, 150, 300, 40);
        backgroundPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 26));
        nameField.setBounds(650, 150, 400, 45);
        backgroundPanel.add(nameField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        phoneLabel.setBounds(350, 220, 300, 40);
        backgroundPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 26));
        phoneField.setBounds(650, 220, 400, 45);
        backgroundPanel.add(phoneField);

        JLabel passwordLabel = new JLabel("Set Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        passwordLabel.setBounds(350, 290, 300, 40);
        backgroundPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 26));
        passwordField.setBounds(650, 290, 400, 45);
        backgroundPanel.add(passwordField);

        JLabel otpLabel = new JLabel("Enter OTP:");
        otpLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        otpLabel.setBounds(350, 360, 300, 40);
        backgroundPanel.add(otpLabel);

        otpField = new JTextField();
        otpField.setFont(new Font("Arial", Font.PLAIN, 26));
        otpField.setBounds(650, 360, 250, 45);
        backgroundPanel.add(otpField);

        generateOtpButton = new JButton("Generate OTP");
        generateOtpButton.setFont(new Font("Arial", Font.BOLD, 26));
        generateOtpButton.setBounds(920, 360, 230, 50);
        backgroundPanel.add(generateOtpButton);

        signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Arial", Font.BOLD, 30));
        signupButton.setBounds(500, 450, 250, 60);
        backgroundPanel.add(signupButton);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBounds(780, 450, 250, 60);
        backgroundPanel.add(backButton);

        generateOtpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generatedOtp = generateOtp();
                JOptionPane.showMessageDialog(null, "Your OTP: " + generatedOtp, "OTP Generated", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateSignup();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WelcomePage();
            }
        });

        setVisible(true);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void validateSignup() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String password = new String(passwordField.getPassword());
        String enteredOtp = otpField.getText();

        if (name.isEmpty() || phone.isEmpty() || password.isEmpty() || enteredOtp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!enteredOtp.equals(generatedOtp)) {
            JOptionPane.showMessageDialog(this, "Invalid OTP!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save user details to database
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/quiz_db", "quiz", "quiz");
            String query = "INSERT INTO APP.USERS (PHONE_NO, NAME, PASSWORD) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, phone);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Signup Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new LoginPage();
    }
}

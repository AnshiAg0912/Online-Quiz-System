package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField phoneField, nameField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton;

    public LoginPage() {
        setTitle("Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 140, 200), getWidth(), getHeight(), new Color(255, 80, 150));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        JLabel titleLabel = new JLabel("Login to Online Quiz System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 40));
        titleLabel.setBounds(450, 50, 600, 50);
        backgroundPanel.add(titleLabel);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        phoneLabel.setBounds(400, 150, 250, 30);
        backgroundPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 22));
        phoneField.setBounds(650, 150, 300, 35);
        backgroundPanel.add(phoneField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        nameLabel.setBounds(400, 200, 250, 30);
        backgroundPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 22));
        nameField.setBounds(650, 200, 300, 35);
        backgroundPanel.add(nameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordLabel.setBounds(400, 250, 250, 30);
        backgroundPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 22));
        passwordField.setBounds(650, 250, 300, 35);
        backgroundPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 24));
        loginButton.setBounds(550, 330, 200, 50);
        backgroundPanel.add(loginButton);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBounds(770, 330, 200, 50);
        backgroundPanel.add(backButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WelcomePage(); // Go back to Welcome Page
            }
        });

        setVisible(true);
    }

    private void authenticateUser() {
        String phone = phoneField.getText();
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());

        if (phone.isEmpty() || name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/quiz_db", "quiz", "quiz");
            String query = "SELECT * FROM APP.USERS WHERE PHONE_NO=? AND NAME=? AND PASSWORD=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, phone);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close login page
                new TopicSelectionPage(phone); // Redirect to Topic Selection Page
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

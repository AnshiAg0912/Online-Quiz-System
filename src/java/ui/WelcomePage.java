package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame {

    private JPanel panel;
    private JLabel welcomeLabel;

    public WelcomePage() {
        setTitle("Welcome to Online Quiz System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start in full screen
        setLayout(null);

        // Custom Panel with Gradient Background
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(52, 143, 80);  // Dark Green
                Color color2 = new Color(86, 180, 211); // Light Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);
        add(panel);

        // Welcome Label (Centered with Italic Font)
        welcomeLabel = new JLabel("Welcome to Online Quiz", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 50)); // Italic + Bold
        welcomeLabel.setForeground(Color.WHITE);
        panel.add(welcomeLabel);

        // Login Button (Larger Size)
        JButton loginButton = new JButton("Login");
        styleButton(loginButton, new Color(255, 87, 34));
        panel.add(loginButton);

        // Signup Button (Larger Size)
        JButton signupButton = new JButton("Signup");
        styleButton(signupButton, new Color(33, 150, 243));
        panel.add(signupButton);

        // Adjust components dynamically on resize
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        // Login Action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginPage();
            }
        });

        // Signup Action
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignupPage();
            }
        });

        setVisible(true);
    }

    // Update layout dynamically
    private void updateLayout() {
        int width = getWidth();
        int height = getHeight();
        panel.setBounds(0, 0, width, height);

        int buttonWidth = 300;  // Increased width
        int buttonHeight = 70;  // Increased height
        int centerX = width / 2 - buttonWidth / 2;

        // Position elements dynamically
        welcomeLabel.setBounds(0, height / 4, width, 70); // Centered Title Label
        panel.getComponent(1).setBounds(centerX, height / 2, buttonWidth, buttonHeight); // Login
        panel.getComponent(2).setBounds(centerX, height / 2 + 90, buttonWidth, buttonHeight); // Signup
    }

    // Style button method
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 22)); // Bigger Font
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        new WelcomePage();
    }
}

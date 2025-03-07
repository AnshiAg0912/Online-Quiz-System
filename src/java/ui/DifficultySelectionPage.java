package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultySelectionPage extends JFrame {
    private String phoneNo, subject;

    public DifficultySelectionPage(final String phoneNo, final String subject) {
        this.phoneNo = phoneNo;
        this.subject = subject;
        setTitle("Select Difficulty");
        
        // Make the frame fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Background Gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 140, 140), getWidth(), getHeight(), new Color(255, 70, 70));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, screenWidth, screenHeight);
        setContentPane(backgroundPanel);

        JLabel label = new JLabel("Select Difficulty", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 50));
        label.setBounds((screenWidth - 500) / 2, 100, 500, 70);
        backgroundPanel.add(label);

        String[] levels = {"Easy", "Medium", "Hard"};
        int buttonWidth = 400;
        int buttonHeight = 80;
        int centerX = (screenWidth - buttonWidth) / 2;
        int startY = screenHeight / 3;

        for (int i = 0; i < levels.length; i++) {
            final String level = levels[i];
            JButton button = new JButton(level);
            button.setFont(new Font("Arial", Font.BOLD, 30));
            button.setBounds(centerX, startY + (i * 120), buttonWidth, buttonHeight);
            button.setBackground(new Color(255, 200, 100));
            button.setForeground(Color.BLACK);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new QuizPage(subject, level, phoneNo);
                    dispose();
                }
            });
            backgroundPanel.add(button);
        }

        setVisible(true);
    }
}

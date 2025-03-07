package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopicSelectionPage extends JFrame {
    private String phoneNo;

    public TopicSelectionPage(final String phoneNo) {
        this.phoneNo = phoneNo;
        setTitle("Select Topic");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background Gradient Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(100, 150, 255), getWidth(), getHeight(), new Color(50, 100, 200));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Select a Topic", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 40));
        backgroundPanel.add(label, gbc);

        String[] topics = {"History", "Geography", "Maths", "Science", "GK"};
        JPanel buttonPanel = new JPanel(new GridLayout(topics.length, 1, 10, 10));
        buttonPanel.setOpaque(false);

        for (final String topic : topics) {
            JButton button = new JButton(topic);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setBackground(new Color(255, 200, 100));
            button.setForeground(Color.BLACK);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new DifficultySelectionPage(phoneNo, topic);
                    dispose();
                }
            });
            buttonPanel.add(button);
        }

        gbc.gridy = 1;
        backgroundPanel.add(buttonPanel, gbc);
        
        setVisible(true);
    }
}

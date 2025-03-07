package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class QuizOver extends JFrame {
    public QuizOver(int score, Map<String, String> correctAnswers) {
        setTitle("Quiz Over");
        setSize(600, 700); // Increased size for better readability
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Panel for Score
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.BLACK);
        scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel scoreLabel = new JLabel("Your Score: " + score + "/10", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 50)); // Large Font
        scoreLabel.setForeground(Color.WHITE);
        scorePanel.add(scoreLabel);

        add(scorePanel, BorderLayout.NORTH);

        // Text Area for displaying correct answers
        JTextArea resultsArea = new JTextArea();
        resultsArea.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24)); // Italic Font
        resultsArea.setForeground(Color.WHITE);
        resultsArea.setBackground(Color.BLACK);
        resultsArea.append("\n");

        // Display correct answers
        for (Map.Entry<String, String> entry : correctAnswers.entrySet()) {
            resultsArea.append("Q: " + entry.getKey() + "\nCorrect Answer: " + entry.getValue() + "\n\n");
        }

        resultsArea.setEditable(false);
        add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("Back to Topics");
        JButton tryAgainButton = new JButton("Try Again");

        Font buttonFont = new Font("Arial", Font.BOLD, 30);
        backButton.setFont(buttonFont);
        tryAgainButton.setFont(buttonFont);

        backButton.setBackground(Color.WHITE);
        tryAgainButton.setBackground(Color.WHITE);

        backButton.setForeground(Color.BLACK);
        tryAgainButton.setForeground(Color.BLACK);

        backButton.setFocusPainted(false);
        tryAgainButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TopicSelectionPage(""); // Go back to topic selection
                dispose();
            }
        });

        tryAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DifficultySelectionPage("", ""); // Restart quiz flow
                dispose();
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(tryAgainButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}

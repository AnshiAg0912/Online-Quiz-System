package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import ui.Question;  

public class QuizPage extends JFrame {
    private String subject, difficulty, phoneNo;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel, timerLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private Timer timer;
    private int timeLeft = 20; 
    private Map<String, String> correctAnswers = new HashMap<>();

    public QuizPage(String subject, String difficulty, String phoneNo) {
        this.subject = subject;
        this.difficulty = difficulty;
        this.phoneNo = phoneNo;

        setTitle("Quiz");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK); // Set background to black

        // Timer Label (Top Center)
        timerLabel = new JLabel("Time: " + timeLeft + "s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 50));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBounds(600, 20, 300, 60);
        add(timerLabel);

        // Question Label (Slightly Smaller Font)
        questionLabel = new JLabel("Loading Question...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 35)); // Reduced font size
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setBounds(200, 150, 1000, 80);
        add(questionLabel);

        // Options (Larger Radio Buttons)
        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();

        Font optionFont = new Font("Arial", Font.BOLD | Font.ITALIC, 32);
        optionA.setFont(optionFont);
        optionB.setFont(optionFont);
        optionC.setFont(optionFont);
        optionD.setFont(optionFont);

        optionA.setForeground(Color.WHITE);
        optionB.setForeground(Color.WHITE);
        optionC.setForeground(Color.WHITE);
        optionD.setForeground(Color.WHITE);

        optionA.setBackground(Color.BLACK);
        optionB.setBackground(Color.BLACK);
        optionC.setBackground(Color.BLACK);
        optionD.setBackground(Color.BLACK);

        optionA.setFocusPainted(false);
        optionB.setFocusPainted(false);
        optionC.setFocusPainted(false);
        optionD.setFocusPainted(false);

        optionA.setPreferredSize(new Dimension(30, 30)); // Larger size
        optionB.setPreferredSize(new Dimension(30, 30));
        optionC.setPreferredSize(new Dimension(30, 30));
        optionD.setPreferredSize(new Dimension(30, 30));

        optionA.setBounds(400, 300, 800, 60);
        optionB.setBounds(400, 370, 800, 60);
        optionC.setBounds(400, 440, 800, 60);
        optionD.setBounds(400, 510, 800, 60);

        add(optionA);
        add(optionB);
        add(optionC);
        add(optionD);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        // Next Button
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 30));
        nextButton.setBounds(600, 600, 250, 70);
        add(nextButton);

        // Fetch questions from the database
        questionList = fetchQuestions(subject, difficulty);
        if (questionList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions available!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Load the first question
        loadQuestion();

        // Start the timer
        startTimer();

        // Next Button Action
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                nextQuestion();
            }
        });

        setVisible(true);
    }

    private List<Question> fetchQuestions(String subject, String difficulty) {
        List<Question> questions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/quiz_db", "quiz", "quiz");
            String query = "SELECT * FROM APP.QUESTIONS WHERE SUBJECT=? AND DIFFICULTY=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, subject);
            pstmt.setString(2, difficulty);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Question question = new Question(
                    rs.getInt("QUESTION_ID"),
                    rs.getString("QUESTION_TEXT"),
                    rs.getString("OPTION_A"),
                    rs.getString("OPTION_B"),
                    rs.getString("OPTION_C"),
                    rs.getString("OPTION_D"),
                    rs.getString("CORRECT_ANSWER")
                );
                questions.add(question);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return questions;
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question question = questionList.get(currentQuestionIndex);
            questionLabel.setText(question.getQuestionText());
            optionA.setText("A) " + question.getOptionA());
            optionB.setText("B) " + question.getOptionB());
            optionC.setText("C) " + question.getOptionC());
            optionD.setText("D) " + question.getOptionD());
            optionsGroup.clearSelection();
        } else {
            endQuiz();
        }
    }

    private void checkAnswer() {
        if (currentQuestionIndex < questionList.size()) {
            Question question = questionList.get(currentQuestionIndex);
            String selectedAnswer = null;
            if (optionA.isSelected()) selectedAnswer = "A";
            if (optionB.isSelected()) selectedAnswer = "B";
            if (optionC.isSelected()) selectedAnswer = "C";
            if (optionD.isSelected()) selectedAnswer = "D";

            if (selectedAnswer != null && selectedAnswer.equals(question.getCorrectAnswer())) {
                score++;
            }

            correctAnswers.put(question.getQuestionText(), getAnswerText(question, question.getCorrectAnswer()));
        }
    }

    private String getAnswerText(Question question, String correctOption) {
        if (correctOption.equals("A")) return question.getOptionA();
        if (correctOption.equals("B")) return question.getOptionB();
        if (correctOption.equals("C")) return question.getOptionC();
        if (correctOption.equals("D")) return question.getOptionD();
        return "Unknown";
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questionList.size()) {
            loadQuestion();
            restartTimer();
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time: " + timeLeft + "s");
                } else {
                    checkAnswer();
                    nextQuestion();
                }
            }
        }, 1000, 1000);
    }

    private void restartTimer() {
        timeLeft = 20;
        timerLabel.setText("Time: " + timeLeft + "s");
    }

    private void endQuiz() {
        timer.cancel();
        dispose();
        new QuizOver(score, correctAnswers);
    }
}


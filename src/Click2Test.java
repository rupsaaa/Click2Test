
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
public class Click2Test extends JFrame implements ActionListener {

    // Panels
    JPanel welcomePanel, testPanel, optionsPanel, buttonsPanel, questionNavPanel, resultPanel;
    JLabel welcomeLabel, instructionLabel, confirmText, timerLabel, questionLabel, scoreLabel;
    JCheckBox confirmCheckBox;
    JButton startButton, nextButton, prevButton, submitButton;
    JRadioButton option1, option2, option3, option4;
    ButtonGroup bg;

    JButton[] qButtons; // navigation buttons for questions
    JLabel ratingLabel;
    JLabel[] stars = new JLabel[5];
    int selectedRating = 0;
    JTextArea feedbackArea;
    JButton submitFeedbackButton;
    Timer timer;
    int totalTime = 15 * 60;
    ArrayList<Question> questions = new ArrayList<>();
    String[] userAnswers;
    boolean[] visited;
    int currentQuestion = 0;

    CardLayout cardLayout;
    JPanel mainPanel;
    String studentName;
    String rollNo;

    public Click2Test() {
        loadQuestionsFromDatabase();
        setTitle("Click2Test: An Online Test Application");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // ---------------- WELCOME PANEL ----------------
        welcomePanel = new JPanel(null);
        welcomePanel.setBackground(new Color(176, 196, 222));
        welcomeLabel = new JLabel("WELCOME TO THE JAVA ONLINE TEST!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setBounds(40, 20, 700, 40);
        welcomePanel.add(welcomeLabel);


        timerLabel = new JLabel("TIME: 00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 35));
        timerLabel.setBounds(700, 20, 250, 50);
        welcomePanel.add(timerLabel);

        String instructions = "<html><b>GENERAL INSTRUCTIONS:</b><br><br>" +
                "• You will have 15 minutes to complete the test.<br>" +
                "• There are 15 questions.<br>" +
                "• Each question carries 1 point.No negative marking.<br>" +
                "• The right side navigation panel consists of the all the question numbers.<br>" +
                "• Green means Attempted,Red means Visited but Unattempted Grey means Not Visited.<br>" +
                "• Use 'Next' and 'Previous' buttons to navigate.<br>" +
                "• Click 'Submit' to finish the test.<br>" +
                "• Time will automatically stop when it reaches zero.<br><br><br></html>";
        instructionLabel = new JLabel(instructions);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionLabel.setBounds(40, 100, 850, 220);
        welcomePanel.add(instructionLabel);


        confirmCheckBox = new JCheckBox("I hereby confirm that I have read and agree to the above instructions.");
        confirmCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
        confirmCheckBox.setBounds(40, 330, 800, 30);
        welcomePanel.add(confirmCheckBox);

        confirmText = new JLabel("CLICK THE START BUTTON TO START THE TEST.");
        confirmText.setFont(new Font("Arial", Font.BOLD, 24));
        confirmText.setBounds(40, 390, 600, 30);
        welcomePanel.add(confirmText);

        startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(new Color(46, 204, 113));
        startButton.setForeground(Color.BLACK);
        startButton.setBounds(660, 385, 120, 40);
        startButton.setEnabled(false);
        startButton.addActionListener(this);
        welcomePanel.add(startButton);

        confirmCheckBox.addActionListener(e -> startButton.setEnabled(confirmCheckBox.isSelected()));

        // ---------------- TEST PANEL ----------------
        testPanel = new JPanel(new BorderLayout(10, 10));
        testPanel.setBackground(new Color(176, 196, 222));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        questionLabel = new JLabel("Click Start to begin");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(questionLabel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.EAST);
        testPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridLayout(1, 2, 20, 0));
        centerWrapper.setOpaque(false);

        JPanel optionsBox = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsBox.setBackground(Color.WHITE);
        optionsBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        option1.setFont(new Font("Arial", Font.PLAIN, 23));
        option2.setFont(new Font("Arial", Font.PLAIN, 23));
        option3.setFont(new Font("Arial", Font.PLAIN, 23));
        option4.setFont(new Font("Arial", Font.PLAIN, 23));

        bg = new ButtonGroup();
        bg.add(option1);
        bg.add(option2);
        bg.add(option3);
        bg.add(option4);

        optionsBox.add(option1);
        optionsBox.add(option2);
        optionsBox.add(option3);
        optionsBox.add(option4);

        questionNavPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        questionNavPanel.setBackground(Color.WHITE);
        questionNavPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        JPanel navWrapper = new JPanel(new BorderLayout());
        navWrapper.setBackground(Color.WHITE);
        navWrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
        JLabel navHeading = new JLabel("NOTE: Green = Attempted | Red = Visited but Unattempted | Grey = Not Visited");
        navHeading.setFont(new Font("Arial", Font.PLAIN, 18));
        navHeading.setHorizontalAlignment(SwingConstants.CENTER);
        navWrapper.add(navHeading, BorderLayout.NORTH);
        qButtons = new JButton[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            qButtons[i] = new JButton(String.valueOf(i + 1));
            qButtons[i].setFont(new Font("Arial", Font.BOLD, 22));
            qButtons[i].setPreferredSize(new Dimension(100, 80));
            qButtons[i].setBackground(Color.LIGHT_GRAY);
            qButtons[i].setOpaque(true);
            qButtons[i].setBorderPainted(false);
            int idx = i;
            qButtons[i].addActionListener(e -> {
                saveAnswer();
                currentQuestion = idx;
                loadQuestion(currentQuestion);
            });
            questionNavPanel.add(qButtons[i]);
        }
        navWrapper.add(questionNavPanel, BorderLayout.CENTER);

        centerWrapper.add(optionsBox);
        centerWrapper.add(navWrapper);
        testPanel.add(centerWrapper, BorderLayout.CENTER);

        buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);

        prevButton = new JButton("Previous");
        prevButton.setFont(new Font("Arial", Font.PLAIN, 18));
        prevButton.setBackground(new Color(255, 215, 0));
        prevButton.setForeground(Color.BLACK);
        prevButton.addActionListener(this);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.PLAIN, 18));
        nextButton.setBackground(new Color(30, 144, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        submitButton.setBackground(new Color(231, 76, 60));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);

        buttonsPanel.add(prevButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(submitButton);
        testPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // ---------------- RESULT PANEL ----------------
        resultPanel = new JPanel(new BorderLayout(20, 20));
        resultPanel.setBackground(new Color(176, 196, 222));

        scoreLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 36));
        resultPanel.add(scoreLabel, BorderLayout.NORTH);

        mainPanel.add(welcomePanel, "welcome");
        mainPanel.add(testPanel, "test");
        mainPanel.add(resultPanel, "result");

        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
       // getStudentDetails();
        setVisible(true);
    }
   /* void getStudentDetails() {

        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();

        Object[] message = {
                "Enter Your Name:", nameField,
                "Enter Your Roll Number:", rollField
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                "Student Details",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {

            studentName = nameField.getText();
            rollNo = rollField.getText();

            if (studentName.isEmpty() || rollNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Name and Roll Number");
                getStudentDetails(); // ask again
            }

        } else {
            System.exit(0);
        }
    }*/

    void startTimer() {
        timer = new Timer(1000, e -> {
            totalTime--;
            timerLabel.setText("TIME: " + formatTime(totalTime));
            if (totalTime <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Time's up!");
                showScoreAndExit();
            }
        });
        timer.start();
    }

    String formatTime(int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    private void loadQuestionsFromDatabase() {

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM questions ORDER BY RAND() LIMIT 15";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Question q = new Question(
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option")
                );
                questions.add(q);
            }

            con.close();

            // Initialize arrays AFTER loading questions
            userAnswers = new String[questions.size()];
            visited = new boolean[questions.size()];

            System.out.println("Questions Loaded: " + questions.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadQuestion(int index) {

        Question q = questions.get(index);

        questionLabel.setText(q.questionText + " (1 point)");
        option1.setText(q.optionA);
        option2.setText(q.optionB);
        option3.setText(q.optionC);
        option4.setText(q.optionD);

        bg.clearSelection();

        if (userAnswers[index] != null) {
            switch (userAnswers[index]) {
                case "A": option1.setSelected(true); break;
                case "B": option2.setSelected(true); break;
                case "C": option3.setSelected(true); break;
                case "D": option4.setSelected(true); break;
            }
        }

        visited[index] = true;
        updateNavColors();
    }

    void saveAnswer() {
        if (option1.isSelected()) userAnswers[currentQuestion] = "A";
        else if (option2.isSelected()) userAnswers[currentQuestion] = "B";
        else if (option3.isSelected()) userAnswers[currentQuestion] = "C";
        else if (option4.isSelected()) userAnswers[currentQuestion] = "D";
        else userAnswers[currentQuestion] = null;

        updateNavColors();
    }

    void updateNavColors() {
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] != null) {
                qButtons[i].setBackground(new Color(46, 204, 113));
            } else if (visited[i]) {
                qButtons[i].setBackground(new Color(231, 76, 60));
            } else {
                qButtons[i].setBackground(Color.LIGHT_GRAY);
            }
        }
    }
    void updateStarColors() {
        for (int i = 0; i < 5; i++) {
            if (i < selectedRating) {
                stars[i].setForeground(Color.ORANGE);
            } else {
                stars[i].setForeground(Color.GRAY);
            }
        }
    }
    void saveResultToDatabase(int score) {

        try {

            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO results (name, roll_no, score) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, studentName);
            ps.setString(2, rollNo);
            ps.setInt(3, score);

            ps.executeUpdate();

            con.close();

            System.out.println("Result Saved Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void showScoreAndExit() {
        int correct = 0, attempted = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] != null) attempted++;
            if (questions.get(i).correctOption.equals(userAnswers[i])) correct++;
        }
        int wrong = attempted - correct;
        saveResultToDatabase(correct);
        scoreLabel.setText("Your Score: " + correct + "/" + questions.size());

        String[] columns = {"Total Questions", "Attempted", "Correct", "Wrong"};
        Object[][] data = {{questions.size(), attempted, correct, wrong}};
        JTable table = new JTable(data, columns);
        table.setFont(new Font("Arial", Font.BOLD, 22));
        table.setRowHeight(50);
        table.setEnabled(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 100));
        scrollPane.setMaximumSize(new Dimension(1600, 100));

        JPanel centerContainer = new JPanel();
        centerContainer.setOpaque(false);
        centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));

        centerContainer.add(scrollPane);
        centerContainer.add(Box.createVerticalStrut(30));

        // ---------------- RATING PANEL ----------------
        JPanel ratingPanel = new JPanel();
        ratingPanel.setOpaque(false);
        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));
        ratingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ratingLabel = new JLabel("Rate Your Experience");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 26));
        ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ratingPanel.add(ratingLabel);

        ratingPanel.add(Box.createVerticalStrut(10));

        // --- RATING STATUS LABEL ---
        JLabel ratingStatusLabel = new JLabel(" ");

        ratingStatusLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 30));
        ratingStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Stars Panel
        JPanel starsPanel = new JPanel();
        starsPanel.setOpaque(false);
        for (int i = 0; i < 5; i++) {
            stars[i] = new JLabel("\u2605");
            stars[i].setFont(new Font("SansSerif", Font.BOLD, 55));
            stars[i].setForeground(new Color(200, 200, 200));
            stars[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            int index = i;
            stars[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedRating = index + 1;
                    updateStarColors();

                    // UPDATE STATUS TEXT AND EMOJIS
                    switch (selectedRating) {
                        case 1:
                            ratingStatusLabel.setText("Worst 😠");
                            ratingStatusLabel.setForeground(new Color(198, 30, 30)); // Red
                            break;
                        case 2:
                            ratingStatusLabel.setText("Bad ☹️");
                            ratingStatusLabel.setForeground(new Color(255, 128, 9)); // Orange
                            break;
                        case 3:
                            ratingStatusLabel.setText("Average 😐");
                            ratingStatusLabel.setForeground(new Color(248, 255, 0)); // Yellow
                            break;
                        case 4:
                            ratingStatusLabel.setText("Good 🙂");
                            ratingStatusLabel.setForeground(new Color(6, 46, 211)); // Blue
                            break;
                        case 5:
                            ratingStatusLabel.setText("Excellent 🤩");
                            ratingStatusLabel.setForeground(new Color(108, 255, 5)); // Green
                            break;
                    }
                }
            });
            starsPanel.add(stars[i]);
        }

        ratingPanel.add(starsPanel);
        ratingPanel.add(ratingStatusLabel); // Add status right below stars
        ratingPanel.add(Box.createVerticalStrut(20));

        feedbackArea = new JTextArea(4, 25);
        feedbackArea.setFont(new Font("Arial", Font.PLAIN, 18));
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setPreferredSize(new Dimension(350, 70));
        feedbackArea.setMaximumSize(new Dimension(350, 70));
        feedbackArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        ratingPanel.add(feedbackArea);

        ratingPanel.add(Box.createVerticalStrut(15));

        submitFeedbackButton = new JButton("Submit Feedback");
        submitFeedbackButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitFeedbackButton.setBackground(new Color(46, 204, 113));
        submitFeedbackButton.setForeground(Color.BLACK);
        submitFeedbackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitFeedbackButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thank you for your feedback!\nRating: " + selectedRating + " Stars");
            feedbackArea.setText("");
            ratingStatusLabel.setText(" "); // Reset text
            selectedRating = 0;
            updateStarColors();
        });

        ratingPanel.add(submitFeedbackButton);
        centerContainer.add(ratingPanel);
        centerContainer.add(Box.createVerticalGlue());

        resultPanel.add(centerContainer, BorderLayout.CENTER);

        JButton close = new JButton("CLOSE");
        close.setFont(new Font("Arial", Font.BOLD, 18));
        close.setBackground(new Color(231, 76, 60));
        close.setForeground(Color.WHITE);
        close.addActionListener(e -> System.exit(0));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(close);
        resultPanel.add(bottomPanel, BorderLayout.SOUTH);

        cardLayout.show(mainPanel, "result");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            cardLayout.show(mainPanel, "test"); // switch to test panel
            currentQuestion = 0;
            loadQuestion(currentQuestion);
            startTimer();
        } else if (e.getSource() == nextButton) {
            saveAnswer();
            currentQuestion++;
            if (currentQuestion >= questions.size()) currentQuestion = 0;
            loadQuestion(currentQuestion);
        } else if (e.getSource() == prevButton) {
            saveAnswer();
            currentQuestion--;
            if (currentQuestion < 0) currentQuestion = questions.size() - 1;
            loadQuestion(currentQuestion);
        } else if (e.getSource() == submitButton) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to submit the test?",
                    "Confirm Submission",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                saveAnswer();
                timer.stop();
                showScoreAndExit();
            }

        }
    }

    public static void main(String[] args) {


            new LoaderScreen();
        }
    }



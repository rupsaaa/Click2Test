import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Click2Test extends JFrame implements ActionListener {

    // Panels
    JPanel welcomePanel, testPanel, optionsPanel, buttonsPanel, questionNavPanel, resultPanel;
    JLabel welcomeLabel, instructionLabel, confirmText, timerLabel, questionLabel, scoreLabel;
    JCheckBox confirmCheckBox;
    JButton startButton, nextButton, prevButton, submitButton;
    JRadioButton option1, option2, option3, option4;
    ButtonGroup bg;

    JButton[] qButtons; // navigation buttons for questions

    Timer timer;
    int totalTime = 5 * 60;
    String[][] questions = {
            {"1. Which keyword is used to inherit a class in Java?", "this", "super", "extends", "implements", "C"},
            {"2. Which of these is not a Java feature?", "Object-oriented", "Use of pointers", "Portable", "Robust", "B"},
            {"3. What is the default value of a boolean variable?", "true", "false", "null", "0", "B"},
            {"4. Which method is the entry point of a Java program?", "start()", "main()", "run()", "init()", "B"},
            {"5. Which of the following is not an access modifier?", "public", "private", "protected", "package", "D"},
            {"6. Which package contains the Scanner class?", "java.io", "java.util", "java.lang", "java.net", "B"},
            {"7. Which operator is used for string concatenation in Java?", "+", "-", "*", "&", "A"},
            {"8. Which exception is thrown when array is accessed out of bounds?", "IOException", "NullPointerException", "ArrayIndexOutOfBoundsException", "ClassNotFoundException", "C"},
            {"9. Which collection class allows duplicates?", "HashSet", "TreeSet", "ArrayList", "EnumSet", "C"},
            {"10. Which keyword is used to prevent method overriding?", "static", "const", "final", "abstract", "C"},
            {"11. What is the size of int data type in Java?", "2 bytes", "4 bytes", "8 bytes", "Depends on OS", "B"},
            {"12. Which keyword is used to create an object in Java?", "class", "new", "create", "object", "B"},
            {"13. Which method is used to get the length of an array?", "size()", "length", "length()", "getSize()", "B"},
            {"14. Which of these is a wrapper class in Java?", "int", "Integer", "string", "char", "B"},
            {"15. Which loop is guaranteed to execute at least once?", "for", "while", "do-while", "foreach", "C"}

        };
    String[] userAnswers = new String[questions.length];
    boolean[] visited = new boolean[questions.length];
    int currentQuestion = 0;

    CardLayout cardLayout;
    JPanel mainPanel;

    public Click2Test() {
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
                "• You will have 5 minutes to complete the test.<br>" +
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
        testPanel.setBackground(new Color(176, 196, 222)); // bluish-grey

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
        qButtons = new JButton[questions.length];
        for (int i = 0; i < questions.length; i++) {
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

        setVisible(true);
    }

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

    void loadQuestion(int index) {
        questionLabel.setText(questions[index][0] + " (1 point)");
        option1.setText(questions[index][1]);
        option2.setText(questions[index][2]);
        option3.setText(questions[index][3]);
        option4.setText(questions[index][4]);

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
        for (int i = 0; i < questions.length; i++) {
            if (userAnswers[i] != null) {
                qButtons[i].setBackground(new Color(46, 204, 113));
            } else if (visited[i]) {
                qButtons[i].setBackground(new Color(231, 76, 60));
            } else {
                qButtons[i].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    void showScoreAndExit() {
        int correct = 0, attempted = 0;
        for (int i = 0; i < questions.length; i++) {
            if (userAnswers[i] != null) attempted++;
            if (questions[i][5].equals(userAnswers[i])) correct++;
        }
        int wrong = attempted - correct;

        scoreLabel.setText("Your Score: " + correct + "/" + questions.length);

        String[] columns = {"Total Questions", "Attempted", "Correct", "Wrong"};
        Object[][] data = {{questions.length, attempted, correct, wrong}};
        JTable table = new JTable(data, columns);
        table.setFont(new Font("Arial", Font.BOLD, 22));
        table.setRowHeight(50);
        table.setEnabled(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        JScrollPane scrollPane = new JScrollPane(table);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        
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
            if (currentQuestion >= questions.length) currentQuestion = 0;
            loadQuestion(currentQuestion);
        } else if (e.getSource() == prevButton) {
            saveAnswer();
            currentQuestion--;
            if (currentQuestion < 0) currentQuestion = questions.length - 1;
            loadQuestion(currentQuestion);
        } else if (e.getSource() == submitButton) {
            saveAnswer();
            timer.stop();
            showScoreAndExit();
        }
    }

    public static void main(String[] args) {
        new Click2Test();
    }
}

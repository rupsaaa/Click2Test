import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentDetailsUI extends JFrame {

    JTextField nameField, rollField;
    JLabel nameError, rollError;

    // ✅ VALIDATION METHODS
    boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    boolean isValidRoll(String roll) {
        return roll.matches("\\d{6}");
    }

    // ✅ CHECK IF ROLL EXISTS
    boolean isRollExists(String roll) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM results WHERE roll_no = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, roll);
            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();
            con.close();
            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ CHECK SAME USER (NAME + ROLL)
    boolean isSameUserExists(String name, String roll) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM results WHERE name = ? AND roll_no = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, roll);
            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();
            con.close();
            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public StudentDetailsUI() {

        setTitle("Student Login");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(176, 196, 222));
        panel.setLayout(null);

        // 🎓 LOGO
        JLabel logo = new JLabel("🎓");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        logo.setBounds(210, 20, 100, 60);
        panel.add(logo);

        JLabel title = new JLabel("Student Details");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(150, 80, 250, 30);
        panel.add(title);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setBounds(80, 140, 100, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 140, 200, 30);
        panel.add(nameField);

        nameError = new JLabel("");
        nameError.setForeground(Color.RED);
        nameError.setBounds(200, 170, 250, 20);
        panel.add(nameError);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setFont(new Font("Arial", Font.BOLD, 18));
        rollLabel.setBounds(80, 190, 100, 25);
        panel.add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(200, 190, 200, 30);
        panel.add(rollField);

        rollError = new JLabel("");
        rollError.setForeground(Color.RED);
        rollError.setBounds(200, 220, 250, 20);
        panel.add(rollError);

        JButton nextBtn = new JButton("Next");
        nextBtn.setBounds(280, 260, 120, 40);
        nextBtn.setBackground(new Color(46, 204, 113));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(100, 260, 120, 40);
        cancelBtn.setBackground(new Color(231, 76, 60));

        panel.add(nextBtn);
        panel.add(cancelBtn);

        // ✅ NEXT BUTTON LOGIC (FULL UPDATED)
        nextBtn.addActionListener(e -> {

            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();

            // RESET UI
            nameError.setText("");
            rollError.setText("");
            nameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            rollField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            boolean valid = true;

            // EMPTY CHECK
            if (name.isEmpty()) {
                nameError.setText("Name is required");
                nameField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                valid = false;
            }

            if (roll.isEmpty()) {
                rollError.setText("Roll number is required");
                rollField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                valid = false;
            }

            // FORMAT VALIDATION
            if (!name.isEmpty() && !isValidName(name)) {
                nameError.setText("Only alphabets allowed");
                nameField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                valid = false;
            }

            if (!roll.isEmpty() && !isValidRoll(roll)) {
                rollError.setText("Must be exactly 6 digits");
                rollField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                valid = false;
            }

            if (!valid) return;

            // ✅ SAME USER CHECK
            if (isSameUserExists(name, roll)) {

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "You have already taken the test.\nDo you want to appear again?",
                        "Already Attempted",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            // ✅ DUPLICATE ROLL CHECK
            else if (isRollExists(roll)) {

                JOptionPane.showMessageDialog(
                        this,
                        "This roll number is already used by another user!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                rollField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                return;
            }

            // ✅ SUCCESS → OPEN MAIN APP
            dispose();

            Click2Test app = new Click2Test();
            app.studentName = name;
            app.rollNo = roll;
            app.setVisible(true);
        });

        cancelBtn.addActionListener(e -> System.exit(0));

        add(panel);
        setVisible(true);
    }
}
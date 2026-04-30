import javax.swing.*;
import java.awt.*;

public class LoaderScreen {

    public LoaderScreen() {
        JFrame frame = new JFrame("Starting Application...");
        frame.setSize(400, 120);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setFont(new Font("Arial", Font.BOLD, 14));

        frame.add(bar);
        frame.setVisible(true);

        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    bar.setValue(i);
                    Thread.sleep(30);
                }

                frame.dispose();

                // OPEN STUDENT DETAILS WINDOW
                new StudentDetailsUI();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
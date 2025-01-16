package hms;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockPanel extends JPanel {
    private JLabel clockLabel;

    public ClockPanel() {
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 16));
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setLayout(new BorderLayout());
        add(clockLabel, BorderLayout.CENTER);

        // Start the clock
        new Timer(1000, e -> updateClock()).start();
        updateClock(); // Initial update
        setVisible(true);
    }

    private void updateClock() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        clockLabel.setText(dateFormat.format(new Date()));
    }
    public static void main(String [] args){
      //  new ClockPanel();
    }
}

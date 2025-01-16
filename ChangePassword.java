package hms;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ChangePassword extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeBox;

    public ChangePassword() {
        // Add a clock panel below the image
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.SOUTH);
        // Set up frame
        setTitle("Change Password");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(12, 2, 10, 10)); // Simplified layout

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Identity Required", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 24));
        add(welcomeLabel);

        // Username Field
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        // User Type Field
        JLabel userTypeLabel = new JLabel("User Type:");
        add(userTypeLabel);

        userTypeBox = new JComboBox<>(new String[]{"Administrator"});
        add(userTypeBox);

        // Change Password Button
        JButton changePasswordButton = new JButton("Change Password");
        add(changePasswordButton);

        changePasswordButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String userType = (String) userTypeBox.getSelectedItem();

            try {
                boolean success = updatePassword(username, password, userType);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Password updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });
        setVisible(true);
    }

    private boolean updatePassword(String username, String oldPassword, String userType) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.connectToDatabase();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Failed to connect to database");
                return false;
            }

            // Check if user exists
            String query = "UPDATE users SET password = ? WHERE username = ? AND password = ? AND user_type = ?";
            ps = conn.prepareStatement(query);

            // Get new password
            String newPassword = JOptionPane.showInputDialog("Enter new Password:");
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.setString(3, oldPassword);
            ps.setString(4, userType);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChangePassword frame = new ChangePassword();

        });
    }

}

package hms;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class addDisease extends JFrame {

    private JTextField diseaseNameField;
    private JTextArea descriptionArea;

    public addDisease() {

        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.NORTH);

        setTitle("Add Disease");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel maiPanel = new JPanel();

        // Main panel with vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Doctor Name
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(new JLabel("Disease Name: "), BorderLayout.WEST);
        diseaseNameField = new JTextField();
        namePanel.add(diseaseNameField, BorderLayout.CENTER);
        mainPanel.add(namePanel);
        mainPanel.add(Box.createVerticalStrut(10)); // Add spacing

        // Doctor Specialization
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.add(new JLabel("Disease Discription: "), BorderLayout.WEST);
        descriptionArea = new JTextArea(3,5);
        JScrollPane scrollPane = new JScrollPane(descriptionArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descriptionPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(descriptionPanel );
        mainPanel.add(Box.createVerticalStrut(10)); // Add spacing

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);

        // Add action listeners
        saveButton.addActionListener(e -> {
            String diseaseName = getDisease();
            String discription = getDescription();
            if (diseaseName.isEmpty() || discription.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields correctly!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } 


            boolean success = saveDiseaseToDatabase(diseaseName, discription);
            if (success) {
                JOptionPane.showMessageDialog(this, "Disease Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error Adding Disease!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        
        });

        cancelButton.addActionListener(e -> dispose());

        // Add main panel to frame
        add(mainPanel);
        setLocationRelativeTo(null); // Center the frame on screen
        setVisible(true);
    }

    public String getDisease() {
        return diseaseNameField.getText().trim();
    }

    public String getDescription() {
        return (String) descriptionArea.getText();
    }

    private boolean saveDiseaseToDatabase(String disease, String description) {
        String query = "INSERT INTO disease (disease_name, description) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.connectToDatabase();  // Use the connection from your class
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters for the prepared statement
            statement.setString(1, disease);
            statement.setString(2, description);

            // Execute the insert query
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;  // Return true if doctor was successfully added
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there was an error
        }
    }

    public static void main(String[] args) {
        new addDisease();
    }
}

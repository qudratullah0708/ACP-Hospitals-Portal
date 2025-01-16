package hms;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class addDoctor extends JFrame {


    private JTextField nameField;
    private JComboBox<String> specializationDropDown;

    public addDoctor() {

        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.NORTH);


        setTitle("Add Doctor");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new GridLayout(4, 1, 5, 5));

        JPanel mainPanel = new JPanel();
        // Form Fields
        // Add Doctor Name field
        mainPanel.add(new JLabel("Doctor Name:"));
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30)); // Set fixed dimensions
        mainPanel.add(nameField);

        // Add Specialization field
        mainPanel.add(new JLabel("Specialization:"));
        JTextField specializationField = new JTextField();
        specializationField.setPreferredSize(new Dimension(200, 30)); // Set fixed dimensions
        mainPanel.add(specializationField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);

        // Adding Action Listeners
        saveButton.addActionListener(e -> {
            String doctorName = getDoctorName();
            String doctorSpecialization = getDoctorSpecialization();
            if (doctorName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Doctor Name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (doctorSpecialization.equals("Select Specialization")) {
                JOptionPane.showMessageDialog(this, "Select correct Specialization!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = saveDoctorToDatabase(doctorName, doctorSpecialization);
            if (success) {
                JOptionPane.showMessageDialog(this, "Doctor Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error Adding Doctor!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        add(mainPanel);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public String getDoctorName() {
        return nameField.getText();
    }

    public String getDoctorSpecialization() {
        return (String) specializationDropDown.getSelectedItem();
    }

    // Method to save doctor to the database
    private boolean saveDoctorToDatabase(String name, String specialization) {
        String query = "INSERT INTO doctors (doctor_name, specialization) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.connectToDatabase(); // Use the connection from your class
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters for the prepared statement
            statement.setString(1, name);
            statement.setString(2, specialization);

            // Execute the insert query
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if doctor was successfully added
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public static void main(String[] args) {
      // new addDoctor();
    }
}

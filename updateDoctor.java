package hms;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class updateDoctor extends JFrame {


    private JTextField idField;
    private JTextField nameField;
    private JTextField specializationField;

    public updateDoctor() {


        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.NORTH);

        setTitle("Update Doctor Record");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel mainJPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        add(mainJPanel);

        mainJPanel.add(new JLabel("Enter Doctor ID:"));
        idField = new JTextField();
        mainJPanel.add(idField);

        JButton findButton = new JButton("Find");
        JButton cancelButton = new JButton("Cancel");

        mainJPanel.add(findButton);
        mainJPanel.add(cancelButton);

        findButton.addActionListener(e -> {
            String doctorID = getDoctorID().trim();

            if (doctorID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Doctor ID is required!", "Warning", JOptionPane.WARNING_MESSAGE);
            }

            try {
                if (checkDoctorExists(doctorID)) {
                    // Prompt for updated values
                    String doctorName = JOptionPane.showInputDialog(this, "Enter Updated name:");
                    String specialization = JOptionPane.showInputDialog(this, "Enter updated specialization:");

                    if ((doctorName != null && !doctorName.trim().isEmpty()) ||
                            (specialization != null && !specialization.trim().isEmpty())) {

                        updateDoctorDatabase(doctorID, doctorName, specialization);
                        JOptionPane.showMessageDialog(this, "Record Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);


                    } else {
                        JOptionPane.showMessageDialog(this, "No changes made! Both name and specialization are empty.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "No doctor found with ID " + doctorID, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }

    public String getDoctorID() {
        return idField.getText();
    }

    private boolean checkDoctorExists(String doctorID) throws SQLException {
        String query = "SELECT COUNT(*) FROM doctor WHERE id = ?";
        try (Connection connection = DatabaseConnection.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, Integer.parseInt(doctorID));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Check if at least one record exists
            }
        }
        return false;
    }

    private void updateDoctorDatabase(String doctorID, String doctorName, String specialization) throws SQLException {
        String query = "UPDATE doctor SET ";
        boolean updateName = doctorName != null && !doctorName.trim().isEmpty();
        boolean updateSpecialization = specialization != null && !specialization.trim().isEmpty();

        // Build query dynamically
        if (updateName) query += "name = ?";
        if (updateSpecialization) query += (updateName ? ", " : "") + "specialization = ?";
        query += " WHERE id = ?";

        try (Connection connection = DatabaseConnection.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            int paramIndex = 1;

            // Set parameters dynamically based on what was updated
            if (updateName) {
                statement.setString(paramIndex++, doctorName);
            }
            if (updateSpecialization) {
                statement.setString(paramIndex++, specialization);
            }
            // Always set doctor ID at the end
            statement.setInt(paramIndex, Integer.parseInt(doctorID)); // Set the ID for WHERE clause


            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Doctor record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update doctor record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
         new updateDoctor();
    }
}

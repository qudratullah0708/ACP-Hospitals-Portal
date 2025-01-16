package hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class updatePateint extends JFrame {
    private JTextField IdField;
    private JTextField nameField;
    private JTextField fatherField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private JTextField dobField;
    private JComboBox<String> doctorDropdown;
    private JTextArea diseaseArea;
    private JTextArea  prescriptionArea;

    public updatePateint(){
        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.CENTER);

        setTitle("Update Patient Record");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel mainPanel = new JPanel();
        //    Form Filds are

        add(new JLabel("Patient ID: "));
        IdField = new JTextField();
        IdField.setPreferredSize(new Dimension(100, 30)); // Set preferred width and height
        add(IdField);



        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton FindButton = new JButton("Find");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(FindButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);
        add(mainPanel);

        FindButton.addActionListener(e->{
            String PatientID = getPatientID();
            if (PatientID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Patient ID is required!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            try {
                if (checkPatientExists(PatientID)) {
            // Prompt for updated values
            String patientName = JOptionPane.showInputDialog(this, "Enter Updated Name:");
            String fatherName = JOptionPane.showInputDialog(this, "Enter Updated Father's Name:");
            String disease = JOptionPane.showInputDialog(this, "Enter Updated Disease:");
            String prescription = JOptionPane.showInputDialog(this, "Enter Updated Prescription:");

            if ((patientName != null && !patientName.trim().isEmpty()) ||
                    (fatherName != null && !fatherName.trim().isEmpty()) ||
                    (disease != null && !disease.trim().isEmpty()) ||
                    (prescription != null && !prescription.trim().isEmpty())) {

                updatePatientDatabase(PatientID, patientName, fatherName, disease, prescription);
                JOptionPane.showMessageDialog(this, "Record Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "No changes made! All fields are empty.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "No patient found with ID " + PatientID, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

 });

        cancelButton.addActionListener(e->dispose());


        setVisible(true);

    }


    public String getPatientID(){
        return IdField.getText();
    }

    private boolean checkPatientExists(String patientID) throws SQLException {
        String query = "SELECT COUNT(*) FROM patient WHERE id = ?";
        try (Connection connection = DatabaseConnection.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, Integer.parseInt(patientID));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Check if at least one record exists
            }
        }
        return false;
    }

    private void updatePatientDatabase(String patientID, String patientName,String FatherName, String Disease, String Prescription) {
        String query = "UPDATE patient set ";
        StringBuilder queryBuilder = new StringBuilder("UPDATE patient SET ");
        boolean updatedName = patientName != null && !patientName.trim().isEmpty();
        boolean updatedFatherName = FatherName != null && !FatherName.trim().isEmpty();
        boolean updatedDisease = Disease != null && !Disease.trim().isEmpty();
        boolean updatedPrescription = Prescription != null && !Prescription.trim().isEmpty();

        // Building the dynamic query
        if (updatedName) ((StringBuilder) queryBuilder).append("name = ?, ");
        if (updatedFatherName) queryBuilder.append("father_name = ?, ");
        if (updatedDisease) queryBuilder.append("disease = ?, ");
        if (updatedPrescription) queryBuilder.append("prescription = ?, ");

        // Remove the trailing comma and space
        query = queryBuilder.substring(0, queryBuilder.length() - 2);
        query += " WHERE id = ?";

        try (Connection connection = DatabaseConnection.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            int paramIndex = 1;
            if (updatedName) statement.setString(paramIndex++, patientName);
            if (updatedFatherName) statement.setString(paramIndex++, FatherName);
            if (updatedDisease) statement.setString(paramIndex++, Disease);
            if (updatedPrescription) statement.setString(paramIndex++, Prescription);
            statement.setInt(paramIndex, Integer.parseInt(patientID)); // Set the ID for WHERE clause

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Patient record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update patient record.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
        public static void main(String[] args){

        new updatePateint();
    }

}




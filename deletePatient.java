package hms;
import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class deletePatient extends JFrame {

    private JTextField patientIdField;


    public deletePatient() {
        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.CENTER);

        setTitle("Delete Patient");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Enter Patient ID:"));
        patientIdField = new JTextField();
        inputPanel.add(patientIdField);


        add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        deleteButton.addActionListener(e->{
            String PatientId = patientIdField.getText();
            if(PatientId.isEmpty()){
                JOptionPane.showMessageDialog(this,"Please Enter Patient ID ", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                deleteFromDatabase(PatientId);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);

    }

    private void deleteFromDatabase(String id) throws SQLException {
        String query = "delete from patient where id=?";
        try (Connection connection = DatabaseConnection.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, Integer.parseInt(id));
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Patient record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No patient found with ID " + id, "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    public  static  void  main(String[] args){
        new deletePatient();
    }
}

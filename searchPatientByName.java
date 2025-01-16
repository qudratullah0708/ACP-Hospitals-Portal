package hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.*;

public class searchPatientByName extends JFrame {
    private JTextField nameField;

    public searchPatientByName() {
        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.CENTER);

        setTitle("Search Patient By Name");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        add(inputPanel, BorderLayout.NORTH);

        inputPanel.add(new JLabel("Enter Patient Name: "));
        nameField = new JTextField(20);
        inputPanel.add(nameField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);

        String[] columnNames = {"Patient_name", "Father_name", "Gender", "Date_of_Birth", "Doctor", "Disease", "Prescription"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String patientName = getPatientName().toLowerCase().trim();
            tableModel.setRowCount(0); // Clear previous results
            if (patientName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No Patients Found", "INFO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    searchPatientByNameInDatabase(patientName, tableModel);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public String getPatientName() {
        return nameField.getText();
    }

    private void searchPatientByNameInDatabase(String patientName, DefaultTableModel tableModel) throws SQLException {
        String query = "SELECT * FROM patient WHERE name LIKE ?";
        try (Connection connection = DatabaseConnection.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + patientName + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(this, "No patients found with name " + patientName, "INFO", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    while (resultSet.next()) {
                        tableModel.addRow(new Object[]{
                              //  resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("father_name"),
                                resultSet.getString("gender"),
                                resultSet.getString("dob"),
                                resultSet.getString("doctor"),
                                resultSet.getString("disease"),
                                resultSet.getString("prescription")
                        });
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new searchPatientByName();
    }
}

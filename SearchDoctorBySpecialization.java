package hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
public class SearchDoctorBySpecialization extends JFrame {
    private JTextField specializationField;

    public SearchDoctorBySpecialization() {
        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.CENTER);

        setTitle("Search Doctors By Specialization");
        setSize(400, 600);
        setLayout(new FlowLayout());
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Enter Specialization"));
        specializationField = new JTextField("          ");
        add(specializationField);

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        add(searchButton);
        add(cancelButton);

        String[] columnNames = {"Doctor_id", "Doctor_name", "Doctor_specialization"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane);

        searchButton.addActionListener(e -> {
            String specialization = getDoctorSpecialization();

            if (specialization.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Specialization", "INFO", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            tableModel.setRowCount(0); // Clear previous results

            try {
                searchDoctorBySpecialization(specialization, tableModel);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public String getDoctorSpecialization() {
        return specializationField.getText();
    }

    private void searchDoctorBySpecialization(String specialization, DefaultTableModel tableModel) throws SQLException {
        String query = "SELECT doctor_id, doctor_name, specialization FROM doctor WHERE specialization LIKE ?";
        Connection connection = DatabaseConnection.connectToDatabase();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + specialization + "%"); // Partial specialization search

        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No Doctor Found with specialization: " + specialization, "INFO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("doctor_id"),
                            resultSet.getString("doctor_name"),
                            resultSet.getString("specialization")
                    });
                }
            }
        }
    }

    public static void main(String[] args) {
        new SearchDoctorBySpecialization();
    }
}
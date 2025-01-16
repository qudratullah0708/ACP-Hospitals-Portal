package hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SearchDoctorByName extends JFrame {
    private JTextField nameField;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    public SearchDoctorByName() {
        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.CENTER);

        setTitle("Search Doctors By Name");
        setSize(400, 600);
        setLayout(new FlowLayout());
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Search By Name"));
        nameField = new JTextField("          ");
        add(nameField);

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        add(searchButton);
        add(cancelButton);

        String[] columnNames = {"Doctor_id", "Doctor_name", "Doctor_specialization"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane);

        searchButton.addActionListener(e -> {
            String doctorName = getDoctorName();

            if (doctorName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Doctor Name", "ERROR", JOptionPane.ERROR_MESSAGE);
                return; // Exit if no name entered
            }

            tableModel.setRowCount(0); // Clear previous results
            searchDoctorByName(doctorName);
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public String getDoctorName() {
        return nameField.getText();
    }

    private void searchDoctorByName(String doctorName) {
        String query = "SELECT * FROM doctor WHERE doctor_name LIKE ?";

        try (Connection connection = DatabaseConnection.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + doctorName + "%"); // Partial name search

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(this, "No Doctor found with name: " + doctorName, "INFO", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    while (resultSet.next()) {
                        tableModel.addRow(new Object[]{
                                resultSet.getInt("id"),
                                resultSet.getString("doctor_name"),
                                resultSet.getString("specialization")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new SearchDoctorByName();
    }
}
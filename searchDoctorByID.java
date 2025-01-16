package hms;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class searchDoctorByID extends JFrame {
    private JTextField IdField;
    public searchDoctorByID(){
        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.CENTER);

        setTitle("Search Doctor By ID");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout()); 

        add(new JLabel("Enter Doctor ID: "));
        IdField = new JTextField("             ");
        add(IdField);

        JButton searchButton = new JButton("Search");
        add(searchButton);
        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);


        String[] ColumnNames = {"Doctor_id", "Doctor_Name", "Doctor_specialization"};
        DefaultTableModel tableModel = new DefaultTableModel(ColumnNames,0);
        
        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane);


        // ActionListner for search Button

        searchButton.addActionListener(e->{
            String id = getDoctorId();
            // Clear previous results
            tableModel.setRowCount(0);

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Doctor ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Search doctor in the database
            try {
                searchDoctorByIdFromDatabase(id, tableModel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        cancelButton.addActionListener(e->dispose());
        setVisible(true);

    }
 
    public String getDoctorId(){
        return IdField.getText().trim();
    }

    private boolean searchDoctorByIdFromDatabase(String id, DefaultTableModel tableModel) throws  SQLException {
        String query = "SELECT doctor_id, doctor_name, specialization FROM doctor WHERE doctor_id = ?";
        try (Connection connection = DatabaseConnection.connectToDatabase();  // Use the connection from your class
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("doctor_id"),
                            resultSet.getString("doctor_name"),
                            resultSet.getString("specialization")
                    });
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "No Doctor Found with ID: " + id, "Info", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
        }
    }




    public static void main(String[] args){

        new searchDoctorByID();
    }

}

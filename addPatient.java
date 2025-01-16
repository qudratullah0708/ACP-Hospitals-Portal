package hms;//package forms;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class addPatient extends JFrame {



    private JTextField nameField;
    private JTextField fatherField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private JTextField dobField;
    private JComboBox<String> doctorDropdown;
    private JTextArea diseaseArea;
    private JTextArea  prescriptionArea;


    public addPatient(){

        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.NORTH);
          
            // This creates a grid with 8 rows and 2 columns, and 10-pixel gaps between rows and columns.
              setTitle("Add New Patient.");
              setSize(400,500);
              setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
              setLayout(new GridLayout(8,2,10,10));

              JPanel mainPanel = new JPanel();
              //    Form Filds are

                add(new JLabel("Patient Name: "));
                nameField = new JTextField();
                add(nameField);


                add(new JLabel("Father Name: "));
                fatherField = new JTextField();
                add(fatherField);
                

                add(new JLabel("Sex: "));
                JPanel sexPanel = new JPanel();
                maleButton = new JRadioButton("Male");
                femaleButton = new JRadioButton("Female");
                // The ButtonGroup ensures that selecting one radio button automatically deselects the others in the group.
                ButtonGroup  sexGroup = new ButtonGroup();
                sexGroup.add(maleButton);
                sexGroup.add(femaleButton);
                sexPanel.add(maleButton);
                sexPanel.add(femaleButton);
                add(sexPanel);

                add(new JLabel("Date of Birth:"));
                dobField = new JTextField("DD-MM-YYYY");
                add(dobField);


                add(new JLabel("Doctor Name: "));
                doctorDropdown = new JComboBox<>(new String[]{"Select Doctor","Dr. Saeed Akhtar", "Dr. Younus", "Dr. Abdul Basit"});
                add(doctorDropdown);


                add(new JLabel("Disease: "));
                diseaseArea = new JTextArea(5,30);
                add(new JScrollPane(diseaseArea));


                add(new JLabel("Prescription: "));
                prescriptionArea = new JTextArea(5,30);
                add(new JScrollPane(prescriptionArea));

               // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);


       
                saveButton.addActionListener(e -> {
                    String patientName = nameField.getText(); // or we may call getPatientName() Method for all these
                    String fatherName = fatherField.getText();
                    String sex = maleButton.isSelected() ? "Male" : "Female";
                    String dob = dobField.getText();
                    String doctorName = (String) doctorDropdown.getSelectedItem();
                    String diseaseHistory = diseaseArea.getText();
                    String prescription = prescriptionArea.getText();
        
                  if(patientName.isEmpty() || fatherName.isEmpty() || sex.isEmpty() || dob.isEmpty() || diseaseHistory.isEmpty()|| prescription.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Fill all the fields correctly","Error", JOptionPane.ERROR_MESSAGE);
                    return;
                  }
                  if(doctorName.equals("Doctor")){
                    JOptionPane.showMessageDialog(this, "Doctor not Selected","Error", JOptionPane.ERROR_MESSAGE);
                    return;
                  }
                    boolean success = savePatientToDatabase(patientName, fatherName,sex,dob,doctorName,diseaseHistory,prescription);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Patient Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error Adding Patient!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                  

                }); 
        
                cancelButton.addActionListener(e -> dispose()); // Close the form when cancel is clicked
              

              add(mainPanel);

             setLocationRelativeTo(null); // Center the frame

              setVisible(true);
    }

    public String getPatientName(){
        return nameField.getText();
    }
    
    public String getFatherName(){
        return fatherField.getText();
    }
    
    public String getSex(){
        return maleButton.isSelected()? "Male" : femaleButton.isSelected()? "Female" : "";
    }

    
    public String getDob(){
        return dobField.getText();
    }

    
    public String getDoctorName(){
        return (String) doctorDropdown.getSelectedItem();
    }

    public String getDiseaseHistory() {
        return diseaseArea.getText();
    }

    public String getPrescription() {
        return prescriptionArea.getText();
    }

    private boolean savePatientToDatabase(String patientName, String fatherName, String sex, String dob, String doctor, String disease, String prescription) {
        String query = "INSERT INTO patients (name, father_name,gender,dob,doctor,disease,prescription) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connectToDatabase();  // Use the connection from your class
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters for the prepared statement
            statement.setString(1, patientName);
            statement.setString(2, fatherName);
            statement.setString(3,sex);
            statement.setString(4,dob);
            statement.setString(5,doctor);
            statement.setString(6,disease);
            statement.setString(7,prescription);

            // Execute the insert query
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;  // Return true if doctor was successfully added
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there was an error
        }
    }



    // Main method to run the form
    public static void main(String[] args) {

       // new addPatient();
    }

}

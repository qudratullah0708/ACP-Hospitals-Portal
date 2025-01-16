package hms;

import javax.swing.*;
import java.awt.*;

public class adminDashboard extends JFrame {
    public adminDashboard() {
        // Set the title and default close operation
        setTitle("Welcome to Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Add a welcome title at the top
        JLabel titleLabel = new JLabel("Welcome to Hospital Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Add a panel with an image in the center
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\PMLS\\Desktop\\ACP_Project\\hospital.jpg"); // Replace with your image path
        Image scaledImage = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.CENTER);

        // Add a clock panel below the image
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.SOUTH);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();


        // Add glue to shift menus towards the center
        menuBar.add(Box.createHorizontalGlue());

        // Add Manage Records menu
        JMenu manageMenu = new JMenu("Manage Records");
        JMenuItem add_new_Patient = new JMenuItem("Add new Patient");
        JMenuItem add_new_doctor = new JMenuItem("Add new Doctor");
        JMenuItem add_new_disease = new JMenuItem("Add new Disease");
        JMenuItem delete_patient = new JMenuItem("Delete Patient Record");
        JMenuItem update_patient = new JMenuItem("Update Patient Record");
        JMenuItem update_doctor = new JMenuItem("Update Doctor Record");

        add_new_Patient.addActionListener(e -> new addPatient());
        add_new_doctor.addActionListener(e -> new addDoctor());
        add_new_disease.addActionListener(e -> {}); // Placeholder for addDisease
        delete_patient.addActionListener(e -> new deletePatient());
        update_patient.addActionListener(e -> new updatePateint());
        update_doctor.addActionListener(e -> new updateDoctor());

        manageMenu.add(add_new_Patient);
        manageMenu.add(add_new_doctor);
        manageMenu.add(add_new_disease);
        manageMenu.add(delete_patient);
        manageMenu.add(update_patient);
        manageMenu.add(update_doctor);

        menuBar.add(manageMenu);

        // Add Search Records menu
        JMenu searchMenu = new JMenu("Search Records");
        JMenuItem search_patient_by_name = new JMenuItem("Search Patient by Name");
        JMenuItem search_doctor_by_id = new JMenuItem("Search Doctor by ID");
        JMenuItem search_doctor_by_name = new JMenuItem("Search Doctor by Name");
        JMenuItem search_by_specialization = new JMenuItem("Search Doctor by Specialization");

        search_patient_by_name.addActionListener(e -> new searchPatientByName());
        search_doctor_by_name.addActionListener(e -> new SearchDoctorByName());
        search_doctor_by_id.addActionListener(e -> new searchDoctorByID());
        search_by_specialization.addActionListener(e -> new SearchDoctorBySpecialization());

        searchMenu.add(search_patient_by_name);
        searchMenu.add(search_doctor_by_name);
        searchMenu.add(search_doctor_by_id);
        searchMenu.add(search_by_specialization);

        menuBar.add(searchMenu);

        // Add Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutuspage = new JMenuItem("About Us Page");
        JMenuItem changePassword = new JMenuItem("Change Password");

        aboutuspage.addActionListener(e -> new AboutUsPage());
        changePassword.addActionListener(e->new ChangePassword());

        helpMenu.add(aboutuspage);
        helpMenu.add(changePassword);

        menuBar.add(helpMenu);


        menuBar.add(Box.createHorizontalGlue()); // Add glue on the right

        // Set the menu bar
        setJMenuBar(menuBar);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        new adminDashboard();
    }
}

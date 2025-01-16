package hms;

import javax.swing.*;
import java.awt.*;

public class guestDashboard extends JFrame {
    public guestDashboard(){
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

        // Add clock at the top
        ClockPanel clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.SOUTH);

        setTitle("Guest Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar MenuBar = new JMenuBar();

         // Add glue to shift menus towards the center
         MenuBar.add(Box.createHorizontalGlue());

        JMenu searchMenu = new JMenu("Search Records");

        

        JMenuItem search_by_name = new JMenuItem("Search Patient by Name");
        search_by_name.addActionListener(e->{
            new searchPatientByName();
        });
        JMenuItem search_by_ID = new JMenuItem("Search Patient by ID");


        searchMenu.add(search_by_name);
        searchMenu.add(search_by_ID);
        MenuBar.add(searchMenu);


        JMenu helpMenu = new JMenu("Help");
         
        JMenuItem aboutuspage = new JMenuItem("About Us Page");
        aboutuspage.addActionListener(e->{
            new AboutUsPage();
        });

        helpMenu.add(aboutuspage);
        MenuBar.add(helpMenu);

        MenuBar.add(Box.createHorizontalGlue()); // Add glue on the right

        setJMenuBar(MenuBar);
        
        setVisible(true);
    }



    public static void main(String[] args) {
        new guestDashboard();
    }
}

package hms;// import javax.swing.JFrame;
// import javax.swing.JButton;

import javax.swing.*;
import java.awt.*;
import java.sql.*;



public class LoginFrame extends JFrame{
   private JTextField usernameField;
   private JPasswordField passwordField;
   private JComboBox<String> userTypeBox;
   public LoginFrame(){
       // Add clock at the top
       ClockPanel clockPanel = new ClockPanel();
       add(clockPanel, BorderLayout.NORTH);

       setTitle("Login");
       setSize(400,600);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLayout( new GridLayout(16,4,3,3));

//       // Welcome Label
       JLabel welcomeLabel = new JLabel("Welcome to HMS", SwingConstants.CENTER);
       welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 24));
//       welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     add(welcomeLabel, BorderLayout.NORTH);




       JLabel usernameLabel = new JLabel("Username: ");
       usernameLabel.setBounds(10,10,80,25);
       add(usernameLabel);

       usernameField = new JTextField();
       usernameField.setBounds(100, 10, 160, 25);
     //  usernameField.setPreferredSize(new Dimension(200, 30)); // Set fixed dimensions
       add(usernameField);

       JLabel passwordLabel = new JLabel("Password: ");
       passwordLabel.setBounds(10, 40, 80, 25);
       add(passwordLabel);

       passwordField = new JPasswordField();
       passwordField.setBounds(100, 40, 160, 25);
    //   passwordField.setPreferredSize(new Dimension(200, 30)); // Set fixed dimensions
       add(passwordField);

       JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setBounds(10, 70, 80, 25);
        add(userTypeLabel);

         userTypeBox = new JComboBox<>(new String[]{"Administrator", "Guest"});
        userTypeBox.setBounds(100, 70, 160, 25);
        add(userTypeBox);


        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100,100,50,25);
        add(loginButton);

        loginButton.addActionListener(e->{
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String userType = (String) userTypeBox.getSelectedItem();

                //JOptionPane.showMessageDialog("Login Attemted by" + username + " : " + userType);
               System.out.println("Login Attemted by " + username + " : " + userType);

               if(authenticateUser(username,password,userType)){
                  JOptionPane.showMessageDialog(null, "Login Successful");
                  if(userType.equals("Administrator")) {
                      new adminDashboard();
                  }
                  else if(userType.equals("Guest")){
                      new guestDashboard();
                  }

               }else{
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
               }

        });

                setVisible(true);

   }
   private boolean authenticateUser(String username, String password, String userType) {
    boolean isAuthenticated = false;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {

        conn = DatabaseConnection.connectToDatabase();
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database");

        }

        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND user_type = ?";
        ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, userType);

        rs = ps.executeQuery();
        if (rs.next()) {
            isAuthenticated = true;
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log the exception for debugging
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return isAuthenticated;
}

   public static void main(String[] args){
    new LoginFrame();

}

}



package hms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
//import javax.websocket.*;

//@ClientEndpoint
public class AboutUsPage extends JFrame {

    private JTextArea chatDisplay;
    private JTextField chatInput;
  //  private Session session;

    public AboutUsPage() {
        // Set the title and default properties
        setTitle("About Us - Hospital Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create a header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel headerLabel = new JLabel("Hospital Management System");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Content and Scroll Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel aboutTitle = new JLabel("About Us");
        aboutTitle.setFont(new Font("Arial", Font.BOLD, 18));
        aboutTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(aboutTitle);

        String aboutText = "<html><p align='justify'>"
                + "Welcome to the <b>Hospital Management System</b>, your comprehensive solution "
                + "for managing all aspects of hospital operations. Our system is designed to streamline "
                + "patient records, appointment scheduling, billing, and staff management, ensuring seamless "
                + "coordination between departments.<br><br>"
                + "Our mission is to empower healthcare providers by leveraging cutting-edge technology "
                + "to deliver better care to patients. With a user-friendly interface and robust features, "
                + "our system reduces administrative burdens, enhances patient satisfaction, and improves "
                + "overall operational efficiency.<br><br>"
                + "Join us in our journey to revolutionize healthcare management and deliver exceptional services "
                + "to those who matter most – the patients."
                + "</p></html>";

        JLabel aboutContent = new JLabel(aboutText);
        aboutContent.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(aboutContent);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Chat Panel
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBorder(BorderFactory.createTitledBorder("Chat Application"));

        chatDisplay = new JTextArea();
        chatDisplay.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatDisplay);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        chatInput = new JTextField();
      //  chatInput.addActionListener(e -> sendMessage(chatInput.getText()));
        chatPanel.add(chatInput, BorderLayout.SOUTH);

        add(chatPanel, BorderLayout.EAST);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        footerPanel.add(closeButton);
        add(footerPanel, BorderLayout.SOUTH);

        // Establish WebSocket connection
      //  connectToWebSocket();

        // Make the frame visible
        setVisible(true);
    }

//    private void connectToWebSocket() {
//        try {
//            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//            container.connectToServer(this, URI.create("ws://localhost:8080/chat"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            chatDisplay.append("Unable to connect to WebSocket server.\n");
//        }
//    }
//
//    @OnOpen
//    public void onOpen(Session session) {
//        this.session = session;
//        chatDisplay.append("Connected to chat server.\n");
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        chatDisplay.append("Server: " + message + "\n");
//    }
//
//    @OnClose
//    public void onClose() {
//        chatDisplay.append("Disconnected from chat server.\n");
//    }
//
//    @OnError
//    public void onError(Throwable throwable) {
//        chatDisplay.append("Error: " + throwable.getMessage() + "\n");
//    }
//
//    private void sendMessage(String message) {
//        if (session != null && session.isOpen()) {
//            try {
//                session.getBasicRemote().sendText(message);
//                chatDisplay.append("You: " + message + "\n");
//                chatInput.setText("");
//            } catch (Exception e) {
//                e.printStackTrace();
//                chatDisplay.append("Failed to send message.\n");
//            }
//        } else {
//            chatDisplay.append("Not connected to server.\n");
//        }
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AboutUsPage::new);
    }
}

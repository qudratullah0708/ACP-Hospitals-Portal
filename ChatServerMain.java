//package hms;
//
//import org.glassfish.tyrus.server.Server;
//
//public class ChatServerMain {
//
//    public static void main(String[] args) {
//        Server server = new Server("localhost", 8080, "/ws", ChatServer.class);
//
//        try {
//            server.start();
//            System.out.println("WebSocket server started at ws://localhost:8080/ws/chat");
//            System.out.println("Press Enter to stop the server...");
//            System.in.read();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            server.stop();
//            System.out.println("Server stopped.");
//        }
//    }
//}

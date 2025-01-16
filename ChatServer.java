//package hms;
//
////import javax.websocket.*;
////import javax.websocket.server.ServerEndpoint;
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.handshake.ServerHandshake;
//
//import java.io.IOException;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//@ServerEndpoint("/chat")
//public class ChatServer {
//
//    private static final Set<Session> clients = new CopyOnWriteArraySet<>();
//
//    @OnOpen
//    public void onOpen(Session session) {
//        clients.add(session);
//        broadcast("A new user has joined the chat.");
//        System.out.println("New connection: " + session.getId());
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        System.out.println("Message from " + session.getId() + ": " + message);
//        broadcast("User " + session.getId() + ": " + message);
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        clients.remove(session);
//        broadcast("A user has left the chat.");
//        System.out.println("Connection closed: " + session.getId());
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        System.err.println("Error for session " + session.getId() + ": " + throwable.getMessage());
//    }
//
//    private void broadcast(String message) {
//        for (Session client : clients) {
//            try {
//                client.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

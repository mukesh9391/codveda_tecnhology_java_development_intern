import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer1 {
    private static final int PORT = 1234;
    private static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("? Chat Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println("? Server error: " + e.getMessage());
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    public static void privateMessage(String recipient, String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client.username != null && client.username.equalsIgnoreCase(recipient)) {
                    client.sendMessage("?? (Private) " + sender.username + ": " + message);
                    sender.sendMessage("?? (You ? " + recipient + "): " + message);
                    return;
                }
            }
            sender.sendMessage("? User '" + recipient + "' not found.");
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Ask for username
                out.println("Enter your username: ");
                username = in.readLine();

                // Validate username
                if (username == null || username.trim().isEmpty()) {
                    out.println("? Invalid username. Connection closed.");
                    socket.close();
                    return;
                }

                synchronized (clients) {
                    for (ClientHandler client : clients) {
                        if (username.equalsIgnoreCase(client.username)) {
                            out.println("? Username already taken. Connection closed.");
                            socket.close();
                            return;
                        }
                    }
                    clients.add(this);
                }

                System.out.println("? " + username + " joined the chat.");
                broadcast("?? " + username + " has joined the chat!", this);

                out.println("? Welcome, " + username + "!");
                out.println("Commands: /msg <username> <message> for private messages, type 'exit' to leave.");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                    if (message.startsWith("/msg ")) {
                        // Format: /msg <username> <message>
                        String[] parts = message.split(" ", 3);
                        if (parts.length >= 3) {
                            String recipient = parts[1];
                            String privateMsg = parts[2];
                            privateMessage(recipient, privateMsg, this);
                        } else {
                            out.println("? Usage: /msg <username> <message>");
                        }
                    } else {
                        System.out.println(username + ": " + message);
                        broadcast(username + ": " + message, this);
                    }
                }
            } catch (IOException e) {
                System.out.println("? Connection lost with " + username);
            } finally {
                try { socket.close(); } catch (IOException ignored) {}
                clients.remove(this);
                if (username != null) {
                    broadcast("?? " + username + " has left the chat.", this);
                    System.out.println(username + " disconnected.");
                }
            }
        }

        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }
    }
}

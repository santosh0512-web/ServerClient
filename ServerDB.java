import java.io.*;
import java.net.*;
import java.sql.*;

public class ServerDB {
    private static final int PORT = 5000;
    private static final String DB_URL = System.getenv().getOrDefault(
        "MYSQL_URL",
        "jdbc:mysql://localhost:3306/chatdb"
    );
    private static final String DB_USER = System.getenv().getOrDefault("MYSQL_USER", "root");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("MYSQL_PASSWORD", "");

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started...");

            try (
                Socket socket = server.accept();
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                System.out.println("Client connected");
                String message = in.readLine();
                if (message == null) {
                    System.err.println("Client disconnected without sending a message.");
                    return;
                }

                System.out.println("Client says: " + message);
                try {
                    saveMessage(message);
                    out.println("Message saved");
                } catch (SQLException e) {
                    System.err.println("Could not save the message to the database:");
                    e.printStackTrace();
                    out.println("Message received, but could not be saved");
                }
            }
        } catch (IOException e) {
            System.err.println("Server socket error:");
            e.printStackTrace();
        }
    }

    private static void saveMessage(String message) throws SQLException {
        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO messages(msg) VALUES(?)"
            )
        ) {
            statement.setString(1, message);
            statement.executeUpdate();
        }
    }
}
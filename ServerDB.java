import java.io.*;
import java.net.*;
import java.sql.*;

public class ServerDB {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(5000);
            System.out.println("Server started...");

            Socket s = ss.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(
                new InputStreamReader(s.getInputStream())
            );

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            // Receive message from client
            String msg = in.readLine();
            System.out.println("Client says: " + msg);

            // 🔹 JDBC Connection
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/chatdb",
                "root",
                "89127"
            );

            // 🔹 Insert into database
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO messages(msg) VALUES(?)"
            );
            ps.setString(1, msg);
            ps.executeUpdate();

            // Send reply
            out.println("Hello Client");

            con.close();
            s.close();
            ss.close();

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
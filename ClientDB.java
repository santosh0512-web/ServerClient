import java.io.*;
import java.net.*;

public class ClientDB {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", PORT);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Hello Server");

            String response = in.readLine();
            if (response == null) {
                System.err.println("Server disconnected without replying.");
                return;
            }
            System.out.println("Server says: " + response);
        } catch (IOException e) {
            System.err.println("Could not connect to the server:");
            e.printStackTrace();
        }
    }
}
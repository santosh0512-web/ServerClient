import java.io.*;
import java.net.*;

public class ClientDB {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 6000);

            BufferedReader in = new BufferedReader(
                new InputStreamReader(s.getInputStream())
            );

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            // Send message
            out.println("Hello Server");

            // Receive reply
            String response = in.readLine();
            System.out.println("Server says: " + response);

            s.close();

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
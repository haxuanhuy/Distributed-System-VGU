import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

public class Server1 {
    public static Connection connectToDatabase(String user, String password) {
        String databaseUrl = "jdbc:mysql://localhost:3306/db1.student?user=" + user + "&password=" + password;
        Connection conn = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver"); 
            conn = DriverManager.getConnection(databaseUrl);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            int port = 9999;

            server = new ServerSocket(port);
            System.out.println("Server 1 run on port " + port);

            while (true) {
                Socket client = server.accept();

                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                String connectionType = in.readUTF();
                if (connectionType.equals("client")) {
                    out.writeUTF("Do you want to synchornize? ");
                    String answer = in.readUTF();
                    if (answer.equals("yes")) {
                        Connection databaseConnect = connectToDatabase("root", "haxuanhuy1234");
                        Tools.synchornize(databaseConnect, "server 1");
                        out.writeUTF("synchornized done");
                    } 
                } else {
                    Connection databaseConnect = connectToDatabase("root", "haxuanhuy1234");
                    Tools.handleSynchornize(client, in, out, databaseConnect, "server 1");
                }

                in.close();
                out.close();
                client.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
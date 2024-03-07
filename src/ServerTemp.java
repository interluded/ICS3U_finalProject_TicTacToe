import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerTemp {
    static final ServerSocket serverSocket;
    static Socket clientSocket;
    static BufferedReader in;
    static PrintWriter out;

    static {
        ServerSocket tempServerSocket = null;
        try {
            tempServerSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket = tempServerSocket;
    }

    public static void main(String[] args) {
        try {
            clientSocket = serverSocket.accept(); // Wait and accept a connection
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread sender = new Thread(() -> {
                Scanner scan = new Scanner(System.in);
                String msg;
                while (true) {
                    msg = scan.nextLine();
                    out.println(msg);
                }
            });
            sender.start();

            Thread receive = new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Client: " + msg);
                    }
                    System.out.println("Client disconnected.");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) out.close();
                        if (in != null) in.close();
                        if (clientSocket != null) clientSocket.close();
                        if (serverSocket != null) serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

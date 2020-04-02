package server;

import elements.UI;

import java.io.IOException;
import java.net.ServerSocket;

// Actual threaded server class
public class TheServer extends Thread {
    private ServerSocket mySocket;

    // Back reference to server
    private Server server;

    public TheServer(Server server) {
        this.server = server;
    }

    public boolean startServer(int port) {
        try {
            this.mySocket = new ServerSocket(port);
            return true;
        }
        catch (IOException e) {
            System.out.println("Server socket did not launch");
            return false;
        }
    }

    public void stopServer() {
        // Closing server socket
        try { mySocket.close(); }
        catch (IOException e) {
            System.out.println("close ex");
            e.printStackTrace();
        }

        // Closing active clients socket
        for (ClientThread t : this.server.clients) {
            try { t.connection.close(); }
            catch (IOException ignored) { }
        }
        this.server.logger.add("All clients were disconnected");
    }

    public void run() {
        try {
            System.out.println("Server is waiting for a client!");
            while(true) {
                ClientThread c = new ClientThread(mySocket.accept(), this.server.count, this.server);
                this.server.logger.add("New client connected");
                this.server.callback.accept("client has connected to server: " + "client #" + this.server.count);
                this.server.clients.add(c);
                c.start();
                this.server.count++;
                this.server.matchWithPartner();

                this.server.updateUI();
            }
        }
        catch(Exception e) {
            this.server.callback.accept("Server accept error");
        }
    }
}

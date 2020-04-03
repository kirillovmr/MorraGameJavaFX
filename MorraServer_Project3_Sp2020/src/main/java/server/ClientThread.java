package server;

import core.MorraInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class ClientThread extends Thread {
    protected int id;
    protected Socket connection;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    // Back reference to server
    private Server server;

    // Room player assigned to
    protected Room room;

    ClientThread(Socket s, int id, Server server) {
        this.connection = s;
        this.id = id;
        this.room = null;
        this.server = server;
    }

    public void run() {
        try {
            in = new ObjectInputStream(connection.getInputStream());
            out = new ObjectOutputStream(connection.getOutputStream());
            connection.setTcpNoDelay(true);
        }
        catch(Exception e) {
            System.out.println("Streams not open");
        }

        // Sending clients' id to the client
        try {
            this.out.writeObject(this.id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                MorraInfo info = (MorraInfo) in.readObject();
                this.server.callback.accept("client: " + id + " sent: " + info);
                this.room.updatePlayerSelection(info);
            }
            catch(Exception e) {
                this.server.callback.accept("OOOOPPs...Something wrong with the socket from client: " + this.id + "....closing down!");
                this.server.logger.add("Client #" + this.id + " disconnected");
                this.server.clients.remove(this);
                break;
            }
        }
    }
}

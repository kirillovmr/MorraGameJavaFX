package server;

import core.Logic;
import core.MorraInfo;
import elements.UI;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                Platform.runLater(() -> this.room.updatePlayerSelection(info));
            }
            catch(Exception e) {
                this.server.callback.accept("OOOOPPs...Something wrong with the socket from client: " + this.id + "....closing down!");
                Platform.runLater(() -> this.server.logger.add("Client #" + this.id + " disconnected"));
                this.server.clients.remove(this);
                for(Room r: this.server.rooms) {
                    if (r.p1 == this) { r.p1 = null; }
                    if (r.p2 == this) { r.p2 = null; }
                }
                // Checking rooms to be cleared
                this.server.rooms.removeIf(r -> r.p1 == null && r.p2 == null);
                Logic.server.updateUI();

                UI.gameArea.deleteRoom(id);

                break;
            }
        }
    }
}

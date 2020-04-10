package server;

import core.Logger;
import elements.UI;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server
{
    protected int count = 1;
    protected Logger logger;
    private TheServer server;
    protected Consumer<Serializable> callback;
    protected ArrayList<ClientThread> clients;
    protected ArrayList<Room> rooms;

    public Server(Logger logger, Consumer<Serializable> callback)
    {
        this.clients = new ArrayList<>();
        this.rooms = new ArrayList<>();

        this.logger = logger;
        this.callback = callback;
        this.server = new TheServer(this);
    }

    protected void matchWithPartner()
    {
        // Checking rooms to be cleared
        rooms.removeIf(r -> r.p1 == null && r.p2 == null);

        if (clients.size() >= 2)
        {
            Room room = new Room(clients.remove(0), clients.remove(0));
            rooms.add(room);
            Platform.runLater(() ->UI.gameArea.addRoom(room.p1.id, room.p2.id));
            Platform.runLater(() -> this.logger.add("Room was created for clients #" + room.p1.id + "," + room.p2.id));
        }
    }

    protected void updateUI() {
        int totalInRooms = 0;
        for (Room r: rooms) {
            if (r.p1 != null) { totalInRooms += 1; }
            if (r.p2 != null) { totalInRooms += 1; }
        }
        UI.updatePlayersConnected(this.clients.size() + totalInRooms);
        UI.updatePlayersWaiting(this.clients.size());
    }

    public TheServer getInstance() { return this.server; }

}







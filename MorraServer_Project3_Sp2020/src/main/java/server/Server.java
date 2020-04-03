package server;

import core.Logger;
import core.MorraInfo;
import elements.UI;
import javafx.util.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
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
        if (clients.size() >= 2)
        {
            Room room = new Room(clients.remove(0), clients.remove(0));
            rooms.add(room);
            this.logger.add("Room was created for clients #" + room.p1.id + "," + room.p2.id);
        }
    }

    protected void updateUI() {
        UI.updatePlayersConnected(this.clients.size() + this.rooms.size()*2);
        UI.updatePlayersWaiting(this.clients.size());
    }

    public TheServer getInstance() { return this.server; }

}







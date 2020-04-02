package server;

import core.MorraInfo;
import java.io.IOException;

public class Room {
    ClientThread p1;
    ClientThread p2;
    MorraInfo morraInfo;

    public Room(ClientThread p1, ClientThread p2) {
        this.p1 = p1;
        this.p2 = p2;

        this.p1.room = this;
        this.p2.room = this;

        this.morraInfo = new MorraInfo();
        this.morraInfo.have2players = true;
        this.broadcast();
    }

    // Broadcast morraInfo to both players
    public void broadcast() {
        try {
            p1.out.writeObject(morraInfo);
            p2.out.writeObject(morraInfo);
        }
        catch (IOException e) {
            System.out.println("Error broadcast room players " + p1.id + "," + p2.id);
        }
    }
}

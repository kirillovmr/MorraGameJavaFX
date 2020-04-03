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
        this.morraInfo.p1ID = this.p1.id;
        this.morraInfo.p2ID = this.p2.id;

        this.morraInfo.p1Points = 13;
        this.morraInfo.p2Points = 26;

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        broadcast();
                    }
                }, 2000
        );
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

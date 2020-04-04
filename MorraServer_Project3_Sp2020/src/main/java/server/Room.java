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

        broadcast(this.morraInfo,2000);
    }

    public void updatePlayerSelection(MorraInfo morraInfoReceived) {
        // If received data from player 1
        if (morraInfoReceived.p1Plays >= 0) {
            this.morraInfo.p1Plays = morraInfoReceived.p1Plays;
        }
        // If received data from player 2
        if (morraInfoReceived.p2Plays >= 0) {
            this.morraInfo.p2Plays = morraInfoReceived.p2Plays;
        }
        // If both players data is ready now
        if (this.morraInfo.p1Plays >= 0 && this.morraInfo.p2Plays >= 0) {
            // Updating scores
            if (this.morraInfo.p1Plays > this.morraInfo.p2Plays) {
                this.morraInfo.p1Points += 1;
            }
            else if (this.morraInfo.p2Plays > this.morraInfo.p1Plays) {
                this.morraInfo.p2Points += 1;
            }
            // Sending data to clients
            broadcast(new MorraInfo(this.morraInfo), 2000);
        }
    }

    // Broadcast morraInfo to both players
    public void broadcast(MorraInfo morraInfo, int delay) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("Sending: " + morraInfo.toString());
                            p1.out.writeObject(morraInfo);
                            p2.out.writeObject(morraInfo);
                        }
                        catch (IOException e) {
                            System.out.println("Error broadcast room players " + p1.id + "," + p2.id);
                        }
                    }
                }, delay
        );
    }
}

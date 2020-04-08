package server;

import core.Logic;
import core.MorraInfo;
import elements.UI;

import java.io.IOException;

public class Room {
    ClientThread p1, p2;

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
            this.morraInfo.p1Guess = morraInfoReceived.p1Guess;
            UI.gameArea.showPlayerSelection(morraInfoReceived.p1ID, morraInfoReceived.p1Plays, morraInfoReceived.p1Guess);
        }
        // If received data from player 2
        if (morraInfoReceived.p2Plays >= 0) {
            this.morraInfo.p2Plays = morraInfoReceived.p2Plays;
            this.morraInfo.p2Guess = morraInfoReceived.p2Guess;
            UI.gameArea.showPlayerSelection(morraInfoReceived.p2ID, morraInfoReceived.p2Plays, morraInfoReceived.p2Guess);
        }
        if (morraInfoReceived.p1PlayAgain >= 0) {
            this.morraInfo.p1PlayAgain = morraInfoReceived.p1PlayAgain;
        }
        if (morraInfoReceived.p2PlayAgain >= 0) {
            this.morraInfo.p2PlayAgain = morraInfoReceived.p2PlayAgain;
        }
        // If both players data is ready now
        if (this.morraInfo.p1Plays >= 0 && this.morraInfo.p2Plays >= 0) {

            // If players want to play again (players FinishScene)
            if (this.morraInfo.p1PlayAgain == 1 && this.morraInfo.p2PlayAgain == 1) {
                this.morraInfo.p1Plays = this.morraInfo.p2Plays = -1;
                this.morraInfo.p1PlayAgain = this.morraInfo.p2PlayAgain = -1;
                System.out.println("Both play again");
                this.broadcast(new MorraInfo(this.morraInfo), 1000);
                UI.gameArea.playAgain(this.morraInfo.p1ID);
            }
            else if (this.morraInfo.p1PlayAgain == 1 && this.morraInfo.p2PlayAgain == 0) {
                System.out.println("p1 plays, p2 not");
                Logic.server.clients.add(this.p1);
                Logic.server.rooms.remove(this);
                Logic.server.matchWithPartner();
                Logic.server.updateUI();
            }
            else if (this.morraInfo.p2PlayAgain == 1 && this.morraInfo.p1PlayAgain == 0) {
                System.out.println("p2 plays, p1 not");
                Logic.server.clients.add(this.p2);
                Logic.server.rooms.remove(this);
                Logic.server.matchWithPartner();
                Logic.server.updateUI();
            }
            // If they are still playing
            else if (this.morraInfo.p2PlayAgain == -1 && this.morraInfo.p1PlayAgain == -1) {
                // Updating scores
                int totalHandValue = this.morraInfo.p1Plays + this.morraInfo.p2Plays;
                if (this.morraInfo.p1Guess == totalHandValue && this.morraInfo.p2Guess == totalHandValue) {
                    // Points are not awarded
                }
                else if (this.morraInfo.p1Guess == totalHandValue) {
                    this.morraInfo.p1Points += 1;
                }
                else if (this.morraInfo.p2Guess == totalHandValue) {
                    this.morraInfo.p2Points += 1;
                }
                // Sending data to clients
                broadcast(new MorraInfo(this.morraInfo), 2000);
            }
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

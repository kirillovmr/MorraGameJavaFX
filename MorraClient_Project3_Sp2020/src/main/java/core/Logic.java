package core;

import client.Client;
import elements.UI;
import javafx.application.Platform;
import scenes.GameScene;
import scenes.WaitingScene;

public class Logic {
    private Logic() {}

    public static Logger logger;
    public static Client client;

    private static int playerID = -1;
    private static MorraInfo morraInfo;

    public static void createClient() {
        Logic.client = new Client(data ->
                Platform.runLater(()->
                {
                    // If Integer received
                    if (data instanceof Integer) {
                        playerID = (int) data;
                        System.out.println("ID received:" + playerID);

                        // When player chose what to play
                        UI.gameArea.setOnPlayerSelect(playerPlays -> {
                            // Setting up what player selected
                            if (morraInfo.p1ID == playerID) {
                                morraInfo.p1Plays = playerPlays;
                            } else {
                                morraInfo.p2Plays = playerPlays;
                            }
                            // Sending to server
                            Logic.client.sendInfo(morraInfo);
                        });
                    }
                    // If Morra info received
                    else if (data instanceof MorraInfo) {
                        morraInfo = (MorraInfo) data;
                        System.out.println("Received:" + data.toString());

                        // If we are waiting for an opponent
                        if (UI.currentScene.getClass().getName().equals(WaitingScene.class.getName())) {
                            UI.setScene(UI.gameScene, true);
                            Logic.logger.add("Matched with a partner");

                            if (morraInfo.p1ID == playerID) {
                                UI.updatePlayerScore(morraInfo.p1Points);
                                UI.updateOpponentScore(morraInfo.p2Points);
                            }
                            else {
                                UI.updatePlayerScore(morraInfo.p2Points);
                                UI.updateOpponentScore(morraInfo.p1Points);
                            }
                        }
                        // If we are playing
                        else if (UI.currentScene.getClass().getName().equals(GameScene.class.getName())) {
                            if (morraInfo.p1ID == playerID) {
                                UI.gameArea.showOpponentHand(morraInfo.p2Plays);
                            }
                            else {
                                UI.gameArea.showOpponentHand(morraInfo.p1Plays);
                            }
                        }
                    }
                }));
    }
}

package elements;

import javafx.scene.text.Text;
import scenes.ActiveScene;

public class UI {
    private static Text playersConnected = null;
    private static Text playersWaiting = null;

    private UI() { }

    public static void setPlayersConnected(Text tf){
        playersConnected = tf;
    }
    public static void setPlayersWaiting(Text tf){
        playersWaiting = tf;
    }

    public static void updatePlayersConnected(int num){
        playersConnected.setText("Players connected: " + num);
    }
    public static void updatePlayersWaiting(int num){
        playersWaiting.setText("Players waiting: " + num);
    }

    public static ActiveScene activeScene;

    public static GameArea gameArea;
}

package elements;

import javafx.scene.text.Text;

public class UI {
    private UI() { }

    private static Text playerScore = null;
    private static Text opponentScore = null;
    public static void setPlayerScore(Text tf){
        playerScore = tf;
    }
    public static void setOpponentScore(Text tf){
        opponentScore = tf;
    }
    public static void updatePlayerScore(int num){
        playerScore.setText("" + num);
    }
    public static void updateOpponentScore(int num){
        opponentScore.setText("" + num);
    }

    private static Text middleText = null;
    private static Text bottomText = null;
    public static void setMiddleText(Text tf){
        middleText = tf;
    }
    public static void setBottomText(Text tf){
        bottomText = tf;
    }
    public static void updateMiddleText(String text){
        middleText.setText(text);
    }
    public static void updateBottomText(String text){
        bottomText.setText(text);
    }


}

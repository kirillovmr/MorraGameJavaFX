package scenes;

import elements.GameLog;
import elements.Title;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class GameScene extends MyScene
{
    private Text playerScore, opponentScore;

    private Title titleText;
    private Text middleText, bottomText;

    public GameScene() {
        playerScore = new Text(Integer.toString(2));
        opponentScore = new Text(Integer.toString(0));

        StackPane.setAlignment(playerScore, Pos.TOP_LEFT);
        StackPane.setAlignment(opponentScore, Pos.TOP_RIGHT);

        titleText = new Title();
        middleText = new Text("LET GAME BEGIN");
        bottomText = new Text("TAKE YOUR PICK");
        VBox root = new VBox(titleText, middleText, bottomText);

        gameLog = new GameLog();
        stack = new StackPane(gameLog, playerScore, opponentScore, root);
        scene = new Scene(stack, MyScene.width, MyScene.height);
    }
}

package scenes;

import elements.GameArea;
import elements.GameLog;
import elements.Title;
import elements.UI;
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

    private GameArea gameArea;

    public GameScene() {
        playerScore = new Text("You: 0");
        playerScore.setId("playerScore");
        UI.setPlayerScore(playerScore);
        opponentScore = new Text("Opp: 0");
        opponentScore.setId("opponentScore");
        UI.setOpponentScore(opponentScore);

        StackPane.setAlignment(playerScore, Pos.TOP_LEFT);
        StackPane.setAlignment(opponentScore, Pos.TOP_RIGHT);

        titleText = new Title();
        middleText = new Text("LET GAME BEGIN");
        middleText.setId("middleText");
        UI.setMiddleText(middleText);
        bottomText = new Text("TAKE YOUR PICK");
        bottomText.setId("bottomText");
        UI.setBottomText(bottomText);
        VBox root = new VBox(titleText, middleText, new Text(), new Text(), bottomText);
        root.setId("rootVBox");

        gameArea = new GameArea();

        gameLog = new GameLog();
        stack = new StackPane(gameLog, playerScore, opponentScore, root, gameArea);
        stack.setId("stack");
        scene = new Scene(stack, MyScene.width, MyScene.height);
        scene.getStylesheets().add("scenes/GameScene.css");
        setBackground(Color.web("#262626"));
    }
}

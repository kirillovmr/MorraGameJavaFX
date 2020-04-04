package scenes;

import core.Logic;
import elements.GameArea;
import elements.GameLog;
import elements.Title;
import elements.UI;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class FinishScene extends MyScene
{
    public Text playerScore, opponentScore;

    private Title titleText;
    private Text middleText;

    Button playAgainBtn, exitBtn;

    public FinishScene() {
        playerScore = new Text("You: 0");
        playerScore.setId("playerScore");
        opponentScore = new Text("Opp: 0");
        opponentScore.setId("opponentScore");

        StackPane.setAlignment(playerScore, Pos.TOP_LEFT);
        StackPane.setAlignment(opponentScore, Pos.TOP_RIGHT);

        titleText = new Title();
        middleText = new Text("PLAY AGAIN?");
        middleText.setId("middleText");

        playAgainBtn = new Button("PLAY");
        playAgainBtn.setId("playAgainBtn");
        exitBtn = new Button("EXIT");
        exitBtn.setId("exitBtn");
        VBox buttonBox = new VBox(playAgainBtn, exitBtn);
        buttonBox.setId("buttonsVBox");

        VBox root = new VBox(titleText, middleText, buttonBox);
        root.setId("rootVBox");

        gameLog = new GameLog();
        stack = new StackPane(gameLog, playerScore, opponentScore, root);
        stack.setId("stack");
        scene = new Scene(stack, MyScene.width, MyScene.height);
        scene.getStylesheets().add("scenes/FinishScene.css");
        setBackground(Color.web("#262626"));

        this.setBtnHandlers();
    }

    private void setBtnHandlers() {
        playAgainBtn.setOnAction(e -> {
            Logic.sendToServerPlayAgain(true);
            UI.gameToWaitingScene();
        });

        exitBtn.setOnAction(e -> {
            Logic.sendToServerPlayAgain(false);
            System.exit(0);
        });
    }
}

package elements;

import core.Logic;
import javafx.animation.FadeTransition;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import scenes.*;

import java.util.function.Consumer;

public class UI {
    private UI() { }

    public static Text middleText = null;
    public static Text bottomText = null;
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


    public static Stage primaryStage;

    public static MyScene currentScene;
    public static ConnectScene connectScene;
    public static WaitingScene waitingScene;
    public static GameScene gameScene;
    public static FinishScene finishScene;

    public static GameArea gameArea;

    public static void createScenes() {
        connectScene = new ConnectScene();
        Logic.logger.subscribe(connectScene.getGameLog());

        waitingScene = new WaitingScene();
        Logic.logger.subscribe(waitingScene.getGameLog());

        gameScene = new GameScene();
        Logic.logger.subscribe(gameScene.getGameLog());

        finishScene = new FinishScene();
        Logic.logger.subscribe(finishScene.getGameLog());
    }

    // Sets the scene to the stage
    public static void setScene(MyScene scene, boolean anim) {
        if (anim) {
            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(100));
            ft.setNode(currentScene.stack);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(e -> {
                currentScene = scene;
                primaryStage.setScene(scene.getScene());
            });
            ft.play();
        }
        else {
            currentScene = scene;
            primaryStage.setScene(scene.getScene());
        }
    }

    public static void gameToWaitingScene() {
        // Recreating gameArea
        Consumer<String> handler = UI.gameArea.getOnPlayerSelect();
        UI.gameScene.stack.getChildren().remove(UI.gameArea);

        UI.gameArea = new GameArea();
        UI.gameArea.setOnPlayerSelect(handler);
        UI.gameScene.stack.getChildren().add(UI.gameArea);

        UI.gameScene.middleText.setText("LET GAME BEGIN");
        UI.gameScene.bottomText.setVisible(true);

        setScene(waitingScene, false);
    }

    public static void gameToConnectScene() {
        gameToWaitingScene();
        Logic.createClient();
        setScene(connectScene, false);
    }

    public static void setScores(int playerScore, int opponentScore) {
        UI.gameScene.playerScore.setText("You: " + playerScore);
        UI.gameScene.opponentScore.setText("Opp: " + opponentScore);
        UI.finishScene.playerScore.setText("You: " + playerScore);
        UI.finishScene.opponentScore.setText("Opp: " + opponentScore);
    }
}

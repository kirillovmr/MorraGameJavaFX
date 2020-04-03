package elements;

import core.Logic;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import scenes.*;

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
}

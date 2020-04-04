package scenes;

import elements.GameLog;
import elements.UI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ActiveScene extends MyScene
{
    private Text playersConnectedText, playersWaitingText;
    private Button stopBtn;

    public ActiveScene()
    {
        playersConnectedText = new Text("Players connected: 0");
        playersConnectedText.setId("playersConnectedText");
        UI.setPlayersConnected(playersConnectedText);
        playersWaitingText = new Text("Players waiting: 0");
        playersWaitingText.setId("playersWaitingText");
        UI.setPlayersWaiting(playersWaitingText);
        stopBtn = new Button("Stop server");
        stopBtn.setId("stopBtn");

        TilePane.setAlignment(playersConnectedText, Pos.CENTER_LEFT);
        TilePane.setAlignment(stopBtn, Pos.CENTER_RIGHT);

        TilePane header = new TilePane();
        header.setPrefTileWidth(MyScene.width/3 - 10);
        header.setPrefColumns(3);
        header.getChildren().addAll(playersConnectedText, playersWaitingText, stopBtn);

        VBox root = new VBox(header);
        root.setId("rootVBox");

        gameLog = new GameLog();
        stack = new StackPane(gameLog, root);
        stack.setId("stack");
        scene = new Scene(stack, MyScene.width, MyScene.height);
        scene.getStylesheets().add("scenes/ActiveScene.css");
        setBackground(Color.web("#262626"));
    }

    public void setOnButtonPress(EventHandler<ActionEvent> e)
    {
        stopBtn.setOnAction(e);
    }

}

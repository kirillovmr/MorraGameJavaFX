package scenes;

import elements.GameLog;
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
    private GameLog gameLog;

    public ActiveScene()
    {
        playersConnectedText = new Text("Players connected: 0");
        playersWaitingText = new Text("Players waiting: 0");
        stopBtn = new Button("Stop server");

        TilePane.setAlignment(playersConnectedText, Pos.CENTER_LEFT);
        TilePane.setAlignment(stopBtn, Pos.CENTER_RIGHT);

        TilePane header = new TilePane();
        header.setPrefTileWidth(MyScene.width/3 - 1);
        header.setPrefColumns(3);
        header.getChildren().addAll(playersConnectedText, playersWaitingText, stopBtn);

        VBox root = new VBox(header);

        gameLog = new GameLog();
        stack = new StackPane(gameLog, root);
        scene = new Scene(stack, MyScene.width, MyScene.height);
        setBackground(Color.web("#262626"));
    }

    public void setOnButtonPress(EventHandler<ActionEvent> e)
    {
        stopBtn.setOnAction(e);
    }

}
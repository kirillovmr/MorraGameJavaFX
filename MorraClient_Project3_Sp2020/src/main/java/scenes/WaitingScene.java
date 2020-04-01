package scenes;

import elements.GameLog;
import elements.Title;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class WaitingScene extends MyScene
{
    private Button connectBtn;
    private GameLog gameLog;

    public WaitingScene()
    {
        Title title = new Title();

        Text middleText = new Text("Waiting for opponent");

        connectBtn = new Button("go back");
        VBox root = new VBox(title, middleText, connectBtn);

        gameLog = new GameLog();
        stack = new StackPane(gameLog, root);
        scene = new Scene(stack, MyScene.width, MyScene.height);
        setBackground(Color.web("#262626"));
    }

    public void setOnButtonPress(EventHandler<ActionEvent> e) {
        connectBtn.setOnAction(e);
    }
}

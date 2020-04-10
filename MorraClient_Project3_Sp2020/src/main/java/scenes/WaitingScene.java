package scenes;

import elements.GameLog;
import elements.Title;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class WaitingScene extends MyScene
{
    public WaitingScene()
    {
        Title title = new Title();

        Text middleText = new Text("Waiting for opponent...");
        middleText.setId("middleText");

        VBox root = new VBox(title, middleText);
        root.setId("rootVBox");

        gameLog = new GameLog();
        stack = new StackPane(gameLog, root);
        stack.setId("stack");
        scene = new Scene(stack, MyScene.width, MyScene.height);
        scene.getStylesheets().add("scenes/WaitingScene.css");
        setBackground(Color.web("#262626"));
    }

}

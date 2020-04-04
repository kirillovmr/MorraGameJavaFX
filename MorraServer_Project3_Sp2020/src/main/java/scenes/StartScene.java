package scenes;

import elements.ConnectForm;
import elements.GameLog;
import elements.Title;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StartScene extends MyScene
{
    public Title title;
    private ConnectForm connectForm;

    public StartScene()
    {
        title = new Title();
        connectForm = new ConnectForm("Start the server");
        VBox root = new VBox(title, connectForm);
        root.setId("rootVBox");

        gameLog = new GameLog();
        stack = new StackPane(gameLog, root);
        stack.setId("stack");
        scene = new Scene(stack, MyScene.width, MyScene.height);
        scene.getStylesheets().add("scenes/StartScene.css");
        setBackground(Color.web("#262626"));
    }

    public ConnectForm getConnectForm() { return this.connectForm; }
}

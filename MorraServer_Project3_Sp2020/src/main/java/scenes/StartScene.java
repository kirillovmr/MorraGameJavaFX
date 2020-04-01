package scenes;

import elements.ConnectForm;
import elements.GameLog;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class StartScene extends MyScene
{
    public Text titleText;
    public ConnectForm connectForm;
    public GameLog gameLog;

    public StartScene()
    {
        titleText = new Text("Title");
        connectForm = new ConnectForm("Start the server");
        VBox root = new VBox(titleText, connectForm);

        gameLog = new GameLog();
        stack = new StackPane(gameLog, root);
        scene = new Scene(stack, MyScene.width, MyScene.height);
        setBackground(Color.web("#262626"));
    }
}

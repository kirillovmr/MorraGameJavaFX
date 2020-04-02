package scenes;

import elements.GameLog;
import elements.Title;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import elements.ConnectForm;

public class ConnectScene extends MyScene
{
    public Title titleText;
    private ConnectForm connectForm;

    public ConnectScene()
    {
        this.titleText = new Title();
        this.connectForm = new ConnectForm("Connect");
        VBox root = new VBox(titleText, connectForm);

        gameLog = new GameLog();
        stack = new StackPane(gameLog, root);
        scene = new Scene(stack, MyScene.width, MyScene.height);
        setBackground(Color.web("#262626"));
    }

    public ConnectForm getConnectForm() { return this.connectForm; }
}

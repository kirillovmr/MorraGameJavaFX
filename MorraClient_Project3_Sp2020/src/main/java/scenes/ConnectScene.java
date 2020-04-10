package scenes;

import core.Logic;
import elements.GameLog;
import elements.Title;
import elements.UI;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import elements.ConnectForm;
import scenes.MyScene;

public class ConnectScene extends MyScene
{
    public Title titleText;
    private ConnectForm connectForm;

    public ConnectScene()
    {
        this.titleText = new Title();
        this.connectForm = new ConnectForm("Connect");
        VBox root = new VBox(titleText, connectForm);
        root.setId("rootVBox");

        gameLog = new GameLog();
        stack = new StackPane(gameLog, root);
        stack.setId("stack");
        scene = new Scene(stack, MyScene.width, MyScene.height);
        scene.getStylesheets().add("scenes/ConnectScene.css");
        setBackground(Color.web("#262626"));

        this.setConnectBtnHandler();
    }

    private void setConnectBtnHandler() {
        this.connectForm.setOnButtonPress(e -> {
            String ip = this.connectForm.getIP();
            int port = this.connectForm.getPort();
            if (Logic.client.connect(ip, port))
            {
                Logic.logger.add("Connected to " + ip + ":" + port);
                UI.setScene(UI.waitingScene, false);
                Logic.client.start();
            }
            else
            {
                Logic.logger.add("Unable to connect to " + ip + ":" + port);
            }
        });
    }
}

package elements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConnectForm extends VBox
{
    private TextField ipField, portField;
    private Button connectBtn;

    public ConnectForm(String buttonText)
    {
        ipField = new TextField();
        if (buttonText.equals("Connect")) {
            ipField.setPromptText("127.0.0.1");
        }
        else {
            ipField.setText("localhost");
            ipField.setDisable(true);
        }

        portField = new TextField();
        portField.setPromptText("3040");
        HBox fieldBox = new HBox(ipField, portField);

        connectBtn = new Button(buttonText);

        this.getChildren().addAll(fieldBox, connectBtn);
    }

    public void setOnButtonPress(EventHandler<ActionEvent> e) {
        connectBtn.setOnAction(e);
    }
}

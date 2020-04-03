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
        this.setId("connectFormVBox");

        ipField = new TextField();
        ipField.setId("ipField");
        ipField.setText("127.0.0.1");
        ipField.setPromptText("127.0.0.1");
        ipField.setDisable(!buttonText.equals("Connect"));

        portField = new TextField();
        portField.setId("portField");
        portField.setText("5555");
        portField.setPromptText("5555");

        HBox fieldBox = new HBox(ipField, portField);
        fieldBox.setId("fieldBoxHBox");

        connectBtn = new Button(buttonText);
        connectBtn.setId("connectBtn");

        this.getChildren().addAll(fieldBox, connectBtn);
    }

    public String getIP()
    {
        return this.ipField.getText();
    }

    public int getPort() { return Integer.parseInt(this.portField.getText()); }

    public void setOnButtonPress(EventHandler<ActionEvent> e) {
        connectBtn.setOnAction(e);
    }
}

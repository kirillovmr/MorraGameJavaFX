package elements;

import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class GameLog extends TextArea
{
    public GameLog()
    {
        this.setId("gameLogTextArea");
        this.setMaxWidth(200);
        this.setMaxHeight(60);
        this.setDisable(true);
        this.setWrapText(true);

        StackPane.setAlignment(this, Pos.BOTTOM_LEFT);
    }
}

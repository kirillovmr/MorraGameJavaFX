package elements;

import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class GameLog extends TextArea
{
    public GameLog()
    {
        this.setMaxWidth(400);
        this.setMaxHeight(100);
        this.setDisable(true);
        StackPane.setAlignment(this, Pos.BOTTOM_LEFT);
    }
}

package elements;

import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class GameLog extends TextArea
{
    public GameLog()
    {
        this.setMaxWidth(100);
        this.setMaxHeight(100);
        StackPane.setAlignment(this, Pos.BOTTOM_LEFT);
    }
}

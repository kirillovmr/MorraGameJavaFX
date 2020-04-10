package elements;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class GameLog extends ListView<String>
{
    public GameLog()
    {
        this.setId("gameLogTextArea");
        this.setMaxHeight(100);
        this.setDisable(true);

        StackPane.setAlignment(this, Pos.BOTTOM_LEFT);
    }
}

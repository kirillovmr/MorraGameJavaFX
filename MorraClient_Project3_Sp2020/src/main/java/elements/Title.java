package elements;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Title extends VBox
{
    private Text topText, bottomText;

    public Title()
    {
        topText = new Text("THE ANCIENT");
        bottomText = new Text("GAME OF MORRA");
        this.getChildren().addAll(topText, bottomText);
    }
}

package elements;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Title extends VBox
{
    private Text topText, bottomText;

    public Title()
    {
        this.setId("titleVBox");

        topText = new Text("THE ANCIENT");
        topText.setId("topText");
        bottomText = new Text("GAME OF MORRA");
        bottomText.setId("bottomText");
        this.getChildren().addAll(topText, bottomText);
    }
}

import core.Logic;
import core.Logger;
import elements.UI;
import javafx.application.Application;
import javafx.stage.Stage;

public class TheGameOfMorra extends Application
{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		UI.primaryStage = primaryStage;
		UI.primaryStage.setTitle("(Client2) Let's Play Morra!!!");

		// Creating a Logger instance
		Logic.logger = new Logger();

		// Creating all required scenes
		UI.createScenes();

		// Creating a Client instance
		Logic.createClient();

		// Setting the initial scene
		UI.setScene(UI.connectScene, false);
		UI.primaryStage.show();
	}
}

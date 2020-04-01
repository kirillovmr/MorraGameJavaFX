import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import scenes.ActiveScene;
import scenes.MyScene;
import scenes.StartScene;
import server.Server;


public class TheGameOfMorra extends Application
{
	Stage primaryStage;
	MyScene currentScene;
	StartScene startScene;
	ActiveScene activeScene;

	Server server;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("(Server) Let's Play Morra!!!");
		this.primaryStage = primaryStage;

		// Creating all required scenes
		createScenes();

		// Setting the initial scene
		setScene(startScene);
		primaryStage.show();
	}

	// Sets the scene to the stage
	private void setScene(MyScene scene)
	{
		currentScene = scene;
		this.primaryStage.setScene(scene.getScene());
	}

	// Creates all the scenes needed by APP
	private void createScenes()
	{
		this.startScene = new StartScene();
		this.startScene.connectForm.setOnButtonPress(e -> {
			server = new Server(data -> Platform.runLater(() -> System.out.println("Data: " + data)));
			this.setScene(this.activeScene);
		});

		this.activeScene = new ActiveScene();
		this.activeScene.setOnButtonPress(e -> this.setScene(this.startScene));
	}
}

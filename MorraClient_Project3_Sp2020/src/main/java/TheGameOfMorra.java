import client.Client;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.util.Duration;
import scenes.*;

public class TheGameOfMorra extends Application
{
	Stage primaryStage;
	MyScene currentScene;
	ConnectScene connectScene;
	WaitingScene waitingScene;
	GameScene gameScene;

	Client client;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("(Client2) Let's Play Morra!!!");
		this.primaryStage = primaryStage;

		// Creating all required scenes
		createScenes();

		// Setting the initial scene
		setScene(connectScene, false);
		primaryStage.show();

		client = new Client(data-> Platform.runLater(()-> System.out.println("Data:" + data)));
	}

	// Sets the scene to the stage
	private void setScene(MyScene scene, boolean anim)
	{
		if (anim)
		{
			FadeTransition ft = new FadeTransition();
			ft.setDuration(Duration.millis(100));
			ft.setNode(currentScene.stack);
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.setOnFinished(e -> {
				currentScene = scene;
				this.primaryStage.setScene(scene.getScene());
			});
			ft.play();
		}
		else
		{
			currentScene = scene;
			this.primaryStage.setScene(scene.getScene());
		}
	}

	// Creates all the scenes needed by APP
	private void createScenes()
	{
		this.connectScene = new ConnectScene();
		this.connectScene.connectForm.setOnButtonPress(e -> {
			this.setScene(this.waitingScene, true);
			this.client.start();
		});

		this.waitingScene = new WaitingScene();
		this.waitingScene.setOnButtonPress(e -> {
			this.currentScene.interpolateBg(Color.web("#262626"), Color.LIGHTBLUE, 500);
			this.client.send("Lol");
		});

		this.gameScene = new GameScene();
	}
}

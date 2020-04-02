import client.Client;
import core.MorraInfo;
import core.Logger;
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

	Logger logger;
	Client client;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("(Client2) Let's Play Morra!!!");
		this.primaryStage = primaryStage;

		// Creating Logger instance
		this.logger = new Logger();

		// Creating all required scenes
		this.createScenes();

		// Setting the initial scene
		this.setScene(this.connectScene, false);
		this.primaryStage.show();

		// Creating a client
		this.client = new Client(morraInfo ->
		{
			Platform.runLater(()->
			{
				System.out.println("Received:" + morraInfo.toString());
			});
		});
	}

	// Creates all the scenes needed by APP
	private void createScenes()
	{
		this.connectScene = new ConnectScene();
		this.logger.subscribe(this.connectScene.getGameLog());
		this.connectScene.getConnectForm().setOnButtonPress(e ->
		{
			String ip = this.connectScene.getConnectForm().getIP();
			int port = this.connectScene.getConnectForm().getPort();
			if (this.client.connect(ip, port))
			{
				this.logger.add("Connected to " + ip + ":" + port);
				this.setScene(this.waitingScene, true);
				this.client.start();
			}
			else
			{
				this.logger.add("Unable to connect to " + ip + ":" + port);
			}
		});

		this.waitingScene = new WaitingScene();
		this.logger.subscribe(this.waitingScene.getGameLog());
		this.waitingScene.setOnButtonPress(e ->
		{
			this.currentScene.interpolateBg(Color.web("#262626"), Color.LIGHTBLUE, 500);
			MorraInfo morraInfo = new MorraInfo();
			this.client.sendInfo(morraInfo);
		});

		this.gameScene = new GameScene();
		this.logger.subscribe(this.gameScene.getGameLog());
	}

	// Sets the scene to the stage
	private void setScene(MyScene scene, boolean anim)
	{
		if (anim)
		{
			FadeTransition ft = new FadeTransition();
			ft.setDuration(Duration.millis(100));
			ft.setNode(this.currentScene.stack);
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.setOnFinished(e -> {
				this.currentScene = scene;
				this.primaryStage.setScene(scene.getScene());
			});
			ft.play();
		}
		else
		{
			this.currentScene = scene;
			this.primaryStage.setScene(scene.getScene());
		}
	}
}

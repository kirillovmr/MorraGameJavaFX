import client.Client;
import core.MorraInfo;
import core.Logger;
import elements.UI;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.util.Duration;
import scenes.*;
import scenes.ConnectScene;

public class TheGameOfMorra extends Application
{
	Stage primaryStage;
	MyScene currentScene;
	ConnectScene connectScene;
	WaitingScene waitingScene;
	GameScene gameScene;

	Logger logger;
	Client client;

	int playerID = -1;

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
		this.setScene(this.gameScene, false);
		this.primaryStage.show();

		// Creating a client
		this.client = new Client(data ->
				Platform.runLater(()->
				{
					// If Morra info received
					if (data instanceof MorraInfo) {
						MorraInfo morraInfo = (MorraInfo) data;
						System.out.println("Received:" + data.toString());

						// If we are waiting for an opponent
						if (currentScene.getClass().getName().equals(waitingScene.getClass().getName())) {
							this.setScene(this.gameScene, true);
							this.logger.add("Matched with a partner");

							if (morraInfo.p1ID == this.playerID) {
								UI.updatePlayerScore(morraInfo.p1Points);
								UI.updateOpponentScore(morraInfo.p2Points);
							}
							else {
								UI.updatePlayerScore(morraInfo.p2Points);
								UI.updateOpponentScore(morraInfo.p1Points);
							}
						}
						// If we are playing
						else if (currentScene.getClass().getName().equals(gameScene.getClass().getName())) {

						}
					}
					// If Integer received
					else if (data instanceof Integer) {
						this.playerID = (int) data;
						System.out.println("ID received:" + this.playerID);
					}
				}));
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

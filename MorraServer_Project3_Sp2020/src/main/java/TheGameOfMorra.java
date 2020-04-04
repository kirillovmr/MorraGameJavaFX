import core.Logger;
import core.Logic;
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

	Logger logger;
	Server server;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("(Server) Let's Play Morra!!!");
		this.primaryStage = primaryStage;

		// Creating Logger instance
		this.logger = new Logger();

		// Creating all required scenes
		this.createScenes();

		// Setting the initial scene
		this.setScene(this.startScene);
		this.primaryStage.show();

		// Creating a server
		this.createServer();
	}

	// Creates all the scenes needed by APP
	private void createScenes()
	{
		this.startScene = new StartScene();
		this.logger.subscribe(this.startScene.getGameLog());
		this.startScene.getConnectForm().setOnButtonPress(e ->
		{
			int port = this.startScene.getConnectForm().getPort();
			if (this.server.getInstance().startServer(port)) {
				this.logger.add("Server started at port " + port);
				this.setScene(this.activeScene);
				this.server.getInstance().start();
			}
			else
			{
				this.logger.add("Unable to start server at port " + port);
			}
		});

		this.activeScene = new ActiveScene();
		this.logger.subscribe(this.activeScene.getGameLog());
		this.activeScene.setOnButtonPress(e ->
		{
			this.server.getInstance().stopServer();
			this.createServer();
			this.setScene(this.startScene);
			this.logger.add("Server stopped");
		});
	}

	// Creating a server instance
	private void createServer()
	{
		this.server = new Server(this.logger, data ->
		{
			Platform.runLater(()->
			{
				System.out.println("Data:" + data);
			});
		});
		Logic.server = this.server;
	}

	// Sets the scene to the stage
	private void setScene(MyScene scene)
	{
		this.currentScene = scene;
		this.primaryStage.setScene(scene.getScene());
	}
}

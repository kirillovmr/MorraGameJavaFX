package elements;

import core.MorraInfo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GameArea extends StackPane {

    ArrayList<ImageView> hands;
    ArrayList<ImageView> oppHands;
    Text vsText;

    private Consumer<Integer> onPlayerSelect;

    Timeline randOppAnim;
    int playerHandValue = -1;
    int opponentHandValue = -1;

    public GameArea() {
        hands = new ArrayList<>();
        oppHands = new ArrayList<>();

        createPlayerHands();
        createOpponentHands();

        vsText = new Text("VS");
        vsText.setId("vsText");
        vsText.setVisible(false);
        this.getChildren().add(vsText);

        //TODO: remove later
        vsText.setOnMouseClicked(e -> {
            showOpponentHand(4);
        });
    }

    public void setOnPlayerSelect(Consumer<Integer> onPlayerSelect) {
        this.onPlayerSelect = onPlayerSelect;
    }

    public void showOpponentHand(int handValue) {
        opponentHandValue = handValue;
        randOppAnim.stop();
        for (int i = 0; i < 5; i++) {
            oppHands.get(i).setVisible(opponentHandValue - 1 == i);
        }
        this.updateUI();
    }

    private void updateUI() {
        int duration = 300;
        Color colorFrom = Color.web("#262626");
        Color colorTo;

        if (this.playerHandValue > this.opponentHandValue) {
            colorTo = Color.web("#73B53A");
            UI.middleText.setText("YOU WIN");
        }
        else if (this.playerHandValue == this.opponentHandValue) {
            colorTo = Color.web("#49ADC1");
            UI.middleText.setText("DRAW");
        }
        else {
            colorTo = Color.web("#EC3A24");
            UI.middleText.setText("YOU LOSE");
        }

        UI.gameScene.interpolateBg(colorFrom, colorTo, duration, e -> {
            Timeline t = new Timeline(new KeyFrame(Duration.millis(3000), e2 -> {
                UI.gameScene.interpolateBg(colorTo, colorFrom, duration, e3 -> {
                    UI.setScene(UI.finishScene, true);
                });
            }));
            t.play();
        });
    }

    private void createPlayerHands() {
        for (int i=1; i<=5; i++) {
            ImageView hand = createImageView("hand"+i+".png", 0.2);
            hands.add(hand);
            this.getChildren().add(hand);

            int finalI = i;
            hand.setOnMouseClicked(e -> {
                playerHandValue = finalI;

                // Accept a callback with a data of what we played
                onPlayerSelect.accept(playerHandValue);

                // Translating it to the left
                TranslateTransition t = new TranslateTransition();
                t.setDuration(Duration.millis(1000));
                t.setNode(hand);
                t.setByX( (finalI-1) * -100 + 100);
                t.setOnFinished(ignored -> {
                    // Update Middle text
                    UI.updateMiddleText("WAITING FOR OPPONENT...");
                    vsText.setVisible(true);

                    // Randomize opp hands
                    randOppAnim = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
                        int idx = 0;
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            int prevIdx = idx - 1;
                            prevIdx = prevIdx < 0 ? 4 : prevIdx;
                            oppHands.get(prevIdx).setVisible(false);
                            oppHands.get(idx).setVisible(true);
                            idx = (idx + 1) % 5;
                        }
                    }));
                    randOppAnim.setCycleCount(Timeline.INDEFINITE);
                    randOppAnim.play();
                });
                t.play();

                // Disabling all other hands
                for (int j=1; j<=5; j++) {
                    if (j != finalI) {
                        hands.get(j-1).setVisible(false);
                    }
                }

                // Disabling on click event
                hand.setOnMouseClicked(e2 -> {
                    System.out.println("Finito");
                });
            });

            // Translating hands
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(1000));
            t.setNode(hand);
            t.setByX( -(3 - i) * 100 );
            t.play();
        }
    }

    private void createOpponentHands() {
        for (int i=1; i<=5; i++) {
            ImageView hand = createImageView("hand"+i+".png", 0.2);
            hand.setVisible(false);
            oppHands.add(hand);
            this.getChildren().add(hand);

            // Translating hands
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(1000));
            t.setNode(hand);
            t.setByX( 100 );
            t.play();
        }
    }

    // Returns ImageView for given filename
    public static ImageView createImageView(String filename, double scale) {
        ImageView imageView = new ImageView(loadImage(filename));
        imageView.setFitHeight(imageView.getImage().getHeight() * scale);
        imageView.setFitWidth(imageView.getImage().getWidth() * scale);
        return imageView;
    }

    // Returns Image for given filename
    public static Image loadImage(String filename) {
        try {
            return new Image(new FileInputStream("src/main/resources/images/" + filename));
        }
        catch (FileNotFoundException e) {
            System.err.println(">< createImage: File " + filename + " was not found");
            return null;
        }
    }
}

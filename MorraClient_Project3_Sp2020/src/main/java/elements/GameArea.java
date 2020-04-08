package elements;

import core.MorraInfo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
    ArrayList<Text> totalGuessTexts;
    Text opponentGuessText;
    Text vsText;

    private Consumer<String> onPlayerSelect;

    Timeline randOppAnim;
    int playerHandValue = -1;
    int opponentHandValue = -1;
    int playerGuess = -1;
    int opponentGuess = -1;

    public GameArea() {
        hands = new ArrayList<>();
        oppHands = new ArrayList<>();
        totalGuessTexts = new ArrayList<>();

        createPlayerHands();
        createOpponentHands();

        opponentGuessText = new Text("?");
        opponentGuessText.setVisible(false);
        this.getChildren().add(opponentGuessText);
        translate(opponentGuessText, 50, null);

        vsText = new Text("VS");
        vsText.setId("vsText");
        vsText.setVisible(false);
        this.getChildren().add(vsText);
    }

    public void setOnPlayerSelect(Consumer<String> onPlayerSelect) {
        this.onPlayerSelect = onPlayerSelect;
    }
    public Consumer<String> getOnPlayerSelect() { return this.onPlayerSelect; }

    public void showOpponentHand(int handValue, int oppGuess) {
        opponentHandValue = handValue;
        opponentGuess = oppGuess;
        randOppAnim.stop();
        for (int i = 0; i < 5; i++) {
            oppHands.get(i).setVisible(opponentHandValue - 1 == i);
        }
        opponentGuessText.setText("" + opponentGuess);
        this.updateUI();
    }

    private void updateUI() {
        int duration = 300;
        Color colorFrom = Color.web("#262626");
        Color colorTo;

        int totalHandValue = playerHandValue + opponentHandValue;

        if (this.playerGuess == totalHandValue && this.opponentGuess == totalHandValue) {
            colorTo = Color.web("#49ADC1");
            UI.middleText.setText("DRAW");
        }
        else if (this.playerGuess == totalHandValue) {
            colorTo = Color.web("#73B53A");
            UI.middleText.setText("YOU WIN");
        }
        else {
            colorTo = Color.web("#EC3A24");
            UI.middleText.setText("YOU LOSE");
        }

        UI.gameScene.interpolateBg(colorFrom, colorTo, duration, e -> {
            Timeline t = new Timeline(new KeyFrame(Duration.millis(5000), e2 -> {
                UI.gameScene.interpolateBg(colorTo, colorFrom, duration, e3 -> {
                    UI.setScene(UI.finishScene, false);
                });
            }));
            t.play();
        });
    }

    private void createGuessText() {
        for (int i=0; i<5; i++) {
            int guessNumber = playerHandValue + 1 + i;
            Text text = new Text("" + guessNumber);
            totalGuessTexts.add(text);

            UI.updateMiddleText("MAKE A TOTAL GUESS");

            int finalI = i;
            text.setOnMouseClicked(e -> {
                System.out.println("Clicked: " + guessNumber);
                playerGuess = guessNumber;

                for (int j=0; j<totalGuessTexts.size(); j++) {
                    Text t = totalGuessTexts.get(j);

                    t.setOnMouseClicked(null);
                    t.setVisible(j == finalI);
                }

                translate(text, -finalI * 50, onFinish -> {
                    // Update Middle text
                    UI.updateMiddleText("WAITING FOR OPPONENT...");
                    vsText.setVisible(true);
                    opponentGuessText.setVisible(true);

                    // Accept a callback with a data of what we played
                    onPlayerSelect.accept("" + playerHandValue + "," + playerGuess);

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
            });

            this.getChildren().add(text);

            translate(text, -(1 - i) * 50, null);
        }
    }

    private void createPlayerHands() {
        for (int i=1; i<=5; i++) {
            ImageView hand = createImageView("hand"+i+".png", 0.2);
            hands.add(hand);
            this.getChildren().add(hand);

            int finalI = i;
            hand.setOnMouseClicked(e -> {
                playerHandValue = finalI;

                UI.gameScene.bottomText.setVisible(false);

                // Translating it to the left
                TranslateTransition t = new TranslateTransition();
                t.setDuration(Duration.millis(1000));
                t.setNode(hand);
                t.setByX( (finalI-1) * -100 + 100);
                t.setOnFinished(ignored -> {
                    createGuessText();
                });
                t.play();

                // Disabling all other hands
                for (int j=1; j<=5; j++) {
                    if (j != finalI) {
                        hands.get(j-1).setVisible(false);
                    }
                }

                // Disabling on click event
                hand.setOnMouseClicked(null);
            });

            // Translating hands
            translate(hand, -(3 - i) * 100, null);
        }
    }

    private void createOpponentHands() {
        for (int i=1; i<=5; i++) {
            ImageView hand = createImageView("hand"+i+".png", 0.2);
            hand.setVisible(false);
            oppHands.add(hand);
            this.getChildren().add(hand);

            // Translating hands
            translate(hand, 100, null);
        }
    }

    private void translate(Node node, double translation, EventHandler<ActionEvent> onFinish) {
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.millis(1000));
        t.setNode(node);
        t.setByX( translation );
        t.setOnFinished(onFinish);
        t.play();
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

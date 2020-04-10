package elements;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameArea extends VBox {

    int numRoomsShowing;
    ArrayList<RoomArea> roomAreas;

    HBox row1, row2;

    public GameArea() {
        numRoomsShowing = 0;
        roomAreas = new ArrayList<>();
        for (int i=0; i<4; i++) {
            RoomArea roomArea = new RoomArea(0, 0);
            roomArea.setVisible(false);
            roomAreas.add(roomArea);
        }

        row1 = new HBox(roomAreas.get(0), roomAreas.get(1));
        row2 = new HBox(roomAreas.get(2), roomAreas.get(3));

        this.getChildren().addAll(row1, row2);
    }

    public void addRoom(int id1, int id2) {
        RoomArea roomArea = null;
        for (RoomArea r: roomAreas) {
            if(!r.isVisible()) {
                roomArea = r;
                break;
            }
        }

        if (roomArea == null) {
            return;
        }

        roomArea.init();
        roomArea.p1ID = id1;
        roomArea.p2ID = id2;
        roomArea.p1IdText.setText("    ID:" + id1);
        roomArea.p2IdText.setText("ID:" + id2 + "    ");
        roomArea.setVisible(true);
    }

    public void deleteRoom(int playerID) {
        for (RoomArea roomArea: roomAreas) {
            if (roomArea.p1ID == playerID || roomArea.p2ID == playerID) {
                roomArea.setVisible(false);
                break;
            }
        }
    }

    public void showPlayerSelection(int playerID, int handValue, int playerValue) {
        for (RoomArea roomArea: roomAreas) {
            if (roomArea.p1ID == playerID || roomArea.p2ID == playerID) {
                Text playerValueText = roomArea.p1ID == playerID ? roomArea.p1Number : roomArea.p2Number;
                ArrayList<ImageView> hands = roomArea.p1ID == playerID ? roomArea.p1Images : roomArea.p2Images;
                Timeline randomAnim = roomArea.p1ID == playerID ? roomArea.p1RandAnim : roomArea.p2RandAnim;

                randomAnim.stop();
                for (int i = 0; i < 5; i++) {
                    hands.get(i).setVisible(handValue - 1 == i);
                }
                playerValueText.setText("" + playerValue);
            }
        }
    }

    public void setPoints(int playerID, int playerPoints) {
        for (RoomArea roomArea: roomAreas) {
            if (roomArea.p1ID == playerID || roomArea.p2ID == playerID) {
                if (roomArea.p1ID == playerID) {
                    roomArea.p1Points.setText("    P:" + playerPoints);
                }
                else {
                    roomArea.p2Points.setText("P:" + playerPoints + "    ");
                }
            }
        }
    }

    public void playAgain(int playerID) {
        for (RoomArea roomArea: roomAreas) {
            if (roomArea.p1ID == playerID || roomArea.p2ID == playerID) {
                roomArea.init();
            }
        }
    }


    public class RoomArea extends TilePane {

        int p1ID, p2ID;
        Text p1IdText, p2IdText;
        Text p1Points, p2Points;
        Text p1Number, p2Number;
        ArrayList<ImageView> p1Images, p2Images;

        Timeline p1RandAnim, p2RandAnim;

        StackPane stack;

        public RoomArea(int id1, int id2) {
            p1ID = id1;
            p2ID = id2;
            stack = new StackPane();

            init();

            this.getChildren().addAll(stack);
            this.setPrefTileWidth(320);
        }

        private void init() {
            // Clearing stack
            stack.getChildren().remove(0, stack.getChildren().size());

            // Setting ID
            p1IdText = new Text("    ID:" + p1ID);
            p2IdText = new Text("ID:" + p2ID + "    ");
            StackPane.setAlignment(p1IdText, Pos.TOP_LEFT);
            StackPane.setAlignment(p2IdText, Pos.TOP_RIGHT);

            // Setting points
            p1Points = new Text("    P:0");
            p2Points = new Text("P:0    ");
            StackPane.setAlignment(p1Points, Pos.BOTTOM_LEFT);
            StackPane.setAlignment(p2Points, Pos.BOTTOM_RIGHT);

            // Hand images
            p1Images = new ArrayList<>();
            p2Images = new ArrayList<>();
            createHands(p1Images, -100);
            createHands(p2Images, 100);
            startAnim(true);
            startAnim(false);

            // Players number
            p1Number = new Text("?");
            p2Number = new Text("?");
            translate(p1Number, -50);
            translate(p2Number, 50);

            // VS text
            Text vsText = new Text("VS");

            stack.getChildren().addAll(p1IdText, p2IdText, p1Points, p2Points, p1Number, p2Number, vsText);
        }

        private void stopAnim(boolean player1) {
            Timeline timeline = player1 ? p1RandAnim : p2RandAnim;
            timeline.stop();
        }

        private void startAnim(boolean player1) {
            ArrayList<ImageView> hands = player1 ? p1Images : p2Images;

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
                int idx = 0;
                @Override
                public void handle(ActionEvent actionEvent) {
                    int prevIdx = idx - 1;
                    prevIdx = prevIdx < 0 ? 4 : prevIdx;
                    hands.get(prevIdx).setVisible(false);
                    hands.get(idx).setVisible(true);
                    idx = (idx + 1) % 5;
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            if (player1) {
                p1RandAnim = timeline;
            } else {
                p2RandAnim = timeline;
            }
        }

        private void createHands(ArrayList<ImageView> list, double translation) {
            for (int i=1; i<=5; i++) {
                ImageView hand = createImageView("hand"+i+".png", 0.2);
                hand.setVisible(true);
                list.add(hand);
                stack.getChildren().add(hand);

                // Translating hands
                translate(hand, translation);
            }
        }

        private void translate(Node node, double translation) {
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.millis(1000));
            t.setNode(node);
            t.setByX( translation );
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

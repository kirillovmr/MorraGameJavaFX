package scenes;

import elements.GameLog;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class MyScene {
    protected Scene scene;
    public StackPane stack;
    public static double width = 640, height = 360;

    protected GameLog gameLog;

    public void setBackground(Color color)
    {
        stack.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void interpolateBg(Color fromColor, Color toColor, int duration)
    {
        Rectangle rect = new Rectangle();
        rect.setFill(fromColor);

        FillTransition tr = new FillTransition();
        tr.setShape(rect);
        tr.setDuration(Duration.millis(duration));
        tr.setFromValue(fromColor);
        tr.setToValue(toColor);

        tr.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                stack.setBackground(new Background(new BackgroundFill(rect.getFill(), CornerRadii.EMPTY, Insets.EMPTY)));
                return t;
            }
        });

        tr.play();
    }

    public Scene getScene() {
        return scene;
    }

    public GameLog getGameLog() { return gameLog; }
}

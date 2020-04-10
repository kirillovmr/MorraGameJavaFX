package core;

import elements.GameLog;
import javafx.application.Platform;

import java.util.ArrayList;

public class Logger
{
    private ArrayList<String> entries;
    private ArrayList<GameLog> logFields;

    public Logger()
    {
        entries = new ArrayList<>();
        logFields = new ArrayList<>();
    }

    public void add(String entry)
    {
        entries.add(entry);
        for(GameLog logField: logFields)
        {
            logField.getItems().add(entry);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> logField.scrollTo(Integer.MAX_VALUE));
                        }
                    }, 100
            );
        }
    }

    public void subscribe(GameLog gameLog)
    {
        logFields.add(gameLog);
        for(String entry: entries)
        {
            gameLog.getItems().add(entry);
        }
        gameLog.scrollTo(Integer.MAX_VALUE);
    }
}

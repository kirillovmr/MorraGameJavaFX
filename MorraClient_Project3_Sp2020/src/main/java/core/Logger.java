package core;

import elements.GameLog;

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
            logField.appendText(entry);
            logField.appendText("\n");
        }
    }

    public void subscribe(GameLog gameLog)
    {
        logFields.add(gameLog);
        for(String entry: entries)
        {
            gameLog.appendText(entry);
            gameLog.appendText("\n");
        }
    }
}
